package com.intern.assignment.repositories;

import com.intern.assignment.config.DatabaseConnection;
import com.intern.assignment.entities.Device;
import org.neo4j.driver.Driver;
import org.neo4j.driver.types.Node;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class DeviceRepository {
    private static final Driver driver = DatabaseConnection.initialise();
    private final static Logger logger = LoggerFactory.getLogger(DeviceRepository.class);
    public Device createDevice(Device device) {
        final String query = """
                MERGE (device:Device
                {
                    deviceName:$deviceName,
                    partNumber:$partNumber,
                    buildingName:$buildingName,
                    deviceType:$deviceType
                }
                )
                RETURN device
                """;
        Map<String, Object> map = new HashMap<>();
        map.put("deviceName", device.getDeviceName());
        map.put("partNumber", device.getPartNumber());
        map.put("buildingName", device.getBuildingName());
        map.put("deviceType", device.getDeviceType());

        var result = driver.executableQuery(query).withParameters(map).execute();
        var record = result.records()
                .stream()
                .map(r -> r.get("device").asNode())
                .findFirst().orElseThrow(() -> new RuntimeException("Device could not be created"));
        logger.info("Device Repository: Device with ID: {} created successfully", record.elementId());
        return device;
    }

    public List<Device> searchDevices(String buildingName, String deviceName, String partNumber, String deviceType) {
        String query = "MATCH (device:Device) WHERE 1=1 ";
        Map<String, Object> params = new HashMap<>();

        if(buildingName != null) {
            query += "AND device.buildingName = $buildingName ";
            params.put("buildingName", buildingName);
        }
        if(deviceName != null) {
            query += "AND device.deviceName = $deviceName ";
            params.put("deviceName", deviceName);
        }
        if(deviceType != null) {
            query += "AND device.deviceType = $deviceType ";
            params.put("deviceType", deviceType);
        }
        if(partNumber != null) {
            query += "AND device.partNumber = $partNumber ";
            params.put("partNumber",partNumber);
        }

        query += "RETURN device";

        var records = driver.executableQuery(query).withParameters(params).execute().records();

        List<Device> devices = new ArrayList<>();
        records.forEach(record -> {
            Node node = record.get("device").asNode();
            Device device = new Device();
            device.setDeviceName(node.get("deviceName").asString());
            device.setDeviceType(node.get("deviceType").asString());
            device.setBuildingName(node.get("buildingName").asString());
            device.setPartNumber(node.get("partNumber").asString());
            devices.add(device);
        });
        logger.info("Device Repository: Search devices function accessed with query: {}", query);
        return devices;
    }
}

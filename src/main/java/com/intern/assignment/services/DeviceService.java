package com.intern.assignment.services;

import com.intern.assignment.entities.Device;
import com.intern.assignment.repositories.DeviceRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DeviceService {
    private final DeviceRepository deviceRepository;
    private final Logger logger = LoggerFactory.getLogger(DeviceService.class);
    @Autowired
    public DeviceService(DeviceRepository deviceRepository) { this.deviceRepository = deviceRepository; }

    public Device createDevice(Device device) {
        logger.info("Device Service: Device creation requested and forwarded to Device Repository");
        return deviceRepository.createDevice(device);
    }

    public List<Device> searchDevices(String deviceName, String buildingName, String partNumber, String deviceType) {
        logger.info("Device Service: Search Devices function called and passed to repository");
        return deviceRepository.searchDevices(buildingName, deviceName, partNumber, deviceType);
    }
}

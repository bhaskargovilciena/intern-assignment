package com.intern.assignment.repositories;

import com.intern.assignment.config.DatabaseConnection;
import com.intern.assignment.entities.ShelfPosition;
import org.neo4j.driver.Driver;
import org.neo4j.driver.Value;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Repository
public class ShelfPositionRepository {
    private static final Driver driver = DatabaseConnection.initialise();
    private static final Logger logger = LoggerFactory.getLogger(ShelfPositionRepository.class);

    public List<ShelfPosition> createShelfPosition(String deviceId, int numberOfShelfPositions) {
        String query = """
                MATCH (device:Device) WHERE elementId(device) = $id
                WITH device, range(1, $numberOfShelfPositions) AS positions
                UNWIND positions AS position
                CREATE (shelfPosition:ShelfPosition {
                    deviceId: $id
                })
                MERGE (device)-[:HAS]->(shelfPosition)
                RETURN collect(shelfPosition) AS shelfPositions
                """;
        var records = driver.executableQuery(query).withParameters(Map.of(
                "id", deviceId,
                "numberOfShelfPositions", numberOfShelfPositions
        )).execute().records();

        logger.info("Shelf Position Repository: {} Shelf Positions created for {}", numberOfShelfPositions, deviceId);

        return records
                .stream()
                .flatMap(record -> record.get("shelfPositions").asList(Value::asNode).stream())
                .map(node -> {
                    ShelfPosition shelfPosition = new ShelfPosition();
                    shelfPosition.setId(node.elementId());
                    shelfPosition.setDeviceId(node.get("deviceId").asString());
                    return shelfPosition;
                })
                .toList();
    }

    public List<ShelfPosition> getShelfPositions(String deviceId) {
        String query = """
                MATCH (device:Device) WHERE elementId(device) = $id
                MATCH (device)-[:HAS]->(shelfPosition:ShelfPosition)
                RETURN shelfPosition
                """;
        var records = driver.executableQuery(query).withParameters(Map.of("id", deviceId)).execute().records();

        List<ShelfPosition> shelfPositions = new ArrayList<>();

        records.stream()
                .map(record -> record.get("shelfPosition").asNode())
                .forEach(node -> {
                    ShelfPosition shelfPosition = new ShelfPosition();
                    shelfPosition.setDeviceId(node.get("deviceId").asString());
                    shelfPosition.setId(node.elementId());
                    shelfPositions.add(shelfPosition);
                });

        return shelfPositions;
    }
}

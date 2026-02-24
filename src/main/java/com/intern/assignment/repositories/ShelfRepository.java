package com.intern.assignment.repositories;

import com.intern.assignment.config.DatabaseConnection;
import com.intern.assignment.entities.Shelf;
import org.neo4j.driver.Driver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import java.util.Map;

@Repository
public class ShelfRepository {
    private static final Driver driver = DatabaseConnection.initialise();
    private static final Logger logger = LoggerFactory.getLogger(ShelfRepository.class);

    public Shelf createShelf(String shelfPositionId, Shelf shelf) {
        String query = """
                MATCH (shelfPosition:ShelfPosition) WHERE elementId(shelfPosition) = $id
                MERGE (shelfPosition)-[:HAS]->(shelf:Shelf {
                    name: $name,
                    partNumber: $partNumber
                })
                RETURN shelf
                """;
        var records = driver.executableQuery(query).withParameters(Map.of(
                "id", shelfPositionId,
                "name", shelf.getName(),
                "partNumber", shelf.getPartNumber()
        )).execute().records();

        records.forEach(record -> shelf.setId(record.get("shelf").asNode().elementId()));
        logger.info("Shelf Repository: Shelf created with ID {}", shelf.getId());
        return shelf;
    }
}

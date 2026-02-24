package com.intern.assignment.repositories;

import com.intern.assignment.config.DatabaseConnection;
import com.intern.assignment.entities.Shelf;
import org.neo4j.driver.Driver;
import org.neo4j.driver.types.Node;
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

    public Shelf getShelf(String shelfPositionId) {
        String query = """
                MATCH (shelfPosition:ShelfPosition) WHERE elementId(shelfPosition) = $id
                MATCH (shelfPosition)-[:HAS]->(shelf:Shelf)
                RETURN shelf
                """;
        var records = driver.executableQuery(query).withParameters(Map.of("id", shelfPositionId)).execute().records();
        Shelf shelf = new Shelf();
        records.forEach(record -> {
            Node node = record.get("shelf").asNode();
            shelf.setId(node.elementId());
            shelf.setPartNumber(node.get("partNumber").asString());
            shelf.setName(node.get("name").asString());
        });
        logger.info("Shelf Repository: Shelf reads performed for shelf position ID: {}", shelfPositionId);
        return shelf;
    }
}

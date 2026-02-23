package com.intern.assignment.config;

import org.neo4j.driver.AuthTokens;
import org.neo4j.driver.Driver;
import org.neo4j.driver.GraphDatabase;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class DatabaseConnection {
    private final static Logger logger = LoggerFactory.getLogger(DatabaseConnection.class);
    public static Driver initialise() {
        Driver driver = GraphDatabase.driver(System.getenv("AURADB_URI"),
                AuthTokens.basic(System.getenv("AURADB_USERNAME"), System.getenv("AURADB_PASSWORD"))
        );
        driver.verifyConnectivity();
        logger.info("Database initialised successfully");
        return driver;
    }
}

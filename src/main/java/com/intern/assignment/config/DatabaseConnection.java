package com.intern.assignment.config;

import org.neo4j.driver.AuthTokens;
import org.neo4j.driver.Driver;
import org.neo4j.driver.GraphDatabase;
import org.springframework.stereotype.Component;

@Component
public class DatabaseConnection {
    public static Driver initialise() {
        Driver driver = GraphDatabase.driver(System.getenv("AURADB_URI"),
                AuthTokens.basic(System.getenv("AURADB_USERNAME"), System.getenv("AURADB_PASSWORD"))
        );
        driver.verifyConnectivity();
        return driver;
    }
}

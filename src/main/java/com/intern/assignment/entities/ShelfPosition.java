package com.intern.assignment.entities;

import org.springframework.data.neo4j.core.schema.GeneratedValue;
import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Node;

@Node
public class ShelfPosition {
    @Id @GeneratedValue
    private String id;
}

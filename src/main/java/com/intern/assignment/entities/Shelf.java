package com.intern.assignment.entities;

import lombok.Data;
import org.springframework.data.neo4j.core.schema.GeneratedValue;
import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Node;

@Node
@Data
public class Shelf {
    @Id @GeneratedValue
    private String id;
    private String partNumber;
    private String shelfName;
}

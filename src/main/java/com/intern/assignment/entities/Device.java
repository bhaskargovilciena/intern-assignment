package com.intern.assignment.entities;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.neo4j.core.schema.GeneratedValue;
import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Node;

@Node
@Data
public class Device {
    @Id @GeneratedValue
    private String id;
    private String deviceName;
    private String partNumber;
    private String buildingName;
    private String deviceType;
    private Integer numberOfShelfPositions;
}

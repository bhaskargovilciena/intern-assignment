package com.intern.assignment.entities;

import lombok.Data;

@Data
public class Shelf {
    Boolean isDeleted = false;
    String id;
    String name;
    String partNumber;
}

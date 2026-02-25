package com.intern.assignment.entities;

import lombok.Data;

@Data
public class ShelfPosition {
    String id;
    String deviceId;
    boolean isDeleted = false;
}

package com.intern.assignment.entities;

import lombok.Data;

@Data
public class Device {
    String deviceName;
    String partNumber;
    String buildingName;
    String DeviceType;
    int numberOfShelfPositions;
}

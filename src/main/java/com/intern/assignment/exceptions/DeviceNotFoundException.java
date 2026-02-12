package com.intern.assignment.exceptions;

public class DeviceNotFoundException extends Exception {
    public DeviceNotFoundException(String name) {
        super("could not find device with name: " + name);
    }
}

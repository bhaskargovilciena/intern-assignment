package com.intern.assignment.exceptions;

public class ShelfNotFoundException extends Exception {
    public ShelfNotFoundException(String name) {
        super("could not find shelf with name: " + name);
    }
}

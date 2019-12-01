package com.kolotilov.jplagweb.exceptions;

/**
 * Indicates that entity with given id cannot be found.
 */
public class EntityNotFoundException extends Exception {

    public EntityNotFoundException() {
    }

    public EntityNotFoundException(String message) {
        super(message);
    }
}

package com.kolotilov.jplagweb.exceptions;

/**
 * Indicates that entity with given id or set of attributes already exists.
 */
public class DuplicateEntityException extends Exception {

    public DuplicateEntityException() {
    }

    public DuplicateEntityException(String message) {
        super(message);
    }
}

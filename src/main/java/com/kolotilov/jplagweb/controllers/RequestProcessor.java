package com.kolotilov.jplagweb.controllers;

import com.kolotilov.jplagweb.exceptions.DuplicateEntityException;
import com.kolotilov.jplagweb.exceptions.EntityNotFoundException;

/**
 * processes request.
 * @param <T> Entity type.
 */
public interface RequestProcessor<T> {

    T run() throws EntityNotFoundException, DuplicateEntityException;
}



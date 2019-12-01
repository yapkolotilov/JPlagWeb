package com.kolotilov.jplagweb.controllers;

import com.kolotilov.jplagweb.exceptions.DuplicateEntityException;
import com.kolotilov.jplagweb.exceptions.EntityNotFoundException;

/**
 * processes request with entity.
 * @param <T> Entity type.
 */
public interface RequestProcessorByEntity<T> {

    T run(T entity) throws DuplicateEntityException, EntityNotFoundException;
}

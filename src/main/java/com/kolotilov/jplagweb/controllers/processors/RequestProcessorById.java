package com.kolotilov.jplagweb.controllers.processors;

import com.kolotilov.jplagweb.exceptions.DuplicateEntityException;
import com.kolotilov.jplagweb.exceptions.EntityNotFoundException;

/**
 * Proceeds request with id.
 * @param <T> Entity type.
 * @param <K> Id type.
 */
public interface RequestProcessorById<T, K> {

    T run(K id) throws EntityNotFoundException, DuplicateEntityException;
}

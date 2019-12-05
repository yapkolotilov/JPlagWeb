package com.kolotilov.jplagweb.controllers.processors;

import com.kolotilov.jplagweb.exceptions.DuplicateEntityException;
import com.kolotilov.jplagweb.exceptions.EntityNotFoundException;

public interface RequestProcessorByDoubleParams<T, A, B> {

    T run(A a, B b) throws EntityNotFoundException, DuplicateEntityException;
}

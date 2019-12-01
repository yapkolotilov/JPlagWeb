package com.kolotilov.jplagweb.services;

import com.kolotilov.jplagweb.exceptions.DuplicateEntityException;
import com.kolotilov.jplagweb.exceptions.EntityNotFoundException;

import java.util.List;

/**
 * Base CRUD service.
 * @param <T> Entity type.
 * @param <K> Entity Id type.
 */
public interface EntityService<T, K> {

    /**
     * Returns all entities in the table.
     * @return all entities in the table.
     */
    List<T> getAll();

    /**
     * Returns object by specified id.
     * @param id Entity id.
     * @return object by specified id.
     */
    T getById(K id) throws EntityNotFoundException;

    /**
     * Creates new object and returns it.
     * @param entity Entity to create.
     * @return Created entity with actual id.
     */
    T create(T entity) throws DuplicateEntityException;

    /**
     * Edits entity by id given.
     * @param entity Entity values. Note that id must be specified too.
     * @return New entity status.
     * @throws EntityNotFoundException if Entity was not found.
     */
    T edit(T entity) throws EntityNotFoundException;

    /**
     * Deletes entity by id.
     * @param id Entitie's id.
     * @return Deleted entity.
     * @throws EntityNotFoundException if entity doesn't exist.
     */
    T delete(K id) throws EntityNotFoundException;
}

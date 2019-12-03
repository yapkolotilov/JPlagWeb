package com.kolotilov.jplagweb.services;

import com.kolotilov.jplagweb.exceptions.DuplicateEntityException;
import com.kolotilov.jplagweb.exceptions.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import java.util.List;

public abstract class AbstractService<T, K> {

    @Autowired
    protected JdbcTemplate jdbc;

    protected String selectDuplicatesQuery() {
        return null;
    }

    protected abstract String selectAllQuery();

    protected abstract String selectByIdQuery();

    protected abstract String insertQuery();

    protected abstract String updateQuery();

    protected abstract String deleteQuery();

    protected abstract String entityName();

    protected abstract RowMapper<T> getMapper();


    protected final int getLastId() {
        return jdbc.queryForObject("SELECT LAST_INSERT_ID()", Integer.class);
    }

    protected final void checkDuplicates(Object... args) throws DuplicateEntityException {
        if (jdbc.query(selectDuplicatesQuery(),
                args,
                getMapper()).size() > 0)
            throw new DuplicateEntityException("Entity with given set of attributes already exists!");
    }


    protected final List<T> getAllBase() {
        return jdbc.query(selectAllQuery(),
                getMapper());
    }

    protected final T getByIdBase(K id) throws EntityNotFoundException {
        try {
            return jdbc.query(selectByIdQuery(),
                    new Object[]{ id },
                    getMapper()).get(0);
        } catch (Exception e) {
            throw new EntityNotFoundException(String.format("%s with id %s was not found!",
                    entityName(), id));
        }
    }

    protected final T createBase(T entity, Object... params) throws DuplicateEntityException {
        try {
            jdbc.update(insertQuery(),
                    params);

            return entity;
        } catch (Exception e) {
            throw new DuplicateEntityException(String.format("%s already exists!",
                    entityName()));
        }
    }

    protected final T editBase(T entity, Object... args) throws EntityNotFoundException {
        try {
            if (jdbc.update(updateQuery(),
                    args) == 0)
                throw new Exception();
            return entity;
        } catch (Exception e) {
            throw new EntityNotFoundException("Match not found!");
        }
    }

    protected final T deleteBase(K id) throws EntityNotFoundException {
        T entity = getByIdBase(id);
        jdbc.update(deleteQuery(),
                id);
        return entity;
    }
}

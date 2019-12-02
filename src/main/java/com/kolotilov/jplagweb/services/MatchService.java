package com.kolotilov.jplagweb.services;

import com.kolotilov.jplagweb.exceptions.DuplicateEntityException;
import com.kolotilov.jplagweb.exceptions.EntityNotFoundException;
import com.kolotilov.jplagweb.models.Match;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class MatchService implements EntityService<Match, Integer> {

    //region Queries
    private static final String SELECT_ALL = "";

    private static final String SELECT_BY_ID = "";

    private static final String INSERT = "";

    private static final String UPDATE = "";

    private static final String DELETE = "";
    //endregion

    @Autowired
    private JdbcTemplate jdbc;

    /**
     * Returns all entities in the table.
     *
     * @return all entities in the table.
     */
    @Override
    public List<Match> getAll() {
        return null;
    }

    /**
     * Returns object by specified id.
     *
     * @param id Entity id.
     * @return object by specified id.
     */
    @Override
    public Match getById(Integer id) throws EntityNotFoundException {
        return null;
    }

    /**
     * Creates new object and returns it.
     *
     * @param entity Entity to create.
     * @return Created entity with actual id.
     */
    @Override
    public Match create(Match entity) throws DuplicateEntityException {
        return null;
    }

    /**
     * Edits entity by id given.
     *
     * @param entity Entity values. Note that id must be specified too.
     * @return New entity status.
     * @throws EntityNotFoundException if Entity was not found.
     */
    @Override
    public Match edit(Match entity) throws EntityNotFoundException {
        return null;
    }

    /**
     * Deletes entity by id.
     *
     * @param id Entitie's id.
     * @return Deleted entity.
     * @throws EntityNotFoundException if entity doesn't exist.
     */
    @Override
    public Match delete(Integer id) throws EntityNotFoundException {
        return null;
    }

    private static class MatchMapper implements RowMapper<Match> {

        /**
         * Implementations must implement this method to map each row of data
         * in the ResultSet. This method should not call {@code next()} on
         * the ResultSet; it is only supposed to map values of the current row.
         *
         * @param rs     the ResultSet to map (pre-initialized for the current row)
         * @param rowNum the number of the current row
         * @return the result object for the current row (may be {@code null})
         * @throws SQLException if an SQLException is encountered getting
         *                      column values (that is, there's no need to catch SQLException)
         */
        @Override
        public Match mapRow(ResultSet rs, int rowNum) throws SQLException {
            Match match = new Match();
            match.setId(rs.getInt("Id"));
            match.setName(rs.getString("Name"));
            match.setContent(rs.getNString("Content"));
            match.setTaskId(rs.getInt("TaskId"));
            return match;
        }
    }
}

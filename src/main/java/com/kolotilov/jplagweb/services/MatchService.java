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
    private static final String SELECT_ALL = "SELECT * FROM `match`";

    private static final String SELECT_BY_ID = "SELECT * FROM `match` WHERE Id = ?";

    private static final String SELECT_BY_TASKID = "SELECT * FROM `match` WHERE TaskId = ?";

    private static final String SELECT_BY_TASKID_AND_NAME = "SELECT * FROM `match` WHERE TaskId = ? AND Name = ?";

    private static final String INSERT = "INSERT INTO `match` (Name, Content, TaskId) VALUE (?, ?, ?)";

    private static final String UPDATE = "UPDATE `match` SET Name = ?, Content = ?, TaskId = ? WHERE Id = ?";

    private static final String DELETE = "DELETE FROM `match` WHERE Id = ?";
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
        return jdbc.query(SELECT_ALL,
                new MatchMapper());
    }

    /**
     * Returns object by specified id.
     *
     * @param id Entity id.
     * @return object by specified id.
     */
    @Override
    public Match getById(Integer id) throws EntityNotFoundException {
        try {
            return jdbc.query(SELECT_BY_ID,
                    new Object[]{id},
                    new MatchMapper()).get(0);
        } catch (Exception e) {
            throw new EntityNotFoundException("Match not found!");
        }
    }

    public List<Match> getByTaskId(int taskId) throws EntityNotFoundException {
        return jdbc.query(SELECT_BY_TASKID,
                new Object[]{taskId},
                new MatchMapper());
    }

    /**
     * Creates new object and returns it.
     *
     * @param match Entity to create.
     * @return Created entity with actual id.
     */
    @Override
    public Match create(Match match) throws DuplicateEntityException {
        try {
            if (jdbc.query(SELECT_BY_TASKID_AND_NAME,
                    new Object[]{
                            match.getTaskId(),
                            match.getName()
                    },
                    new MatchMapper()).size() > 0)
                throw new Exception();

            jdbc.update(INSERT,
                    match.getName(),
                    match.getContent(),
                    match.getTaskId()
            );

            match.setId(getLastId());
            return match;
        } catch (Exception e) {
            throw new DuplicateEntityException("Match already exists!");
        }
    }

    /**
     * Edits entity by id given.
     *
     * @param match Entity values. Note that id must be specified too.
     * @return New entity status.
     * @throws EntityNotFoundException if Entity was not found.
     */
    @Override
    public Match edit(Match match) throws EntityNotFoundException {
        try {
            if (jdbc.update(UPDATE,
                    match.getName(),
                    match.getContent(),
                    match.getTaskId(),
                    match.getId()) == 0)
                throw new Exception();
            return match;
        } catch (Exception e) {
            throw new EntityNotFoundException("Match not found!");
        }
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
        try {
            Match match = getById(id);
            jdbc.update(DELETE,
                    id
            );
            return match;
        } catch (Exception e) {
            throw new EntityNotFoundException("Match not found!");
        }
    }

    private int getLastId() {
        return jdbc.queryForObject(SELECT_LAST_INSERT_ID, Integer.class);
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

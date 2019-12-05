package com.kolotilov.jplagweb.services;

import com.kolotilov.jplagweb.exceptions.DuplicateEntityException;
import com.kolotilov.jplagweb.exceptions.EntityNotFoundException;
import com.kolotilov.jplagweb.models.Match;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Service
public class MatchService extends AbstractService<Match, Integer> implements EntityService<Match, Integer> {

    //region Queries
    private static final String SELECT_ALL = "SELECT * FROM `match`";

    private static final String SELECT_BY_ID = "SELECT * FROM `match` WHERE Id = ?";

    private static final String SELECT_BY_TASKID = "SELECT * FROM `match` WHERE TaskId = ?";

    private static final String SELECT_BY_TASKID_AND_NAME = "SELECT * FROM `match` WHERE TaskId = ? AND Name = ?";

    private static final String INSERT = "INSERT INTO `match` (Name, Content, TaskId) VALUE (?, ?, ?)";

    private static final String UPDATE = "UPDATE `match` SET Name = ?, Content = ?, TaskId = ? WHERE Id = ?";

    private static final String DELETE = "DELETE FROM `match` WHERE Id = ?";
    //endregion

    /**
     * Returns all entities in the table.
     *
     * @return all entities in the table.
     */
    @Override
    public List<Match> getAll() {
        return getAllBase();
    }

    /**
     * Returns object by specified id.
     *
     * @param id Entity id.
     * @return object by specified id.
     */
    @Override
    public Match getById(Integer id) throws EntityNotFoundException {
        return getByIdBase(id);
    }

    public Match getByTaskIdAndName(int taskId, String name) throws EntityNotFoundException {
        try {
            return jdbc.query(SELECT_BY_TASKID_AND_NAME,
                    new Object[]{taskId, name},
                    new MatchMapper()).get(0);
        } catch (Exception e) {
            throw new EntityNotFoundException("Match not found!");
        }
    }

    public List<Match> getByTaskId(int taskId) {
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
        checkDuplicates(
                match.getTaskId(),
                match.getName());
        return createBase(match,
                match.getName(),
                match.getContent(),
                match.getTaskId());
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
        return editBase(match,
                match.getName(),
                match.getContent(),
                match.getTaskId(),
                match.getId());
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
        return deleteBase(id);
    }

    @Override
    protected String selectAllQuery() {
        return SELECT_ALL;
    }

    @Override
    protected String selectByIdQuery() {
        return null;
    }

    @Override
    protected String insertQuery() {
        return null;
    }

    @Override
    protected String updateQuery() {
        return null;
    }

    @Override
    protected String deleteQuery() {
        return null;
    }

    @Override
    protected String entityName() {
        return null;
    }

    @Override
    protected RowMapper<Match> getMapper() {
        return null;
    }

    @Override
    protected String selectDuplicatesQuery() {
        return SELECT_BY_TASKID_AND_NAME;
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

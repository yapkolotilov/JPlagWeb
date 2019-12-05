package com.kolotilov.jplagweb.services;

import com.kolotilov.jplagweb.exceptions.DuplicateEntityException;
import com.kolotilov.jplagweb.exceptions.EntityNotFoundException;
import com.kolotilov.jplagweb.models.Match;
import com.kolotilov.jplagweb.models.MatchPart;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Service
public class MatchPartService extends AbstractService<MatchPart, Integer> implements EntityService<MatchPart, Integer> {

    //region Queries
    private static final String SELECT_DUPLICATES = "SELECT * FROM MatchPart WHERE MatchId = ? AND Name = ?";

    private static final String SELECT_ALL = "SELECT * FROM MatchPart";

    private static final String SELECT_BY_ID = "SELECT * FROM MatchPart WHERE Id = ?";

    private static final String SELECT_BY_TASKID_AND_NAME = "SELECT * FROM MatchPart WHERE TaskId = ? AND Name = ?";

    private static final String INSERT = "INSERT INTO MatchPart (Name, Content, TaskId, MatchId) VALUE (?, ?, ?, ?)";

    private static final String UPDATE = "UPDATE MatchPart SET Name = ?, Content = ?, TaskId = ?, MatchId = ? WHERE Id = ?";

    private static final String DELETE = "DELETE FROM MatchPart WHERE Id = ?";
    //endregion


    @Override
    public List<MatchPart> getAll() {
        return getAllBase();
    }

    @Override
    public MatchPart getById(Integer id) throws EntityNotFoundException {
        return getById(id);
    }

    @Override
    public MatchPart create(MatchPart matchPart) throws DuplicateEntityException {
        checkDuplicates(matchPart.getTaskId(), matchPart.getName());
        createBase(matchPart,
                matchPart.getName(),
                matchPart.getContent(),
                matchPart.getTaskId(),
                matchPart.getMatchId());
        matchPart.setId(getLastId());

        return matchPart;
    }

    @Override
    public MatchPart edit(MatchPart matchPart) throws EntityNotFoundException {
        return editBase(matchPart,
                matchPart.getName(),
                matchPart.getContent(),
                matchPart.getTaskId(),
                matchPart.getMatchId(),
                matchPart.getId());
    }

    @Override
    public MatchPart delete(Integer id) throws EntityNotFoundException {
        return deleteBase(id);
    }

    public MatchPart getByTaskIdAndName(int taskId, String name) throws EntityNotFoundException {
        try {
            return jdbc.query(SELECT_BY_TASKID_AND_NAME,
                    new Object[]{taskId, name},
                    getMapper()).get(0);
        } catch (Exception e) {
            throw new EntityNotFoundException("MatchPart not found!");
        }
    }


    @Override
    protected String selectAllQuery() {
        return SELECT_ALL;
    }

    @Override
    protected String selectByIdQuery() {
        return SELECT_BY_ID;
    }

    @Override
    protected String insertQuery() {
        return INSERT;
    }

    @Override
    protected String updateQuery() {
        return UPDATE;
    }

    @Override
    protected String deleteQuery() {
        return DELETE;
    }

    @Override
    protected String entityName() {
        return "MatchPart";
    }

    @Override
    protected RowMapper<MatchPart> getMapper() {
        return new MatchMapper();
    }

    @Override
    protected String selectDuplicatesQuery() {
        return SELECT_DUPLICATES;
    }

    private static class MatchMapper implements RowMapper<MatchPart> {

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
        public MatchPart mapRow(ResultSet rs, int rowNum) throws SQLException {
            MatchPart match = new MatchPart();
            match.setId(rs.getInt("Id"));
            match.setName(rs.getString("Name"));
            match.setContent(rs.getNString("Content"));
            match.setTaskId(rs.getInt("TaskId"));
            match.setMatchId(rs.getInt("MatchId"));
            return match;
        }
    }
}

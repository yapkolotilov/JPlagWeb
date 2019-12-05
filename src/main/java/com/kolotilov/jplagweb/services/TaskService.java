package com.kolotilov.jplagweb.services;

import com.kolotilov.jplagweb.exceptions.DuplicateEntityException;
import com.kolotilov.jplagweb.exceptions.EntityNotFoundException;
import com.kolotilov.jplagweb.models.Task;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 * Service for tasks.
 */
@Service
public class TaskService extends AbstractService<Task, Integer> implements EntityService<Task, Integer> {

    //region Queries
    private static String SELECT_ALL = "SELECT * FROM Task";

    private static String SELECT_BY_ID = "SELECT * FROM Task WHERE Id = ?";

    private static String SELECT_BY_USERNAME_AND_NAME = "SELECT * FROM Task WHERE UserUsername = ? AND Name = ?";

    private static String SELECT_BY_USERNAME = "SELECT * FROM Task WHERE UserUsername = ?";

    private static String INSERT = "INSERT INTO Task (Name, Description, UserUsername) VALUE (?, ?, ?)";

    private static String UPDATE = "UPDATE Task SET Name = ?, Description = ? WHERE Id = ?";

    private static String DELETE = "DELETE FROM Task WHERE Id = ?";
    //endregion

    @Override
    public List<Task> getAll() {
        return getAllBase();
    }

    public List<Task> getByUsername(String username) {
        return jdbc.query(SELECT_BY_USERNAME,
                new Object[]{username},
                new TaskMapper());
    }

    @Override
    public Task getById(Integer id) throws EntityNotFoundException {
        return getByIdBase(id);
    }

    @Override
    public Task create(Task task) throws DuplicateEntityException {
        checkDuplicates(
                task.getUserUsername(),
                task.getName());
        createBase(task,
                task.getName(),
                task.getDescription(),
                task.getUserUsername());
        task.setId(getLastId());
        return task;
    }

    @Override
    public Task edit(Task task) throws EntityNotFoundException {
        return editBase(task,
                task.getName(),
                task.getDescription(),
                task.getId());
    }

    @Override
    public Task delete(Integer id) throws EntityNotFoundException {
        return deleteBase(id);
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
        return "Task";
    }

    @Override
    protected RowMapper<Task> getMapper() {
        return new TaskMapper();
    }

    @Override
    protected String selectDuplicatesQuery() {
        return SELECT_BY_USERNAME_AND_NAME;
    }

    private static class TaskMapper implements RowMapper<Task> {

        @Override
        public Task mapRow(ResultSet rs, int rowNum) throws SQLException {
            Task result = new Task(
                    rs.getString("Name"),
                    rs.getNString("Description"),
                    rs.getString("UserUsername")
            );
            result.setId(rs.getInt("Id"));
            return result;
        }
    }
}

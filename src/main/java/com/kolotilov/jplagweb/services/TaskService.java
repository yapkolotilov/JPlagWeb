package com.kolotilov.jplagweb.services;

import com.kolotilov.jplagweb.exceptions.DuplicateEntityException;
import com.kolotilov.jplagweb.exceptions.EntityNotFoundException;
import com.kolotilov.jplagweb.models.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 * Service for tasks.
 */
@Service
public class TaskService implements EntityService<Task, Integer> {

    //region Queries
    private static String SELECT_ALL = "SELECT * FROM Task";

    private static String SELECT_BY_ID = "SELECT * FROM Task WHERE Id = ?";

    private static String SELECT_BY_USERNAME_AND_NAME = "SELECT * FROM Task WHERE UserUsername = ? AND Name = ?";

    private static String SELECT_BY_USERNAME = "SELECT * FROM Task WHERE UserUsername = ?";

    private static String INSERT = "INSERT INTO Task (Name, Description, UserUsername) VALUE (?, ?, ?)";

    private static String UPDATE = "UPDATE Task SET Name = ?, Description = ? WHERE Id = ?";

    private static String DELETE = "DELETE FROM Task WHERE Id = ?";
    //endregion

    @Autowired
    private JdbcTemplate jdbc;

    @Override
    public List<Task> getAll() {
        return jdbc.query(SELECT_ALL,
                new TaskMapper()
        );
    }

    @Override
    public Task getById(Integer id) throws EntityNotFoundException {
        try {
            return jdbc.query(SELECT_BY_ID,
                    new Object[]{id},
                    new TaskMapper()).get(0);
        } catch (Exception e) {
            throw new EntityNotFoundException("Task not found!");
        }
    }

    public List<Task> getByUsername(String username) {
        return jdbc.query(SELECT_BY_USERNAME,
                new TaskMapper());
    }

    @Override
    public Task create(Task task) throws DuplicateEntityException {
        try {
            if (jdbc.query(SELECT_BY_USERNAME_AND_NAME,
                    new Object[] {
                            task.getUserUsername(),
                            task.getName()
                        },
                    new TaskMapper()).size() > 0)
                throw new DuplicateEntityException("Task already exists!");
            jdbc.update(INSERT,
                    task.getName(),
                    task.getDescription(),
                    task.getUserUsername()
            );
            task.setId(getLastId());
            return task;
        } catch (Exception e) {
            throw new DuplicateEntityException("Task already exists!");
        }
    }

    @Override
    public Task edit(Task task) throws EntityNotFoundException {
        try {
            int rows = jdbc.update(UPDATE,
                    task.getName(),
                    task.getDescription(),
                    task.getId()
            );
            if (rows == 0)
                throw new EntityNotFoundException("Task not found!");
            return task;
        } catch (Exception e) {
            throw new EntityNotFoundException("Task not found!");
        }
    }

    @Override
    public Task delete(Integer id) throws EntityNotFoundException {
        try {
            Task task = getById(id);
            jdbc.update(DELETE,
                    id
            );
            return task;
        } catch (Exception e) {
            throw new EntityNotFoundException("User not found!");
        }
    }

    private int getLastId() {
        return jdbc.queryForObject(SELECT_LAST_INSERT_ID, Integer.class);
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

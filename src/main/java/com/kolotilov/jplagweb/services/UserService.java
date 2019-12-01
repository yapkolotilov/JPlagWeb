package com.kolotilov.jplagweb.services;

import com.kolotilov.jplagweb.exceptions.DuplicateEntityException;
import com.kolotilov.jplagweb.exceptions.EntityNotFoundException;
import com.kolotilov.jplagweb.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.IncorrectResultSetColumnCountException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import javax.validation.ConstraintViolationException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 * User service.
 */
public class UserService implements EntityService<User, String> {

    //region Queries
    private static String SELECT_ALL = "SELECT * FROM User";

    private static String SELECT_BY_ID = "SELECT * FROM User WHERE Username = ?";

    private static String INSERT = "INSERT INTO User (Username, Password, Name) VALUE (?, ?, ?)";

    private static String UPDATE = "UPDATE User SET Password = ?, Name = ? WHERE Username = ?";

    private static String DELETE = "DELETE FROM User WHERE Username = ?";
    //endregion

    @Autowired
    private JdbcTemplate jdbc;

    /**
     * Returns all entities in the table.
     *
     * @return all entities in the table.
     */
    @Override
    public List<User> getAll() {
        return jdbc.query(SELECT_ALL, new UserMapper());
    }

    /**
     * Returns object by specified id.
     *
     * @param id Entity id.
     * @return object by specified id.
     */
    @Override
    public User getById(String id) throws EntityNotFoundException {
        try {
            return jdbc.queryForObject(SELECT_BY_ID,
                    new Object[]{id},
                    new UserMapper());
        } catch (IncorrectResultSetColumnCountException e) {
            throw new EntityNotFoundException("User not found!");
        }
    }

    /**
     * Creates new object and returns it.
     *
     * @param user Entity to create.
     * @return Created entity with actual id.
     */
    @Override
    public User create(User user) throws DuplicateEntityException {
        try {
            jdbc.update(INSERT,
                    user.getUsername(),
                    user.getPassword(),
                    user.getName());
            return user;
        } catch (ConstraintViolationException e) {
            throw new DuplicateEntityException("User already exists!");
        }
    }

    /**
     * Edits entity by id given.
     *
     * @param user Entity values. Note that id must be specified too.
     * @return New entity status.
     * @throws EntityNotFoundException if Entity was not found.
     */
    @Override
    public User edit(User user) throws EntityNotFoundException {
        int rows = jdbc.update(UPDATE,
                user.getPassword(),
                user.getUsername(),
                user.getUsername()
        );
        if (rows == 0)
            throw new EntityNotFoundException("User not found!");
        return user;
    }

    /**
     * Deletes entity by id.
     *
     * @param id Entitie's id.
     * @return Deleted entity.
     * @throws EntityNotFoundException if entity doesn't exist.
     */
    @Override
    public void delete(String id) throws EntityNotFoundException {
        int rows = jdbc.update(DELETE,
                id
        );
        if (rows == 0)
            throw new EntityNotFoundException("User not found!");
    }

    private static class UserMapper implements RowMapper<User> {

        @Override
        public User mapRow(ResultSet rs, int rowNum) throws SQLException {
            return new User(
                    rs.getString("Username"),
                    rs.getString("Password"),
                    rs.getNString("Name")
            );
        }
    }
}

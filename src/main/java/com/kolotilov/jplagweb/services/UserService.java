package com.kolotilov.jplagweb.services;

import com.kolotilov.jplagweb.exceptions.DuplicateEntityException;
import com.kolotilov.jplagweb.exceptions.EntityNotFoundException;
import com.kolotilov.jplagweb.models.User;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 * User service.
 */
@Service
public class UserService extends AbstractService<User, String> implements EntityService<User, String> {

    //region Queries
    private static String SELECT_ALL = "SELECT * FROM User";

    private static String SELECT_BY_ID = "SELECT * FROM User WHERE Username = ?";

    private static String INSERT = "INSERT INTO User (Username, Password, Name) VALUE (?, ?, ?)";

    private static String UPDATE = "UPDATE User SET Password = ?, Name = ? WHERE Username = ?";

    private static String DELETE = "DELETE FROM User WHERE Username = ?";
    //endregion

    /**
     * Returns all entities in the table.
     *
     * @return all entities in the table.
     */
    @Override
    public List<User> getAll() {
        return getAllBase();
    }

    /**
     * Returns object by specified id.
     *
     * @param username Entity id.
     * @return object by specified id.
     */
    @Override
    public User getById(String username) throws EntityNotFoundException {
        return getByIdBase(username);
    }

    /**
     * Creates new object and returns it.
     *
     * @param user Entity to create.
     * @return Created entity with actual id.
     */
    @Override
    public User create(User user) throws DuplicateEntityException {
        return createBase(user,
                user.getUsername(),
                user.getPassword(),
                user.getName());
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
        return editBase(user,
                user.getPassword(),
                user.getName(),
                user.getUsername());
    }

    /**
     * Deletes entity by id.
     *
     * @param id Entitie's id.
     * @return Deleted entity.
     * @throws EntityNotFoundException if entity doesn't exist.
     */
    @Override
    public User delete(String id) throws EntityNotFoundException {
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
        return "User";
    }

    @Override
    protected RowMapper<User> getMapper() {
        return new UserMapper();
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

package by.gruca.cafe.dao.impl;

import by.gruca.cafe.util.ModelMapper;
import by.gruca.cafe.dao.RoleDAO;
import by.gruca.cafe.dao.connectionpool.ConnectionProxy;
import by.gruca.cafe.dao.connectionpool.SQLConnectionPool;
import by.gruca.cafe.dao.exception.DAOException;
import by.gruca.cafe.model.Role;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class RoleDAOImpl implements RoleDAO {
    public static final String SQL_STATEMENT_ERROR = "SQL statement error";
    public static final String ROLE_ROLE_NAME = "role_name";
    private static final String SQL_READ = "SELECT * FROM role WHERE role_id ";
    private static final String SQL_READ_ALL = "SELECT * from role";
    private static final String SQL_CREATE = "INSERT INTO role (role_id,role_name) " +
            "VALUES (DEFAULT,?)";
    private static final String SQL_UPDATE = "UPDATE role SET role_name=? WHERE role_id=?";
    private static final String SQL_DELETE = "DELETE FROM role WHERE role_id=? ";
    private static final long CREATE_ERROR = -1;
    Logger logger = LogManager.getLogger(RoleDAOImpl.class);

    @Override
    public long create(Role entity) throws DAOException {
        try (ConnectionProxy connection = SQLConnectionPool.INSTANCE.getConnection();
             PreparedStatement statement = connection.prepareStatement(SQL_CREATE, Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, entity.getRoleValue());
            statement.execute();
            ResultSet resultSet = statement.getGeneratedKeys();
            if (resultSet.next()) {
                return resultSet.getLong(1);
            }
            return CREATE_ERROR;

        } catch (SQLException e) {
            logger.error(e);
            throw new DAOException(SQL_STATEMENT_ERROR, e);
        }
    }

    @Override
    public Optional<Role> read(long id) throws DAOException {
        Role role = null;
        try (ConnectionProxy connection = SQLConnectionPool.INSTANCE.getConnection();
             PreparedStatement readStatement = connection.prepareStatement(SQL_READ)) {
            readStatement.setLong(1, id);
            role = buildRole(readStatement);
        } catch (SQLException e) {
            logger.error(e);
            throw new DAOException(SQL_STATEMENT_ERROR, e);
        }
        return Optional.ofNullable(role);

    }


    @Override
    public int update(Role entity) throws DAOException {
        try (ConnectionProxy connection = SQLConnectionPool.INSTANCE.getConnection();
             PreparedStatement statement = connection.prepareStatement(SQL_UPDATE)) {
            statement.setString(1, entity.getRoleValue());
            statement.setLong(2, entity.getId());
            int rowsAffected = statement.executeUpdate();
            return statement.executeUpdate();

        } catch (SQLException e) {
            logger.error(e);
            throw new DAOException(SQL_STATEMENT_ERROR, e);
        }
    }

    @Override
    public boolean delete(Role entity) throws DAOException {
        try (ConnectionProxy connection = SQLConnectionPool.INSTANCE.getConnection();
             PreparedStatement statement = connection.prepareStatement(SQL_DELETE)) {
            statement.setLong(1, entity.getId());
            return statement.execute();
        } catch (SQLException e) {
            logger.error(e);
            throw new DAOException(SQL_STATEMENT_ERROR, e);
        }
    }

    @Override
    public List<Role> readAll() throws DAOException {
        List<Role> roles;
        try (ConnectionProxy connection = SQLConnectionPool.INSTANCE.getConnection();
             PreparedStatement statement = connection.prepareStatement(SQL_READ_ALL);) {
            roles = buildRoleList(statement);
        } catch (SQLException e) {
            logger.error(e);
            throw new DAOException(SQL_STATEMENT_ERROR, e);
        }
        return roles;
    }


    private Role buildRole(PreparedStatement readStatement) throws DAOException {
        Role role = null;
        try (ResultSet rs = readStatement.executeQuery()) {
            if (rs.next()) {
                role = ModelMapper.INSTANCE.getRoleFromResultSet(rs);
            }
        } catch (SQLException e) {
            logger.error(e);
            throw new DAOException(e);
        }
        return role;
    }

    private List<Role> buildRoleList(PreparedStatement readStatement) throws DAOException {
        List<Role> roles = new ArrayList<>();
        Role role;
        try (ResultSet rs = readStatement.executeQuery()) {
            while (rs.next()) {
                role = ModelMapper.INSTANCE.getRoleFromResultSet(rs);
                roles.add(role);
            }
        } catch (SQLException e) {
            logger.error(e);
            throw new DAOException(e);
        }
        return roles;
    }
}

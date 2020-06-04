package by.gruca.cafe.dao.impl;


import by.gruca.cafe.dao.AccountDAO;
import by.gruca.cafe.dao.connectionpool.ConnectionProxy;
import by.gruca.cafe.dao.connectionpool.SQLConnectionPool;
import by.gruca.cafe.dao.exception.DAOException;
import by.gruca.cafe.model.Account;
import by.gruca.cafe.model.Role;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

public class AccountDAOImpl implements AccountDAO {
    private static final String ACCOUNT_ID = "id";
    private static final String ACCOUNT_LOGIN = "login";
    private static final String ACCOUNT_PASSWORD = "password";
    private static final String ACCOUNT_EMAIL = "email";
    private static final String ACCOUNT_FIRSTNAME = "firstname";
    private static final String ACCOUNT_LASTNAME = "lastname";
    private static final String ACCOUNT_ROLE_ID = "role_id";
    private static final String ACCOUNT_IS_ENABLED = "is_enabled";
    private static final String ACCOUNT_ROLE = "role";

    private static final String SQL_GET = "select * from account join role on role_id=role.id where login=? ";
    private static final String SQL_GET_ALL = "select * from account";
    private static final String SQL_SAVE = "insert into account(id,login,password,email,firstname,lastname,role_id,is_enabled) " +
            "values(DEFAULT,?,?,?,?,?,?,?)";
    private static final String SQL_UPDATE = "update account set login=?,password=?,email=?" +
            ",firstname=?,lastname=?,role_id=?, is_enabled=? where id=?";
    private static final String SQL_DELETE = "delete from account where id=? ";
    private static final String SQL_GET_ROLE = "select role from role where id=?";

    Logger logger = LogManager.getLogger(AccountDAO.class);

    @Override
    public boolean create(Account account) throws DAOException {
        try (ConnectionProxy connection = SQLConnectionPool.INSTANCE.getConnection();
             PreparedStatement statement = connection.prepareStatement(SQL_SAVE)) {
            statement.setString(1, account.getLogin());
            statement.setString(2, account.getPassword());
            statement.setString(3, account.getEmail());
            statement.setString(4, account.getFirstName());
            statement.setString(5, account.getLastName());
            statement.setInt(6, 3);
            statement.setBoolean(7, account.isEnabled());
            return statement.execute();
        } catch (SQLException e) {
             logger.error(e);
            throw new DAOException("SQL statement error", e);
        }

    }

    @Override
    public Optional<Account> read(String login) throws DAOException {
        Account account = new Account();
        try (ConnectionProxy connection = SQLConnectionPool.INSTANCE.getConnection();
             PreparedStatement readStatement = connection.prepareStatement(SQL_GET)) {
            readStatement.setString(1, login);
            try (ResultSet rs = readStatement.executeQuery()) {
                if (rs.next()) {
                    account.setId(rs.getInt(ACCOUNT_ID));
                    account.setLogin(rs.getNString(ACCOUNT_LOGIN));
                    account.setPassword(rs.getNString(ACCOUNT_PASSWORD));
                    account.setEmail(rs.getNString(ACCOUNT_EMAIL));
                    account.setFirstName(rs.getNString(ACCOUNT_FIRSTNAME));
                    account.setLastName(rs.getNString(ACCOUNT_LASTNAME));
                    account.setEnabled(rs.getBoolean(ACCOUNT_IS_ENABLED));
                    account.setRole(new Role(rs.getInt(ACCOUNT_ROLE_ID), rs.getNString(ACCOUNT_ROLE)));
                } else throw new DAOException("Account is not exist");
            } catch (SQLException e) {
                logger.error(e);
            }
        } catch (SQLException e) {
            logger.error(e);
            throw new DAOException("SQL statement error", e);
        }
        return Optional.of(account);
    }


    @Override
    public int update(Account account) throws DAOException {
        try (ConnectionProxy connection = SQLConnectionPool.INSTANCE.getConnection();
             PreparedStatement statement = connection.prepareStatement(SQL_UPDATE)) {
            statement.setString(1, account.getLogin());
            statement.setString(2, account.getPassword());
            statement.setString(3, account.getEmail());
            statement.setString(4, account.getFirstName());
            statement.setString(5, account.getLastName());
            statement.setInt(6, account.getRole().getRoleId());
            statement.setBoolean(7, account.isEnabled());
            statement.setInt(8, account.getId());
            int rowsAffected = statement.executeUpdate();
            logger.info(rowsAffected + " row affected");
            return rowsAffected;

        } catch (SQLException e) {
            logger.error(e);
            throw new DAOException("SQL statement error", e);
        }

    }

    @Override
    public boolean delete(Account account) throws DAOException {
        try (ConnectionProxy connection = SQLConnectionPool.INSTANCE.getConnection();
             PreparedStatement statement = connection.prepareStatement(SQL_DELETE);) {
            statement.setInt(1, account.getId());
            return statement.execute();
        } catch (SQLException e) {
            logger.error(e);
            throw new DAOException("SQL statement error", e);
        }

    }

//
}

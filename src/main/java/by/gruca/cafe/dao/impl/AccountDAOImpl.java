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
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class AccountDAOImpl implements AccountDAO {
    private static final String ACCOUNT_ID = "id";
    private static final String ACCOUNT_PHONE_NUMBER = "phone_number";
    private static final String ACCOUNT_PASSWORD = "password";
    private static final String ACCOUNT_EMAIL = "email";
    private static final String ACCOUNT_FIRSTNAME = "firstname";
    private static final String ACCOUNT_ROLE = "role";
    private static final String ACCOUNT_BONUS_POINTS = "bonus_points";
    private static final String SQL_GET = "select * from account where email=? ";
    private static final String SQL_GET_ALL = "select * from account";
    private static final String SQL_CREATE = "insert into account (id,phone_number,password,email,firstname,role,bonus_points) " +
            "values(DEFAULT,?,?,?,?,?,?)";
    private static final String SQL_UPDATE = "update account set phone_number=?,password=?,email=?" +
            ",firstname=?, role=?, bonus_points=? where id=?";
    private static final String SQL_DELETE = "delete from account where email=? ";
    Logger logger = LogManager.getLogger(AccountDAO.class);


    public AccountDAOImpl() {
    }

    @Override
    public List<Account> getAllAccounts() throws DAOException {
        List<Account> accounts = new ArrayList<Account>();
        try (ConnectionProxy connection = SQLConnectionPool.INSTANCE.getConnection();
             PreparedStatement readStatement = connection.prepareStatement(SQL_GET_ALL)) {
            try (ResultSet resultSet = readStatement.executeQuery()) {
                while (resultSet.next()) {
                    Account account = new Account();
                    account.setId(resultSet.getInt(ACCOUNT_ID));
                    account.setPhoneNumber(resultSet.getLong(ACCOUNT_PHONE_NUMBER));
                    account.setPassword(resultSet.getNString(ACCOUNT_PASSWORD));
                    account.setEmail(resultSet.getNString(ACCOUNT_EMAIL));
                    account.setFirstName(resultSet.getNString(ACCOUNT_FIRSTNAME));
                    account.setBonusPoints(resultSet.getInt(ACCOUNT_BONUS_POINTS));
                    accounts.add(account);
                    for (Role role : Role.values()) {
                        if (role.getRoleValue().equals(resultSet.getString(ACCOUNT_ROLE))) {
                            account.setRole(role);
                        }
                    }
                }
            } catch (SQLException e) {
                logger.error(e);
                throw new DAOException("SQL statement error", e);
            }
        } catch (SQLException e) {
            logger.error(e);
            throw new DAOException("SQL statement error", e);
        }
        return accounts;
    }

    @Override
    public boolean create(Account account) throws DAOException {
        try (ConnectionProxy connection = SQLConnectionPool.INSTANCE.getConnection();
             PreparedStatement statement = connection.prepareStatement(SQL_CREATE)) {
            statement.setLong(1, account.getPhoneNumber());
            statement.setString(2, account.getPassword());
            statement.setString(3, account.getEmail());
            statement.setString(4, account.getFirstName());
            statement.setString(5, Role.USER.getRoleValue());
            statement.setInt(6, account.getBonusPoints());
            return statement.execute();
        } catch (SQLException e) {
            logger.error(e);
            throw new DAOException("SQL statement error", e);
        }
    }

    @Override
    public Optional<Account> read(String email) throws DAOException {
        Account account = new Account();
        try (ConnectionProxy connection = SQLConnectionPool.INSTANCE.getConnection();
             PreparedStatement readStatement = connection.prepareStatement(SQL_GET)) {
            readStatement.setString(1, email);
            try (ResultSet rs = readStatement.executeQuery()) {
                if (rs.next()) {
                    account.setId(rs.getInt(ACCOUNT_ID));
                    account.setPhoneNumber(rs.getLong(ACCOUNT_PHONE_NUMBER));
                    account.setPassword(rs.getNString(ACCOUNT_PASSWORD));
                    account.setEmail(rs.getNString(ACCOUNT_EMAIL));
                    account.setFirstName(rs.getNString(ACCOUNT_FIRSTNAME));
                    account.setBonusPoints(rs.getInt(ACCOUNT_BONUS_POINTS));
                    for (Role role : Role.values()) {
                        if (role.getRoleValue().equals(rs.getString(ACCOUNT_ROLE))) {
                            account.setRole(role);
                        }
                    }
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
            statement.setLong(1, account.getPhoneNumber());
            statement.setString(2, account.getPassword());
            statement.setString(3, account.getEmail());
            statement.setString(4, account.getFirstName());
            statement.setString(5, account.getRole().getRoleValue());
            statement.setInt(6, account.getBonusPoints());
            statement.setInt(7, account.getId());
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

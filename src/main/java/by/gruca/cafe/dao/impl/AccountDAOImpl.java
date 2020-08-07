package by.gruca.cafe.dao.impl;


import by.gruca.cafe.dao.AccountDAO;
import by.gruca.cafe.util.ModelMapper;
import by.gruca.cafe.dao.connectionpool.ConnectionProxy;
import by.gruca.cafe.dao.connectionpool.SQLConnectionPool;
import by.gruca.cafe.dao.exception.DAOException;
import by.gruca.cafe.model.Account;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class AccountDAOImpl implements AccountDAO {

    public static final String ACCOUNT_ID = "account_id";
    public static final String ACCOUNT_PHONE_NUMBER = "phone_number";
    public static final String ACCOUNT_PASSWORD = "password";
    public static final String ACCOUNT_EMAIL = "email";
    public static final String ACCOUNT_FIRST_NAME = "first_name";
    public static final String ACCOUNT_ROLE = "role_id";
    public static final String ACCOUNT_BALANCE = "balance";
    public static final String ACCOUNT_BONUS = "bonus";
    private static final String SQL_READ_ACCOUNTS_COUNT = "SELECT COUNT(*) accounts_count FROM account";
    public static final String SQL_READ_BY_EMAIL = "SELECT * FROM account " +
            "JOIN role r on account.role_id = r.role_id where account.email=? ";
    public static final String SQL_READ = "SELECT * FROM account " +
            "JOIN role r on account.role_id = r.role_id where account_id=?";
    public static final String SQL_READ_ALL = "SELECT * FROM account" +
            " JOIN role r on account.role_id = r.role_id ORDER BY account.account_id";
    public static final String SQL_CREATE = "INSERT INTO account (account_id,role_id,email,password," +
            "first_name,phone_number,balance,bonus) " +
            "VALUES (DEFAULT,?,?,?,?,?,?,?)";
    public static final String SQL_UPDATE = "UPDATE account SET role_id=?,email=?," +
            "password=?,first_name=?,phone_number=?,balance=?,bonus=? WHERE account_id=?";
    public static final String SQL_DELETE = "DELETE FROM account WHERE account_id=? ";
    public static final String SQL_STATEMENT_ERROR = "SQL statement error";
    public static final String ACCOUNTS_COUNT = "accounts_count";
    public static final String SQL_READ_PAGINATED_ACCOUNTS = "SELECT * FROM account" +
            " JOIN role r on account.role_id = r.role_id ORDER BY account.account_id" +
            " LIMIT ? OFFSET ?";

    Logger logger = LogManager.getLogger(AccountDAOImpl.class);


    @Override
    public Optional<Account> read(long id) throws DAOException {
        Account account;
        try (ConnectionProxy connection = SQLConnectionPool.INSTANCE.getConnection();
             PreparedStatement readStatement = connection.prepareStatement(SQL_READ)) {
            readStatement.setLong(1, id);
            account = buildAccount(readStatement);
        } catch (SQLException e) {
            logger.error(e);
            throw new DAOException(SQL_STATEMENT_ERROR, e);
        }
        return Optional.ofNullable(account);
    }

    @Override
    public List<Account> readAll() throws DAOException {
        List<Account> accounts;
        try (ConnectionProxy connection = SQLConnectionPool.INSTANCE.getConnection();
             PreparedStatement readStatement = connection.prepareStatement(SQL_READ_ALL);) {
            accounts = buildAccountList(readStatement);
        } catch (SQLException e) {
            logger.error(e);
            throw new DAOException(SQL_STATEMENT_ERROR, e);
        }
        return accounts;
    }

    @Override
    public List<Account> readAll(int itemsPerPage, int pageNumber) throws DAOException {
        List<Account> accounts;
        try (ConnectionProxy connection = SQLConnectionPool.INSTANCE.getConnection();
             PreparedStatement statement =
                     connection.prepareStatement(SQL_READ_PAGINATED_ACCOUNTS);) {
            statement.setInt(1, itemsPerPage);
            statement.setInt(2, (pageNumber - 1) * itemsPerPage);
            accounts = buildAccountList(statement);
        } catch (SQLException e) {
            logger.error(e);
            throw new DAOException(SQL_STATEMENT_ERROR, e);
        }
        return accounts;
    }

    @Override
    public int readAccountCount() throws DAOException {
        int accountsCount = 0;
        try (ConnectionProxy connection = SQLConnectionPool.INSTANCE.getConnection();
             PreparedStatement statement = connection.prepareStatement(SQL_READ_ACCOUNTS_COUNT);
             ResultSet rs = statement.executeQuery();) {
            if (rs.next()) {
                accountsCount = rs.getInt(ACCOUNTS_COUNT);
            }
        } catch (SQLException e) {
            logger.error(e);
            throw new DAOException(SQL_STATEMENT_ERROR, e);
        }
        return accountsCount;
    }

    @Override
    public long create(Account entity) throws DAOException {
        try (ConnectionProxy connection = SQLConnectionPool.INSTANCE.getConnection();
             PreparedStatement statement = connection.prepareStatement(SQL_CREATE, Statement.RETURN_GENERATED_KEYS)) {
            statement.setInt(1, entity.getRole().getId());
            statement.setString(2, entity.getEmail());
            statement.setString(3, entity.getPassword());
            statement.setString(4, entity.getFirstName());
            statement.setString(5, entity.getPhoneNumber());
            statement.setBigDecimal(6, entity.getBalance());
            statement.setInt(7, entity.getBonus());
            statement.execute();
            ResultSet resultSet = statement.getGeneratedKeys();
            if (resultSet.next()) {
                return resultSet.getLong(1);
            }
            return -1;

        } catch (SQLException e) {
            logger.error(e);
            throw new DAOException(SQL_STATEMENT_ERROR, e);
        }
    }

    @Override
    public Optional<Account> read(String email) throws DAOException {
        Account account;
        try (ConnectionProxy connection = SQLConnectionPool.INSTANCE.getConnection();
             PreparedStatement readStatement = connection.prepareStatement(SQL_READ_BY_EMAIL)) {
            readStatement.setString(1, email);
            account = buildAccount(readStatement);
        } catch (SQLException e) {
            logger.error(e);
            throw new DAOException(SQL_STATEMENT_ERROR, e);
        }
        return Optional.ofNullable(account);
    }


    @Override
    public int update(Account entity) throws DAOException {
        try (ConnectionProxy connection = SQLConnectionPool.INSTANCE.getConnection();
             PreparedStatement statement = connection.prepareStatement(SQL_UPDATE)) {
            statement.setInt(1, entity.getRole().getId());
            statement.setString(2, entity.getEmail());
            statement.setString(3, entity.getPassword());
            statement.setString(4, entity.getFirstName());
            statement.setString(5, entity.getPhoneNumber());
            statement.setBigDecimal(6, entity.getBalance());
            statement.setInt(7, entity.getBonus());
            statement.setLong(8, entity.getId());
            return statement.executeUpdate();

        } catch (SQLException e) {
            logger.error(e);
            throw new DAOException(SQL_STATEMENT_ERROR, e);
        }

    }

    @Override
    public boolean delete(Account entity) throws DAOException {
        try (ConnectionProxy connection = SQLConnectionPool.INSTANCE.getConnection();
             PreparedStatement statement = connection.prepareStatement(SQL_DELETE);) {
            statement.setLong(1, entity.getId());
            return statement.execute();
        } catch (SQLException e) {
            logger.error(e);
            throw new DAOException(SQL_STATEMENT_ERROR, e);
        }

    }


    private Account buildAccount(PreparedStatement readStatement) throws DAOException {
        Account account = null;
        try (ResultSet rs = readStatement.executeQuery()) {
            if (rs.next()) {
                account = ModelMapper.INSTANCE.getAccountFromResultSet(rs);
            }
        } catch (SQLException e) {
            logger.error(e);
            throw new DAOException(e);
        }
        return account;
    }

    private List<Account> buildAccountList(PreparedStatement readStatement) throws DAOException {
        List<Account> accounts = new ArrayList<>();
        Account account;
        try (ResultSet rs = readStatement.executeQuery()) {
            while (rs.next()) {
                account = ModelMapper.INSTANCE.getAccountFromResultSet(rs);
                accounts.add(account);
            }
        } catch (SQLException e) {
            logger.error(e);
            throw new DAOException(e);
        }
        return accounts;
    }
}

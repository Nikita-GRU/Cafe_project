package DAO.repository.impl;

import DAO.db.ConnectionProxy;
import DAO.db.SQLConnectionPool;
import DAO.exception.DAOException;
import DAO.repository.AccountRepository;
import model.Account;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

public class AccountRepositoryImpl implements AccountRepository {
    Logger logger = LogManager.getLogger(AccountRepositoryImpl.class);
    private static final String SQL_GET = "select * from account where id=(?)";
    private static final String SQL_GET_ALL = "select * from account";
    private static final String SQL_SAVE = "insert into account(id,login,password,email,firstname,lastname,role_id) " +
            "values(?,?,?,?,?,?,?)";
    private static final String SQL_UPDATE = "update account set login=?,password=?,email=?" +
            ",firstname=?,lastname=?,role_id=? where id=?";
    private static final String SQL_DELETE = "delete from account where id=?";


    @Override
    public Optional<Account> get(int id) throws DAOException {
        Account account = new Account();
        ConnectionProxy connection = new ConnectionProxy(SQLConnectionPool.INSTANCE.getConnection());
        try {
            PreparedStatement statement = connection.prepareStatement(SQL_GET);
            statement.setInt(1, (int) id);
            ResultSet rs = statement.executeQuery();
            rs.next();
            account.setLogin(rs.getNString(2));
            account.setPassword(rs.getNString(3));
            account.setEmail(rs.getNString(4));
            account.setFirstName(rs.getNString(5));
            account.setLastName(rs.getNString(6));
        } catch (SQLException e) {
            logger.error(e);
            throw new DAOException("SQL statement error", e);
        } finally {

            connection.close();


        }
        return Optional.of(account);
    }

    @Override
    public List<Account> getAll() throws DAOException {
        List<Account> resultCollection = new ArrayList<>();
        ConnectionProxy connection = new ConnectionProxy(SQLConnectionPool.INSTANCE.getConnection());
        if (Objects.isNull(connection)) {
            throw new RuntimeException("No connection");
        }
        try (PreparedStatement preparedStatement = connection.prepareStatement(SQL_GET_ALL)) {
            final ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet == null) {
                return resultCollection;
            }
            while (resultSet.next()) {
                Account receivedUser = new Account(resultSet.getInt(1),
                        resultSet.getString(2),
                        resultSet.getString(3),
                        resultSet.getString(4),
                        resultSet.getString(5),
                        resultSet.getString(6),
                        resultSet.getInt(7));
                resultCollection.add(receivedUser);
            }
        } catch (SQLException e) {
            logger.error(e);
            throw new DAOException("SQL statement error", e);
        } finally {
            connection.close();

        }
        return resultCollection;
    }

    @Override
    public void save(Account account) throws DAOException {
        ConnectionProxy connection = new ConnectionProxy(SQLConnectionPool.INSTANCE.getConnection());
        try {
            PreparedStatement statement = connection.prepareStatement(SQL_SAVE);
            statement.setInt(1, account.getId());
            statement.setString(2, account.getLogin());
            statement.setString(3, account.getPassword());
            statement.setString(4, account.getEmail());
            statement.setString(5, account.getFirstName());
            statement.setString(6, account.getLastName());
            statement.setInt(7, account.getRoleID());
            statement.execute();
        } catch (SQLException e) {
            logger.error(e);
            throw new DAOException("SQL statement error", e);
        } finally {
            connection.close();

        }

    }

    @Override
    public void update(Account account) throws DAOException {
        ConnectionProxy connection = new ConnectionProxy(SQLConnectionPool.INSTANCE.getConnection());
        try {
            PreparedStatement statement = connection.prepareStatement(SQL_UPDATE);
            statement.setString(1, account.getLogin());
            statement.setString(2, account.getPassword());
            statement.setString(3, account.getEmail());
            statement.setString(4, account.getFirstName());
            statement.setString(5, account.getLastName());
            statement.setInt(6, account.getRoleID());
            statement.setInt(7, account.getId());
            logger.info(statement.executeUpdate() + " row affected");
        } catch (SQLException e) {
            logger.error(e);
            throw new DAOException("SQL statement error", e);
        } finally {
            connection.close();

        }
    }

    @Override
    public void delete(Account account) throws DAOException {
        ConnectionProxy connection = new ConnectionProxy(SQLConnectionPool.INSTANCE.getConnection());
        try {
            PreparedStatement statement = connection.prepareStatement(SQL_DELETE);
            statement.setInt(1, account.getId());
            statement.execute();
        } catch (SQLException e) {
            logger.error(e);
            throw new DAOException("SQL statement error", e);
        } finally {
            connection.close();

        }
    }
}

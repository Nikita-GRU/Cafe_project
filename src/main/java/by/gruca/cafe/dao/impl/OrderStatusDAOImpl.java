package by.gruca.cafe.dao.impl;

import by.gruca.cafe.util.ModelMapper;
import by.gruca.cafe.dao.OrderStatusDAO;
import by.gruca.cafe.dao.connectionpool.ConnectionProxy;
import by.gruca.cafe.dao.connectionpool.SQLConnectionPool;
import by.gruca.cafe.dao.exception.DAOException;
import by.gruca.cafe.model.OrderStatus;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class OrderStatusDAOImpl implements OrderStatusDAO {
    public static final String ORDER_STATUS_ID = "id";
    public static final String SQL_STATEMENT_ERROR = "SQL statement error";
    public static final String ORDER_STATUS_STATUS = "status";
    private static final String SQL_READ = "SELECT * FROM order_status WHERE order_status_id=? ";
    private static final String SQL_READ_ALL = "SELECT * from order_status";
    private static final String SQL_CREATE = "INSERT INTO order_status (order_status_id,status) " +
            "VALUES (DEFAULT,?)";
    private static final String SQL_UPDATE = "UPDATE order_status SET status=?" +
            " WHERE order_status_id=?";
    private static final String SQL_DELETE = "DELETE FROM order_status WHERE order_status_id=? ";
    private static final long CREATE_ERROR = -1;
    Logger logger = LogManager.getLogger(OrderStatusDAOImpl.class);

    @Override
    public long create(OrderStatus entity) throws DAOException {
        try (ConnectionProxy connection = SQLConnectionPool.INSTANCE.getConnection();
             PreparedStatement statement = connection.prepareStatement(SQL_CREATE, Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, entity.getStatus());
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
    public Optional<OrderStatus> read(long id) throws DAOException {
        OrderStatus orderStatus;
        try (ConnectionProxy connection = SQLConnectionPool.INSTANCE.getConnection();
             PreparedStatement readStatement = connection.prepareStatement(SQL_READ)) {
            readStatement.setLong(1, id);

            orderStatus = buildOrderStatus(readStatement);

        } catch (SQLException e) {
            logger.error(e);
            throw new DAOException(SQL_STATEMENT_ERROR, e);
        }
        return Optional.ofNullable(orderStatus);
    }


    @Override
    public int update(OrderStatus entity) throws DAOException {
        try (ConnectionProxy connection = SQLConnectionPool.INSTANCE.getConnection();
             PreparedStatement statement = connection.prepareStatement(SQL_UPDATE)) {
            statement.setString(1, entity.getStatus());
            statement.setLong(2, entity.getId());
            int rowsAffected = statement.executeUpdate();
            return statement.executeUpdate();

        } catch (SQLException e) {
            logger.error(e);
            throw new DAOException(SQL_STATEMENT_ERROR, e);
        }
    }

    @Override
    public boolean delete(OrderStatus entity) throws DAOException {
        try (ConnectionProxy connection = SQLConnectionPool.INSTANCE.getConnection();
             PreparedStatement statement = connection.prepareStatement(SQL_DELETE);) {
            statement.setLong(1, entity.getId());
            return statement.execute();
        } catch (SQLException e) {
            logger.error(e);
            throw new DAOException(SQL_STATEMENT_ERROR, e);
        }
    }

    @Override
    public List<OrderStatus> readAll() throws DAOException {
        List<OrderStatus> orderStatusList = new ArrayList<>();
        try (ConnectionProxy connection = SQLConnectionPool.INSTANCE.getConnection();
             PreparedStatement statement = connection.prepareStatement(SQL_READ_ALL);) {
            orderStatusList = buildOrderStatusList(statement);
        } catch (SQLException e) {
            logger.error(e);
            throw new DAOException(SQL_STATEMENT_ERROR, e);
        }
        return orderStatusList;
    }

    private OrderStatus buildOrderStatus(PreparedStatement readStatement) throws DAOException {
        OrderStatus orderStatus = null;
        try (ResultSet rs = readStatement.executeQuery()) {
            if (rs.next()) {
                orderStatus = ModelMapper.INSTANCE.getOrderStatusFromResultSet(rs);
            }
        } catch (SQLException e) {
            logger.error(e);
            throw new DAOException(e);
        }
        return orderStatus;
    }

    private List<OrderStatus> buildOrderStatusList(PreparedStatement readStatement) throws DAOException {
        List<OrderStatus> orderStatusList = new ArrayList<>();
        OrderStatus orderStatus;
        try (ResultSet rs = readStatement.executeQuery()) {
            while (rs.next()) {
                orderStatus = ModelMapper.INSTANCE.getOrderStatusFromResultSet(rs);
                orderStatusList.add(orderStatus);
            }
        } catch (SQLException e) {
            logger.error(e);
            throw new DAOException(e);
        }
        return orderStatusList;
    }
}

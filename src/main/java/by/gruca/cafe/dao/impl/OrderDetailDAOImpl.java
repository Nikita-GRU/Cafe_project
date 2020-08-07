package by.gruca.cafe.dao.impl;

import by.gruca.cafe.dao.OrderDetailDAO;
import by.gruca.cafe.dao.connectionpool.ConnectionProxy;
import by.gruca.cafe.dao.connectionpool.SQLConnectionPool;
import by.gruca.cafe.dao.exception.DAOException;
import by.gruca.cafe.model.OrderDetail;
import by.gruca.cafe.util.ModelMapper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class OrderDetailDAOImpl implements OrderDetailDAO {
    public static final String ORDER_DETAIL_ID = "id";
    public static final String ORDER_DETAIL_ORDER_ID = "order_id";
    public static final String ORDER_DETAIL_PRODUCT_ID = "product_id";
    public static final String ORDER_DETAIL_QUANTITY = "product_quantity";
    public static final String SQL_STATEMENT_ERROR = "SQL statement error";
    private static final String SQL_READ = "SELECT * FROM order_detail o " +
            "JOIN product p  ON o.product_id= p.product_id " +
            "JOIN category c on p.category_id = c.category_id WHERE order_id=?";

    private static final String SQL_READ_ALL = "SELECT * FROM order_detail o " +
            "JOIN product p  ON o.product_id= p.product_id " +
            "JOIN category c on p.category_id = c.category_id";

    private static final String SQL_CREATE = "INSERT INTO order_detail (order_id,product_id,product_quantity) " +
            "VALUES (?,?,?)";
    private static final String SQL_UPDATE = "UPDATE order_detail SET order_id=?,product_id=?,product_quantity=?" +
            " WHERE order_id=?";
    private static final String SQL_DELETE = "DELETE FROM order_detail WHERE order_id=? ";
    private static final long CREATE_ERROR = -1;
    private static final String SQL_READ_ALL_BY_ORDER = "SELECT * FROM order_detail o " +
            "JOIN product p  ON o.product_id= p.product_id " +
            "JOIN category c on p.category_id = c.category_id WHERE order_id=?";
    Logger logger = LogManager.getLogger(OrderDetailDAOImpl.class);

    @Override
    public long create(OrderDetail entity) throws DAOException {
        try (ConnectionProxy connection = SQLConnectionPool.INSTANCE.getConnection();
             PreparedStatement statement = connection.prepareStatement(SQL_CREATE, Statement.RETURN_GENERATED_KEYS)) {
            statement.setLong(1, entity.getOrderId());
            statement.setInt(2, entity.getProduct().getId());
            statement.setInt(3, entity.getQuantity());
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
    public Optional<OrderDetail> read(long orderId) throws DAOException {
        OrderDetail orderDetail;
        try (ConnectionProxy connection = SQLConnectionPool.INSTANCE.getConnection();
             PreparedStatement readStatement = connection.prepareStatement(SQL_READ)) {
            readStatement.setLong(1, orderId);
            orderDetail = buildOrderDetail(readStatement);
        } catch (SQLException e) {
            logger.error(e);
            throw new DAOException(SQL_STATEMENT_ERROR, e);
        }
        return Optional.of(orderDetail);
    }


    @Override
    public int update(OrderDetail entity) throws DAOException {
        try (ConnectionProxy connection = SQLConnectionPool.INSTANCE.getConnection();
             PreparedStatement statement = connection.prepareStatement(SQL_UPDATE)) {
            statement.setLong(1, entity.getOrderId());
            statement.setInt(2, entity.getProduct().getId());
            statement.setInt(3, entity.getQuantity());
            statement.setLong(4, entity.getOrderId());
            int rowsAffected = statement.executeUpdate();
            return statement.executeUpdate();

        } catch (SQLException e) {
            logger.error(e);
            throw new DAOException(SQL_STATEMENT_ERROR, e);
        }
    }

    @Override
    public boolean delete(OrderDetail entity) throws DAOException {
        try (ConnectionProxy connection = SQLConnectionPool.INSTANCE.getConnection();
             PreparedStatement statement = connection.prepareStatement(SQL_DELETE);) {
            statement.setLong(1, entity.getOrderId());
            return statement.execute();
        } catch (SQLException e) {
            logger.error(e);
            throw new DAOException(SQL_STATEMENT_ERROR, e);
        }
    }

    @Override
    public List<OrderDetail> readAll() throws DAOException {
        List<OrderDetail> orderDetailList;
        try (ConnectionProxy connection = SQLConnectionPool.INSTANCE.getConnection();
             PreparedStatement statement = connection.prepareStatement(SQL_READ_ALL);) {
            orderDetailList = buildOrderDetailList(statement);
        } catch (SQLException e) {
            logger.error(e);
            throw new DAOException(SQL_STATEMENT_ERROR, e);
        }
        return orderDetailList;
    }

    @Override
    public List<OrderDetail> readAllByOrder(long orderId) throws DAOException {
        List<OrderDetail> orderDetailList;
        try (ConnectionProxy connection = SQLConnectionPool.INSTANCE.getConnection();
             PreparedStatement statement = connection.prepareStatement(SQL_READ_ALL_BY_ORDER)) {
            statement.setLong(1, orderId);
            orderDetailList = buildOrderDetailList(statement);
        } catch (SQLException e) {
            logger.error(e);
            throw new DAOException(SQL_STATEMENT_ERROR, e);
        }
        return orderDetailList;
    }

    private OrderDetail buildOrderDetail(PreparedStatement readStatement) throws DAOException {
        OrderDetail orderDetail = null;
        try (ResultSet rs = readStatement.executeQuery()) {
            if (rs.next()) {
                orderDetail = ModelMapper.INSTANCE.getOrderDetailFromResultSet(rs);
            }
        } catch (SQLException e) {
            logger.error(e);
            throw new DAOException(e);
        }
        return orderDetail;
    }

    private List<OrderDetail> buildOrderDetailList(PreparedStatement readStatement) throws DAOException {
        List<OrderDetail> orderDetails = new ArrayList<>();
        OrderDetail orderDetail;
        try (ResultSet rs = readStatement.executeQuery()) {
            while (rs.next()) {
                orderDetail = ModelMapper.INSTANCE.getOrderDetailFromResultSet(rs);
                orderDetails.add(orderDetail);
            }
        } catch (SQLException e) {
            logger.error(e);
            throw new DAOException(e);
        }
        return orderDetails;
    }
}

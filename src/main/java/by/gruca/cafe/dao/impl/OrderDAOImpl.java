package by.gruca.cafe.dao.impl;


import by.gruca.cafe.dao.OrderDAO;
import by.gruca.cafe.dao.connectionpool.ConnectionProxy;
import by.gruca.cafe.dao.connectionpool.SQLConnectionPool;
import by.gruca.cafe.dao.exception.DAOException;
import by.gruca.cafe.factory.DAOFactory;
import by.gruca.cafe.model.Order;
import by.gruca.cafe.model.OrderDetail;
import by.gruca.cafe.model.OrderStatus;
import by.gruca.cafe.model.Product;
import by.gruca.cafe.util.ModelMapper;
import by.gruca.cafe.util.TimeConverter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.*;


public class OrderDAOImpl implements OrderDAO {
    public static final String ORDER_ID = "order_id";
    public static final String ORDER_PRICE = "order_price";
    public static final String ORDER_CREATION_DATE = "creation_date";
    public static final String ORDER_DELIVERY_DATE = "delivery_date";
    public static final String ORDER_NOTE = "note";
    public static final String ORDER_SCORE = "score";
    public static final String ORDER_FEEDBACK = "feedback";
    public static final String SQL_STATEMENT_ERROR = "SQL statement error";
    public static final String ORDER_ADDRESS = "address";
    public static final String ORDER_APARTMENT = "apartment";
    public static final String ORDER_BONUS_TO_PAY = "bonus_to_pay";
    public static final String ORDER_COUNT = "order_count";
    private static final String SQL_READ_ORDERS_COUNT = "SELECT COUNT(*) order_count FROM `order`";
    private static final String SQL_READ_PAGINATED_ORDERS = "SELECT * FROM `order` " +
            "JOIN account a on `order`.account_id = a.account_id " +
            "JOIN order_status os on `order`.order_status_id = os.order_status_id " +
            "JOIN order_payment op on `order`.order_payment_id = op.order_payment_id " +
            "JOIN role r on a.role_id = r.role_id ORDER BY `order`.order_id LIMIT ? OFFSET ?";
    private static final String SQL_READ_NOT_DELIVERED_ORDERS = "SELECT * FROM `order` " +
            "JOIN account a on `order`.account_id = a.account_id " +
            "JOIN order_status os on `order`.order_status_id = os.order_status_id " +
            "JOIN order_payment op on `order`.order_payment_id = op.order_payment_id " +
            "JOIN role r on a.role_id = r.role_id  " +
            "WHERE status!=? ORDER BY `order`.order_id";
    private static final String SQL_READ = "SELECT * FROM `order` " +
            "JOIN account a on `order`.account_id = a.account_id " +
            "JOIN order_status os on `order`.order_status_id = os.order_status_id " +
            "JOIN order_payment op on `order`.order_payment_id = op.order_payment_id " +
            "JOIN role r on a.role_id = r.role_id " +
            "WHERE `order`.order_id=?";
    private static final String SQL_READ_ALL = "SELECT * FROM `order` " +
            "JOIN account a on `order`.account_id = a.account_id " +
            "JOIN order_status os on `order`.order_status_id = os.order_status_id " +
            "JOIN order_payment op on `order`.order_payment_id = op.order_payment_id " +
            "JOIN role r on a.role_id = r.role_id ORDER BY `order`.order_id";
    private static final String SQL_READ_ALL_BY_ACCOUNT = "SELECT * FROM `order` " +
            "JOIN account a on `order`.account_id = a.account_id " +
            "JOIN order_status os on `order`.order_status_id = os.order_status_id " +
            "JOIN order_payment op on `order`.order_payment_id = op.order_payment_id " +
            "JOIN role r on a.role_id = r.role_id " +
            "WHERE a.account_id=? ORDER BY `order`.order_id ";
    private static final String SQL_CREATE = "INSERT INTO `order` (order_id, account_id,order_status_id," +
            "order_payment_id,creation_date,delivery_date, order_price, note,feedback,score,address,apartment,bonus_to_pay) " +
            "VALUES (DEFAULT,?,?,?,?,?,?,?,?,?,?,?,?)";
    private static final String SQL_UPDATE = "UPDATE `order` SET  account_id=?,order_status_id=?," +
            "order_payment_id=?,creation_date=?,delivery_date=?, " +
            "order_price=?, note=?,feedback=?,score=?, address=?,apartment=?, bonus_to_pay=? WHERE `order`.order_id=?";
    private static final String SQL_DELETE = "DELETE FROM `order` WHERE order_id=?";
    Logger logger = LogManager.getLogger(OrderDAOImpl.class);


    @Override
    public Optional<Order> read(long id) throws DAOException {
        Order order;
        try (ConnectionProxy connection = SQLConnectionPool.INSTANCE.getConnection();
             PreparedStatement readStatement = connection.prepareStatement(SQL_READ)) {
            readStatement.setLong(1, id);
            order = buildOrder(readStatement);
        } catch (SQLException e) {
            logger.error(e);
            throw new DAOException(SQL_STATEMENT_ERROR, e);
        }
        return Optional.ofNullable(order);
    }

    @Override
    public List<Order> readAll() throws DAOException {
        List<Order> orders;
        try (ConnectionProxy connection = SQLConnectionPool.INSTANCE.getConnection();
             PreparedStatement statement = connection.prepareStatement(SQL_READ_ALL);) {
            orders = buildOrderList(statement);
        } catch (SQLException e) {
            logger.error(e);
            throw new DAOException(SQL_STATEMENT_ERROR, e);
        }
        return orders;
    }

    @Override
    public List<Order> readAll(int itemsPerPage, int pageNumber) throws DAOException {
        List<Order> orders;
        try (ConnectionProxy connection = SQLConnectionPool.INSTANCE.getConnection();
             PreparedStatement statement =
                     connection.prepareStatement(SQL_READ_PAGINATED_ORDERS);) {
            statement.setInt(1, itemsPerPage);
            statement.setInt(2, (pageNumber - 1) * itemsPerPage);
            orders = buildOrderList(statement);
        } catch (SQLException e) {
            logger.error(e);
            throw new DAOException(SQL_STATEMENT_ERROR, e);
        }
        return orders;
    }

    @Override
    public int readOrdersCount() throws DAOException {
        int ordersCount = 0;
        try (ConnectionProxy connection = SQLConnectionPool.INSTANCE.getConnection();
             PreparedStatement statement = connection.prepareStatement(SQL_READ_ORDERS_COUNT);
             ResultSet rs = statement.executeQuery();) {
            if (rs.next()) {
                ordersCount = rs.getInt(ORDER_COUNT);
            }
        } catch (SQLException e) {
            logger.error(e);
            throw new DAOException(SQL_STATEMENT_ERROR, e);
        }
        return ordersCount;
    }

    @Override
    public long create(Order entity) throws DAOException {

        try (ConnectionProxy connection = SQLConnectionPool.INSTANCE.getConnection();
             PreparedStatement orderCreateStatement = connection.prepareStatement(SQL_CREATE, Statement.RETURN_GENERATED_KEYS);
        ) {
            orderCreateStatement.setLong(1, entity.getAccount().getId());
            orderCreateStatement.setLong(2, entity.getOrderStatus().getId());
            orderCreateStatement.setInt(3, entity.getPaymentType().getId());
            orderCreateStatement.setString(4, TimeConverter.convertFromLocalDateTimeToSQLDateTime(entity.getCreationDate()));
            orderCreateStatement.setString(5, TimeConverter.convertFromLocalDateTimeToSQLDateTime(entity.getDeliveryDate()));
            orderCreateStatement.setBigDecimal(6, entity.getPrice());
            orderCreateStatement.setString(7, entity.getNote());
            orderCreateStatement.setString(8, entity.getFeedback());
            orderCreateStatement.setInt(9, entity.getScore());
            orderCreateStatement.setString(10, entity.getAddress());
            orderCreateStatement.setString(11, entity.getApartment());
            orderCreateStatement.setInt(12, entity.getBonusToPay());
            orderCreateStatement.execute();

            ResultSet resultSet = orderCreateStatement.getGeneratedKeys();
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
    public int update(Order entity) throws DAOException {
        try (ConnectionProxy connection = SQLConnectionPool.INSTANCE.getConnection();
             PreparedStatement updateStatement = connection.prepareStatement(SQL_UPDATE)) {
            updateStatement.setLong(1, entity.getAccount().getId());
            updateStatement.setInt(2, entity.getOrderStatus().getId());
            updateStatement.setInt(3, entity.getPaymentType().getId());
            updateStatement.setString(4, entity.getCreationDate().toString());
            updateStatement.setString(5, entity.getDeliveryDate().toString());
            updateStatement.setBigDecimal(6, entity.getPrice());
            updateStatement.setString(7, entity.getNote());
            updateStatement.setString(8, entity.getFeedback());
            updateStatement.setInt(9, entity.getScore());
            updateStatement.setString(10, entity.getAddress());
            updateStatement.setString(11, entity.getApartment());
            updateStatement.setInt(12, entity.getBonusToPay());
            updateStatement.setLong(13, entity.getId());
            return updateStatement.executeUpdate();
        } catch (SQLException e) {
            logger.error(e);
            throw new DAOException(SQL_STATEMENT_ERROR, e);
        }
    }

    @Override
    public boolean delete(Order entity) throws DAOException {
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
    public List<Order> readAllByAccount(long accountId) throws DAOException {
        List<Order> orders;
        try (ConnectionProxy connection = SQLConnectionPool.INSTANCE.getConnection();
             PreparedStatement statement = connection.prepareStatement(SQL_READ_ALL_BY_ACCOUNT);) {
            statement.setLong(1, accountId);
            orders = buildOrderList(statement);
        } catch (SQLException e) {
            logger.error(e);
            throw new DAOException(SQL_STATEMENT_ERROR, e);
        }
        return orders;

    }

    @Override
    public List<Order> readNotDeliveredOrders() throws DAOException {
        List<Order> orders;
        try (ConnectionProxy connection = SQLConnectionPool.INSTANCE.getConnection();
             PreparedStatement statement = connection.prepareStatement(SQL_READ_NOT_DELIVERED_ORDERS);) {
            statement.setString(1, OrderStatus.DELIVERED.getStatus());
            orders = buildOrderList(statement);
        } catch (SQLException e) {
            logger.error(e);
            throw new DAOException(SQL_STATEMENT_ERROR, e);
        }
        return orders;
    }

    private Order buildOrder(PreparedStatement readStatement) throws DAOException {
        Order currentOrder = null;
        try (ResultSet rs = readStatement.executeQuery()) {

            if (rs.next()) {
                currentOrder = ModelMapper.INSTANCE.getOrderFromResultSet(rs);
                currentOrder.setProducts(getOrderProducts(currentOrder.getId()));
            }
        } catch (SQLException e) {
            logger.error(e);
            throw new DAOException(e);
        }
        return currentOrder;
    }

    private List<Order> buildOrderList(PreparedStatement readStatement) throws DAOException {
        List<Order> orders = new ArrayList<>();
        Order currentOrder;
        try (ResultSet rs = readStatement.executeQuery()) {
            while (rs.next()) {
                currentOrder = ModelMapper.INSTANCE.getOrderFromResultSet(rs);
                currentOrder.setProducts(getOrderProducts(currentOrder.getId()));
                orders.add(currentOrder);
            }
        } catch (SQLException e) {
            logger.error(e);
            throw new DAOException(e);
        }
        return orders;
    }

    private Map<Product, Integer> getOrderProducts(long orderId) throws DAOException {
        Map<Product, Integer> products = new HashMap<>();
        List<OrderDetail> orderDetails = DAOFactory.INSTANCE.getOrderDetailDAO().readAllByOrder(orderId);
        for (OrderDetail orderDetail : orderDetails
        ) {
            products.put(orderDetail.getProduct(), orderDetail.getQuantity());
        }
        return products;
    }
}

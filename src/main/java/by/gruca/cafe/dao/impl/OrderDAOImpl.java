package by.gruca.cafe.dao.impl;


import by.gruca.cafe.dao.AccountDAO;
import by.gruca.cafe.dao.OrderDAO;
import by.gruca.cafe.dao.connectionpool.ConnectionProxy;
import by.gruca.cafe.dao.connectionpool.SQLConnectionPool;
import by.gruca.cafe.dao.exception.DAOException;
import by.gruca.cafe.factory.DAOFactory;
import by.gruca.cafe.model.Account;
import by.gruca.cafe.model.Order;
import by.gruca.cafe.model.Product;
import by.gruca.cafe.util.TimeConverter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;


public class OrderDAOImpl implements OrderDAO {
    private static final String SQL_GET_NOT_DELIVERED_ORDERS = "SELECT * FROM `order` WHERE is_delivered=?";
    private static final String SQL_READ_BY_TIME_AND_ACCOUNT = "SELECT * FROM `order` where date=? AND account=? ";
    private static final String SQL_ATTACH_PRODUCTS = "insert into order_detail(id, order_id, product_id, quantity) values(DEFAULT,?,?,?)";
    private static final String SQL_GET = "select * from order where id=(?)";
    private static final String SQL_GET_ALL = "select * from order";
    private static final String SQL_GET_ALL_BY_LOGIN = "select * from order join account on order.id = account.id where account=?";
    private static final String SQL_CREATE = "INSERT INTO `order` (id,date, price, account, review) values (DEFAULT,?,?,?,?)";
    private static final String SQL_UPDATE = "update order set date=?,price=?,account=?,review=?" +
            "where id=?";
    private static final String SQL_DELETE = "delete from order where id=?";
    private static final String SQL_GET_PRODUCTS = "select product_id from order join product on order.product_id=product.id where order.id=?";
    private static final String SQL_GET_ORDER_PRODUCTS = "SELECT * FROM order_detail where order_id=?";
    private static final String SQL_SET_ACCEPTED = "UPDATE `order` SET is_accepted=? where id=?";
    private static final String SQL_SET_DELIVERED = "UPDATE `order` SET is_delivered=? where id=?";
    private final AccountDAO accountDAO = new AccountDAOImpl();
    Logger logger = LogManager.getLogger(OrderDAOImpl.class);

    public OrderDAOImpl() {
    }

    @Override
    public boolean create(Order order) throws DAOException {
        try (ConnectionProxy connection = SQLConnectionPool.INSTANCE.getConnection();
             PreparedStatement statement = connection.prepareStatement(SQL_CREATE)) {
            statement.setString(1, TimeConverter.convertFromLocalDateTimeToSQLDateTime(order.getDate()));
            statement.setDouble(2, order.getPrice());
            statement.setString(3, order.getAccount().getEmail());
            statement.setString(4, order.getReview());
            return statement.execute();
        } catch (SQLException e) {
            logger.error(e);
            throw new DAOException("SQL statement error", e);
        }
    }

    public Optional<Order> readByTimeAndAccount(String time, Account account) throws DAOException {
        Order order = new Order();
        try (ConnectionProxy connection = SQLConnectionPool.INSTANCE.getConnection();
             PreparedStatement readStatement = connection.prepareStatement(SQL_READ_BY_TIME_AND_ACCOUNT)) {
            readStatement.setString(1, time);
            readStatement.setString(2, account.getEmail());
            try (ResultSet resultSet = readStatement.executeQuery()) {
                if (resultSet.next()) {
                    order.setId(resultSet.getInt("id"));
                    order.setReview(resultSet.getString("review"));
                    order.setAccount(accountDAO.read(resultSet.getString("account")).get());
                    order.setPrice(resultSet.getDouble("price"));
                } else throw new DAOException("Account is not exist");
            } catch (SQLException e) {
                logger.error(e);
                throw new DAOException("SQL statement error", e);
            }
        } catch (SQLException e) {
            logger.error(e);
            throw new DAOException("SQL statement error", e);
        }
        return Optional.of(order);
    }

    @Override
    public Optional<Order> read(Integer orderId) throws DAOException {
        Order order = new Order();
        try (ConnectionProxy connection = SQLConnectionPool.INSTANCE.getConnection();
             PreparedStatement readStatement = connection.prepareStatement(SQL_GET)) {
            readStatement.setInt(1, orderId);
            try (ResultSet resultSet = readStatement.executeQuery()) {
                if (resultSet.next()) {
                    order.setId(resultSet.getInt("id"));
                    order.setReview(resultSet.getString("review"));
                    order.setAccount(accountDAO.read(resultSet.getString("email")).get());
                    order.setPrice(resultSet.getDouble("price"));
                } else throw new DAOException("Order is not exist");
            } catch (SQLException e) {
                logger.error(e);
                throw new DAOException("SQL statement error", e);
            }
        } catch (SQLException e) {
            logger.error(e);
            throw new DAOException("SQL statement error", e);
        }
        return Optional.of(order);
    }

    @Override
    public int update(Order order) throws DAOException {
        try (ConnectionProxy connection = SQLConnectionPool.INSTANCE.getConnection();
             PreparedStatement statement = connection.prepareStatement(SQL_UPDATE)) {
            statement.setString(1, order.getDate().toString());
            statement.setDouble(2, order.getPrice());
            statement.setString(3, order.getAccount().getEmail());
            statement.setString(4, order.getReview());
            statement.setInt(5, order.getId());
            int rowsAffected = statement.executeUpdate();
            logger.info(rowsAffected + " row affected");
            return rowsAffected;

        } catch (SQLException e) {
            logger.error(e);
            throw new DAOException("SQL statement error", e);
        }
    }

    @Override
    public boolean delete(Order order) throws DAOException {
        try (ConnectionProxy connection = SQLConnectionPool.INSTANCE.getConnection();
             PreparedStatement statement = connection.prepareStatement(SQL_DELETE);) {
            statement.setInt(1, order.getId());
            return statement.execute();
        } catch (SQLException e) {
            logger.error(e);
            throw new DAOException("SQL statement error", e);
        }
    }

    @Override
    public List<Order> getAll() throws UnsupportedOperationException {
        throw new UnsupportedOperationException("");
    }

    @Override
    public List<Order> getAllByAccount(String email) throws DAOException {
        try (ConnectionProxy connection = SQLConnectionPool.INSTANCE.getConnection();
             PreparedStatement statement = connection.prepareStatement(SQL_GET_ALL_BY_LOGIN)) {
            statement.setString(1, email);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Order receivedUser = new Order();
            }
        } catch (SQLException e) {
            logger.error(e);
            throw new DAOException("SQL statement error", e);
        }
        return null;
    }

    @Override
    public List<Order> getNotDeliveredOrders() throws DAOException {
        List<Order> orders = new ArrayList<>();
        try (ConnectionProxy connection = SQLConnectionPool.INSTANCE.getConnection();
             PreparedStatement readStatement = connection.prepareStatement(SQL_GET_NOT_DELIVERED_ORDERS)) {
            readStatement.setBoolean(1, false);
            try (ResultSet resultSet = readStatement.executeQuery()) {
                while (resultSet.next()) {
                    Order currentOrder = new Order();
                    currentOrder.setId(resultSet.getInt("id"));
                    currentOrder.setReview(resultSet.getString("review"));
                    currentOrder.setAccount(accountDAO.read(resultSet.getString("account")).get());
                    currentOrder.setPrice(resultSet.getDouble("price"));
                    currentOrder.setProducts(DAOFactory.INSTANCE.getOrderDAO().getOrderProducts(currentOrder.getId()));
                    currentOrder.setDate(TimeConverter.convertFromSQLDateTimeToLocalDateTime(resultSet.getString("date")));
                    currentOrder.setAccepted(resultSet.getBoolean("is_accepted"));
                    currentOrder.setDelivered(resultSet.getBoolean("is_delivered"));
                    orders.add(currentOrder);
                }
            } catch (SQLException e) {
                logger.error(e);
                throw new DAOException("SQL statement error", e);
            }
        } catch (SQLException e) {
            logger.error(e);
            throw new DAOException("SQL statement error", e);
        }
        return orders;
    }

    @Override
    public HashMap<Product, Integer> getOrderProducts(Integer orderId) throws DAOException {
        HashMap<Product, Integer> products = new HashMap<>();
        try (ConnectionProxy connection = SQLConnectionPool.INSTANCE.getConnection();
             PreparedStatement readStatement = connection.prepareStatement(SQL_GET_ORDER_PRODUCTS)) {
            readStatement.setInt(1, orderId);
            try (ResultSet resultSet = readStatement.executeQuery()) {
                while (resultSet.next()) {
                    products.put((DAOFactory.INSTANCE.getProductDAO().read(resultSet.getInt("product_id"))).get()
                            , resultSet.getInt("quantity"));
                }
            } catch (SQLException e) {
                logger.error(e);
                throw new DAOException("SQL statement error", e);
            }
        } catch (SQLException e) {
            logger.error(e);
            throw new DAOException("SQL statement error", e);
        }


        return products;
    }

    @Override
    public void setAccepted(int orderId) throws DAOException {
        try (ConnectionProxy connection = SQLConnectionPool.INSTANCE.getConnection();
             PreparedStatement statement = connection.prepareStatement(SQL_SET_ACCEPTED)) {
            statement.setBoolean(1, true);
            statement.setInt(2, orderId);
            statement.executeUpdate();
        } catch (SQLException e) {
            logger.error(e);
            throw new DAOException(e);
        }
    }

    @Override
    public void setDelivered(int orderId) throws DAOException {
        try (ConnectionProxy connection = SQLConnectionPool.INSTANCE.getConnection();
             PreparedStatement statement = connection.prepareStatement(SQL_SET_DELIVERED)) {
            statement.setBoolean(1, true);
            statement.setInt(2, orderId);
            statement.executeUpdate();
        } catch (SQLException e) {
            logger.error(e);
            throw new DAOException(e);
        }
    }

    @Override
    public void attachProductsToOrder(HashMap<Product, Integer> products, Order order) throws DAOException {
        try (ConnectionProxy connection = SQLConnectionPool.INSTANCE.getConnection();
             PreparedStatement statement = connection.prepareStatement(SQL_ATTACH_PRODUCTS)) {
            for (Product product : products.keySet()) {
                statement.setInt(1, order.getId());
                statement.setInt(2, product.getId());
                statement.setInt(3, products.get(product));
                statement.execute();
            }
        } catch (SQLException e) {
            logger.error(e);
            throw new DAOException("SQL statement error", e);
        }


    }
//    @Override
//    public List<Order> getAll() throws DAOException {
//        List<Order> resultCollection = new ArrayList<>();
//        ConnectionProxy connection = SQLConnectionPool.INSTANCE.getConnection();
//        if (Objects.isNull(connection)) {
//            throw new RuntimeException("No connection");
//        }
//        try {
//            PreparedStatement preparedStatement = connection.prepareStatement(SQL_GET_ALL);
//            ResultSet resultSet = preparedStatement.executeQuery();
//            while (resultSet.next()) {
//                Order receivedUser = new Order();
//
//            }
//            private String orderDate;
//            private int totalPrice;
//            private int bonusPoints;
//            private List<Product> products;
//            private String review;
//            private Account account;
//            private PaymentMethod paymentMethod;
    //
}

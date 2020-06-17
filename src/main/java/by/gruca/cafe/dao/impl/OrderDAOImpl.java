package by.gruca.cafe.dao.impl;


import by.gruca.cafe.dao.AccountDAO;
import by.gruca.cafe.dao.OrderDAO;
import by.gruca.cafe.dao.connectionpool.ConnectionProxy;
import by.gruca.cafe.dao.connectionpool.SQLConnectionPool;
import by.gruca.cafe.dao.exception.DAOException;
import by.gruca.cafe.factory.DAOFactory;
import by.gruca.cafe.model.Order;
import by.gruca.cafe.model.Product;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;


public class OrderDAOImpl implements OrderDAO {
    private static final String SQL_ATTACH_PRODUCTS = "insert into order_detail(id, order_id, product_id, quantity) values(DEFAULT,?,?,?)";
    private static final String SQL_GET = "select * from order where id=(?)";
    private static final String SQL_GET_ALL = "select * from order";
    private static final String SQL_GET_ALL_BY_LOGIN = "select * from order join account on `order`.id = account.id where email=?";
    private static final String SQL_CREATE = "insert into order (id,date,price,account_id,review) values(DEFAULT,CURRENT_TIMESTAMP,?,?,?)";
    private static final String SQL_UPDATE = "update order set date=?,price=?,account_id=?,review=?" +
            "where id=?";
    private static final String SQL_DELETE = "delete from order where id=?";
    private static final String SQL_GET_PRODUCTS = "select product_id from order join product on order.product_id=product.id where order.id=?";
    private final AccountDAO accountDAO =new AccountDAOImpl();
    Logger logger = LogManager.getLogger(OrderDAOImpl.class);

    public OrderDAOImpl() {
    }

    @Override
    public boolean create(Order order) throws DAOException {
        try (ConnectionProxy connection = SQLConnectionPool.INSTANCE.getConnection();
             PreparedStatement statement = connection.prepareStatement(SQL_CREATE)) {
            statement.setDouble(1, order.getPrice());
            statement.setInt(2, order.getAccount().getId());
            statement.setString(3, order.getReview());
            return statement.execute();
        } catch (SQLException e) {
            logger.error(e);
            throw new DAOException("SQL statement error", e);
        }
    }

    @Override
    public Optional<Order> read(Integer orderId) throws DAOException {
        Order order = new Order();
        try (ConnectionProxy connection = SQLConnectionPool.INSTANCE.getConnection();
             PreparedStatement readStatement = connection.prepareStatement(SQL_GET)) {
            readStatement.setString(1, orderId.toString());
            try (ResultSet resultSet = readStatement.executeQuery()) {
                if (resultSet.next()) {
                    order.setId(resultSet.getInt("id"));
                    order.setReview(resultSet.getString("review"));
                    order.setAccount(accountDAO.read(resultSet.getString("login")).get());
                    order.setPrice(resultSet.getInt("price"));
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
    public int update(Order order) throws DAOException {
        try (ConnectionProxy connection = SQLConnectionPool.INSTANCE.getConnection();
             PreparedStatement statement = connection.prepareStatement(SQL_UPDATE)) {
            statement.setString(1, order.getDate().toString());
            statement.setDouble(2, order.getPrice());
            statement.setInt(3, order.getAccount().getId());
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
    public List<Order> getAllByAccount(String login) throws DAOException {
        try (ConnectionProxy connection = SQLConnectionPool.INSTANCE.getConnection();
             PreparedStatement statement = connection.prepareStatement(SQL_GET_ALL_BY_LOGIN)) {
            statement.setString(1, login);
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

    private void attachProductsToOrder(List<Product> products, Order order) throws DAOException {
        try (ConnectionProxy connection = SQLConnectionPool.INSTANCE.getConnection();
             PreparedStatement statement = connection.prepareStatement(SQL_ATTACH_PRODUCTS)) {
            for (Product product : products) {
                statement.setInt(1, order.getId());
                statement.setInt(2, product.getId());
                statement.setInt(3, product.getQuantity());
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

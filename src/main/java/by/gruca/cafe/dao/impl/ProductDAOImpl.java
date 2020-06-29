package by.gruca.cafe.dao.impl;

import by.gruca.cafe.dao.ProductDAO;
import by.gruca.cafe.dao.connectionpool.ConnectionProxy;
import by.gruca.cafe.dao.connectionpool.SQLConnectionPool;
import by.gruca.cafe.dao.exception.DAOException;
import by.gruca.cafe.model.Product;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ProductDAOImpl implements by.gruca.cafe.dao.ProductDAO {
    private static final String SQL_GET_ALL = "select * from product";
    private static final String SQL_GET = "select * from product where id=? ";
    private static final String SQL_CREATE = "insert into product (id,name,price,description) " +
            "values(DEFAULT,?,?,?)";
    private static final String SQL_UPDATE = "update product set name=?,price=?,description=? where id=?";
    private static final String SQL_DELETE = "delete from product where id=? ";
    private static final String SQL_GET_BY_NAME = "SELECT * FROM product WHERE name=?";
    Logger logger = LogManager.getLogger(ProductDAOImpl.class);


    public ProductDAOImpl() {
    }

    @Override
    public boolean create(Product product) throws DAOException {
        try (ConnectionProxy connection = SQLConnectionPool.INSTANCE.getConnection();
             PreparedStatement statement = connection.prepareStatement(SQL_CREATE)) {
            statement.setString(1, product.getName());
            statement.setDouble(2, product.getPrice());
            statement.setString(3, product.getDescription());
            return statement.execute();
        } catch (SQLException e) {
            logger.error(e);
            throw new DAOException("SQL statement error", e);
        }
    }

    @Override
    public Optional<Product> read(Integer productId) throws DAOException {
        Product product = new Product();
        try (ConnectionProxy connection = SQLConnectionPool.INSTANCE.getConnection();
             PreparedStatement readStatement = connection.prepareStatement(SQL_GET)) {
            readStatement.setInt(1, productId);
            try (ResultSet resultSet = readStatement.executeQuery()) {
                if (resultSet.next()) {
                    product.setId(resultSet.getInt("id"));
                    product.setDescription(resultSet.getString("description"));
                    product.setPrice(resultSet.getDouble("price"));
                    product.setName(resultSet.getString("name"));
                } else throw new DAOException("Product is not exist");
            } catch (SQLException e) {
                logger.error(e);
            }
        } catch (SQLException e) {
            logger.error(e);
            throw new DAOException("SQL statement error", e);
        }
        return Optional.of(product);
    }

    @Override
    public int update(Product product) throws DAOException {
        try (ConnectionProxy connection = SQLConnectionPool.INSTANCE.getConnection();
             PreparedStatement statement = connection.prepareStatement(SQL_UPDATE)) {
            statement.setString(1, product.getName());
            statement.setDouble(2, product.getPrice());
            statement.setString(3, product.getDescription());
            statement.setInt(4, product.getId());
            int rowsAffected = statement.executeUpdate();
            logger.info(rowsAffected + " row affected");
            return rowsAffected;

        } catch (SQLException e) {
            logger.error(e);
            throw new DAOException("SQL statement error", e);
        }
    }

    @Override
    public Optional<Product> getProductByName(String productName) throws DAOException {
        Product product = new Product();
        try (ConnectionProxy connection = SQLConnectionPool.INSTANCE.getConnection();
             PreparedStatement readStatement = connection.prepareStatement(SQL_GET_BY_NAME)) {
            readStatement.setString(1, productName);
            try (ResultSet resultSet = readStatement.executeQuery()) {
                if (resultSet.next()) {
                    product.setId(resultSet.getInt("id"));
                    product.setDescription(resultSet.getString("description"));
                    product.setPrice(resultSet.getDouble("price"));
                    product.setName(resultSet.getString("name"));
                } else throw new DAOException("Product is not exist");
            } catch (SQLException e) {
                logger.error(e);
            }
        } catch (SQLException e) {
            logger.error(e);
            throw new DAOException("SQL statement error", e);
        }
        return Optional.of(product);

    }

    @Override
    public boolean delete(Product product) throws DAOException {
        try (ConnectionProxy connection = SQLConnectionPool.INSTANCE.getConnection();
             PreparedStatement statement = connection.prepareStatement(SQL_DELETE);) {
            statement.setInt(1, product.getId());
            return statement.execute();
        } catch (SQLException e) {
            logger.error(e);
            throw new DAOException("SQL statement error", e);
        }
    }
@Override
    public List<Product> getAllProducts() throws DAOException {
        List<Product> productList = new ArrayList<Product>();
        try (ConnectionProxy connection = SQLConnectionPool.INSTANCE.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SQL_GET_ALL);
             ResultSet resultSet = preparedStatement.executeQuery()) {
            while (resultSet.next()) {
                Product product = new Product();
                product.setName(resultSet.getString("name"));
                product.setPrice(Integer.parseInt(resultSet.getString("price")));
                product.setDescription(resultSet.getString("description"));
                product.setId(resultSet.getInt("id"));
                productList.add(product);
            }
        } catch (SQLException e) {
            logger.error(e);
            throw new DAOException("getAll query error", e);
        }

        return productList;
    }
}

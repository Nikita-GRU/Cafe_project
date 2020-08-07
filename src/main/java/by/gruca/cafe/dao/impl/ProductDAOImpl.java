package by.gruca.cafe.dao.impl;

import by.gruca.cafe.dao.connectionpool.ConnectionProxy;
import by.gruca.cafe.dao.connectionpool.SQLConnectionPool;
import by.gruca.cafe.dao.exception.DAOException;
import by.gruca.cafe.model.Category;
import by.gruca.cafe.model.Product;
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

public class ProductDAOImpl implements by.gruca.cafe.dao.ProductDAO {
    public static final String SQL_STATEMENT_ERROR = "SQL statement error";
    public static final String PRODUCT_ID = "product_id";
    public static final String PRODUCT_DESCRIPTION = "description";
    public static final String PRODUCT_PRICE = "price";
    public static final String PRODUCT_NAME = "name";
    public static final String PRODUCT_IMAGE_URI = "image_uri";
    public static final String PRODUCT_BONUS = "bonus";
    private static final String SQL_READ_ALL_BY_CATEGORY = "SELECT * FROM product " +
            "JOIN category ON product.category_id = category.category_id WHERE category.category_name=? ORDER BY product.product_id";
    private static final String SQL_READ_ALL = "SELECT * FROM product  " +
            "JOIN category ON product.category_id = category.category_id ORDER BY product.product_id";
    private static final String SQL_READ = "SELECT * FROM product " +
            "JOIN category ON product.category_id = category.category_id WHERE product_id=? ";
    private static final String SQL_CREATE = "INSERT INTO product (product_id,category_id,name,price," +
            "description,image_uri,bonus) " +
            "VALUES(DEFAULT,?,?,?,?,?,?)";
    private static final String SQL_UPDATE = "UPDATE product SET category_id=?,name=?,price=?," +
            "description=?, image_uri=?, bonus=? WHERE product_id=?";
    private static final String SQL_DELETE = "DELETE FROM product WHERE product_id=? ";
    private static final String SQL_READ_BY_NAME = "SELECT * FROM product " +
            "JOIN category ON product.category_id = category.category_id WHERE name=?";
    private static final String SQL_READ_PAGINATED_PRODUCTS = "SELECT * FROM product " +
            "JOIN category ON product.category_id = category.category_id ORDER BY product.product_id " +
            "LIMIT ? OFFSET ?";
    private static final String PRODUCTS_COUNT = "products_count";
    private static final String SQL_READ_PRODUCTS_COUNT = "SELECT COUNT(*) products_count FROM product";
    Logger logger = LogManager.getLogger(ProductDAOImpl.class);

    @Override
    public long create(Product entity) throws DAOException {
        try (ConnectionProxy connection = SQLConnectionPool.INSTANCE.getConnection();
             PreparedStatement statement = connection.prepareStatement(SQL_CREATE, Statement.RETURN_GENERATED_KEYS)) {
            statement.setInt(1, entity.getCategory().getId());
            statement.setString(2, entity.getName());
            statement.setBigDecimal(3, entity.getPrice());
            statement.setString(4, entity.getDescription());
            statement.setString(5, entity.getImageUri());
            statement.setInt(6, entity.getBonus());
            statement.execute();
            ResultSet resultSet = statement.getGeneratedKeys();
            if (resultSet.next()) {
                return resultSet.getInt(1);
            }
            return -1;
        } catch (SQLException e) {
            logger.error(e);
            throw new DAOException("SQL create error", e);
        }
    }

    @Override
    public Optional<Product> read(long id) throws DAOException {
        Product product;
        try (ConnectionProxy connection = SQLConnectionPool.INSTANCE.getConnection();
             PreparedStatement readStatement = connection.prepareStatement(SQL_READ)) {
            readStatement.setLong(1, id);
            product = buildProduct(readStatement);
        } catch (SQLException e) {
            logger.error(e);
            throw new DAOException(SQL_STATEMENT_ERROR, e);
        }
        return Optional.ofNullable(product);
    }

    @Override
    public int update(Product entity) throws DAOException {
        try (ConnectionProxy connection = SQLConnectionPool.INSTANCE.getConnection();
             PreparedStatement statement = connection.prepareStatement(SQL_UPDATE)) {
            statement.setInt(1, entity.getCategory().getId());
            statement.setString(2, entity.getName());
            statement.setBigDecimal(3, entity.getPrice());
            statement.setString(4, entity.getDescription());
            statement.setString(5, entity.getImageUri());
            statement.setInt(6, entity.getBonus());
            statement.setLong(7, entity.getId());
            return statement.executeUpdate();
        } catch (SQLException e) {
            logger.error(e);
            throw new DAOException("SQL update error", e);
        }
    }

    @Override
    public Optional<Product> readByName(String productName) throws DAOException {
        Product product = new Product();
        try (ConnectionProxy connection = SQLConnectionPool.INSTANCE.getConnection();
             PreparedStatement readStatement = connection.prepareStatement(SQL_READ_BY_NAME)) {
            readStatement.setString(1, productName);
            product = buildProduct(readStatement);
        } catch (SQLException e) {
            logger.error(e);
            throw new DAOException(SQL_STATEMENT_ERROR, e);
        }
        return Optional.ofNullable(product);

    }

    @Override
    public List<Product> readAll() throws DAOException {
        List<Product> products;
        try (ConnectionProxy connection = SQLConnectionPool.INSTANCE.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SQL_READ_ALL);) {
            products = buildProductList(preparedStatement);
        } catch (SQLException e) {
            logger.error(e);
            throw new DAOException("getAll query error", e);
        }

        return products;
    }

    @Override
    public List<Product> readAll(int itemsPerPage, int pageNumber) throws DAOException {
        List<Product> products;
        try (ConnectionProxy connection = SQLConnectionPool.INSTANCE.getConnection();
             PreparedStatement statement =
                     connection.prepareStatement(SQL_READ_PAGINATED_PRODUCTS);) {
            statement.setInt(1, itemsPerPage);
            statement.setInt(2, (pageNumber - 1) * itemsPerPage);
            products = buildProductList(statement);
        } catch (SQLException e) {
            logger.error(e);
            throw new DAOException(SQL_STATEMENT_ERROR, e);
        }
        return products;
    }

    @Override
    public int readProductCount() throws DAOException {
        int productsCount = 0;
        try (ConnectionProxy connection = SQLConnectionPool.INSTANCE.getConnection();
             PreparedStatement statement = connection.prepareStatement(SQL_READ_PRODUCTS_COUNT);
             ResultSet rs = statement.executeQuery();) {
            if (rs.next()) {
                productsCount = rs.getInt(PRODUCTS_COUNT);
            }
        } catch (SQLException e) {
            logger.error(e);
            throw new DAOException(SQL_STATEMENT_ERROR, e);
        }
        return productsCount;
    }

    @Override
    public boolean delete(Product entity) throws DAOException {
        try (ConnectionProxy connection = SQLConnectionPool.INSTANCE.getConnection();
             PreparedStatement statement = connection.prepareStatement(SQL_DELETE);) {
            statement.setLong(1, entity.getId());
            return statement.execute();
        } catch (SQLException e) {
            logger.error(e);
            throw new DAOException("SQL delete error", e);
        }
    }

    @Override
    public List<Product> readAllByCategory(Category category) throws DAOException {
        List<Product> products;
        try (ConnectionProxy connection = SQLConnectionPool.INSTANCE.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SQL_READ_ALL_BY_CATEGORY);) {
            preparedStatement.setString(1, category.getCategory());
            products = buildProductList(preparedStatement);
        } catch (SQLException e) {
            logger.error(e);
            throw new DAOException("getAll query error", e);
        }

        return products;
    }

    private Product buildProduct(PreparedStatement readStatement) throws DAOException {
        Product product = null;
        try (ResultSet rs = readStatement.executeQuery()) {
            if (rs.next()) {
                product = ModelMapper.INSTANCE.getProductFromResultSet(rs);
            }
        } catch (SQLException e) {
            logger.error(e);
            throw new DAOException(e);
        }
        return product;
    }

    private List<Product> buildProductList(PreparedStatement readStatement) throws DAOException {
        List<Product> products = new ArrayList<>();
        Product product;
        try (ResultSet rs = readStatement.executeQuery()) {
            while (rs.next()) {
                product = ModelMapper.INSTANCE.getProductFromResultSet(rs);
                products.add(product);
            }
        } catch (SQLException e) {
            logger.error(e);
            throw new DAOException(e);
        }
        return products;
    }


}

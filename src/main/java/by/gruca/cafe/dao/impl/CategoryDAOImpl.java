package by.gruca.cafe.dao.impl;

import by.gruca.cafe.dao.CategoryDAO;
import by.gruca.cafe.util.ModelMapper;
import by.gruca.cafe.dao.connectionpool.ConnectionProxy;
import by.gruca.cafe.dao.connectionpool.SQLConnectionPool;
import by.gruca.cafe.dao.exception.DAOException;
import by.gruca.cafe.model.Category;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class CategoryDAOImpl implements CategoryDAO {
    public static final String SQL_STATEMENT_ERROR = "SQL statement error";
    public static final String CATEGORY_CATEGORY_NAME = "category_name";
    private static final String SQL_READ = "SELECT * FROM category WHERE category_id=? ";
    private static final String SQL_READ_ALL = "SELECT * from category";
    private static final String SQL_CREATE = "INSERT INTO category (category_id,category_name) " +
            "VALUES (DEFAULT,?)";
    private static final String SQL_UPDATE = "UPDATE category SET category_name=? WHERE category_id=?";
    private static final String SQL_DELETE = "DELETE FROM category WHERE category_id=? ";
    private static final long CREATE_ERROR = -1;
    Logger logger = LogManager.getLogger(CategoryDAOImpl.class);

    @Override
    public long create(Category entity) throws DAOException {
        try (ConnectionProxy connection = SQLConnectionPool.INSTANCE.getConnection();
             PreparedStatement statement = connection.prepareStatement(SQL_CREATE, Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, entity.getCategory());
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
    public Optional<Category> read(long id) throws DAOException {
        Category category = null;
        try (ConnectionProxy connection = SQLConnectionPool.INSTANCE.getConnection();
             PreparedStatement readStatement = connection.prepareStatement(SQL_READ)) {
            readStatement.setLong(1, id);
            category = buildCategory(readStatement);
        } catch (SQLException e) {
            logger.error(e);
            throw new DAOException(SQL_STATEMENT_ERROR, e);
        }
        return Optional.ofNullable(category);

    }


    @Override
    public int update(Category entity) throws DAOException {
        try (ConnectionProxy connection = SQLConnectionPool.INSTANCE.getConnection();
             PreparedStatement statement = connection.prepareStatement(SQL_UPDATE)) {
            statement.setString(1, entity.getCategory());
            statement.setLong(2, entity.getId());
            return statement.executeUpdate();

        } catch (SQLException e) {
            logger.error(e);
            throw new DAOException(SQL_STATEMENT_ERROR, e);
        }
    }

    @Override
    public boolean delete(Category entity) throws DAOException {
        try (ConnectionProxy connection = SQLConnectionPool.INSTANCE.getConnection();
             PreparedStatement statement = connection.prepareStatement(SQL_DELETE)) {
            statement.setLong(1, entity.getId());
            return statement.execute();
        } catch (SQLException e) {
            logger.error(e);
            throw new DAOException(SQL_STATEMENT_ERROR, e);
        }
    }

    @Override
    public List<Category> readAll() throws DAOException {
        List<Category> categories;
        try (ConnectionProxy connection = SQLConnectionPool.INSTANCE.getConnection();
             PreparedStatement statement = connection.prepareStatement(SQL_READ_ALL);) {
            categories = buildCategoryList(statement);
        } catch (SQLException e) {
            logger.error(e);
            throw new DAOException(SQL_STATEMENT_ERROR, e);
        }
        return categories;
    }


    private Category buildCategory(PreparedStatement readStatement) throws DAOException {
        Category category = null;
        try (ResultSet rs = readStatement.executeQuery()) {
            if (rs.next()) {
                category = ModelMapper.INSTANCE.getCategoryFromResultSet(rs);
            }
        } catch (SQLException e) {
            logger.error(e);
            throw new DAOException(e);
        }
        return category;
    }

    private List<Category> buildCategoryList(PreparedStatement readStatement) throws DAOException {
        List<Category> categories = new ArrayList<>();
        Category category;
        try (ResultSet rs = readStatement.executeQuery()) {
            while (rs.next()) {
                category = ModelMapper.INSTANCE.getCategoryFromResultSet(rs);
                categories.add(category);
            }
        } catch (SQLException e) {
            logger.error(e);
            throw new DAOException(e);
        }
        return categories;
    }
}

package by.gruca.cafe.DAO.repository.impl;

import by.gruca.cafe.DAO.db.ConnectionProxy;
import by.gruca.cafe.DAO.db.SQLConnectionPool;
import by.gruca.cafe.DAO.exception.DAOException;
import by.gruca.cafe.DAO.repository.DAO;
import by.gruca.cafe.model.Product;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

public class ProductDAOImpl implements DAO<Product, String> {
    private static final String SQL_GET_ALL = "select * from product";

    @Override
    public boolean create(Product product) throws DAOException, DAOException {
        return false;
    }

    @Override
    public Optional<Product> read(String productName) throws DAOException {
        return Optional.empty();
    }

    @Override
    public int update(Product model) throws DAOException {
        return 0;
    }

    @Override
    public boolean delete(Product model) throws DAOException {
        return false;
    }

    public List<Product> getAll() throws DAOException {
        List productList = new ArrayList();

        ConnectionProxy connection = SQLConnectionPool.INSTANCE.getConnection();
        if (Objects.isNull(connection)) {
            throw new RuntimeException("No connection");
        }
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(SQL_GET_ALL);
            ResultSet resultSet = null;
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Product product = new Product();
                product.setName(resultSet.getString("name"));
                product.setPrice(Integer.parseInt(resultSet.getString("price")));
                product.setDescription(resultSet.getString("description"));
                productList.add(product);
            }
        } catch (SQLException e) {
            throw new DAOException("getAll query error", e);
        }

        return productList;
    }
}

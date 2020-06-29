package by.gruca.cafe.dao;

import by.gruca.cafe.dao.exception.DAOException;
import by.gruca.cafe.model.Product;

import java.util.List;
import java.util.Optional;

public interface ProductDAO extends AbstractDAO<Product, Integer> {

    Optional<Product> getProductByName(String productName) throws DAOException;

    List<Product> getAllProducts() throws DAOException;
}

package by.gruca.cafe.dao;

import by.gruca.cafe.dao.exception.DAOException;
import by.gruca.cafe.model.Category;
import by.gruca.cafe.model.Product;

import java.util.List;
import java.util.Optional;

public interface ProductDAO extends AbstractDAO<Product> {

    Optional<Product> readByName(String productName) throws DAOException;

    List<Product> readAllByCategory(Category category) throws DAOException;

    List<Product> readAll(int itemsPerPage, int pageNumber) throws DAOException;

    int readProductCount() throws DAOException;
}

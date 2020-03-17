package DAO.repository;

import DAO.exception.DAOException;
import model.Order;
import model.Product;

import java.util.List;
import java.util.Optional;

public interface ProductRepository {
    Optional<Product> get(int id) throws DAOException;

    List<Product> getAll() throws DAOException;

    void save(Product product) throws DAOException;

    void update(Product product) throws DAOException;

    void delete(Product product) throws DAOException;
}

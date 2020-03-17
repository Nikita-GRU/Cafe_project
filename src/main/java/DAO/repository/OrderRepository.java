package DAO.repository;

import DAO.exception.DAOException;
import model.Order;

import java.util.List;
import java.util.Optional;

public interface OrderRepository {
    Optional<Order> get(int id) throws DAOException;

    List<Order> getAll() throws DAOException;

    void save(Order order) throws DAOException;

    void update(Order order) throws DAOException;

    void delete(Order order) throws DAOException;

}

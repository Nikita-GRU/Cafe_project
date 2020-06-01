package by.gruca.cafe.DAO.repository;



import by.gruca.cafe.DAO.exception.DAOException;
import by.gruca.cafe.model.Order;

import java.util.List;
import java.util.Optional;

public interface OrderDAO {
    Optional<Order> get(int id) throws DAOException;

    List<Order> getAll() throws DAOException;

    void save(Order order) throws DAOException;

    void update(Order order) throws DAOException;

    void delete(Order order) throws DAOException;

}

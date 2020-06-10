package by.gruca.cafe.dao;


import by.gruca.cafe.dao.exception.DAOException;
import by.gruca.cafe.model.Order;
import by.gruca.cafe.model.Product;

import java.util.List;

public interface OrderDAO extends AbstractDAO<Order, Integer> {

    List<Order> getAll() throws DAOException;

    List<Order> getAllByAccount(String login) throws DAOException;


}

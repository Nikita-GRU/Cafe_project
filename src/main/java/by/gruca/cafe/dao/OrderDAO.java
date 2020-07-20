package by.gruca.cafe.dao;


import by.gruca.cafe.dao.exception.DAOException;
import by.gruca.cafe.model.Account;
import by.gruca.cafe.model.Order;
import by.gruca.cafe.model.Product;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;

public interface OrderDAO extends AbstractDAO<Order, Integer> {

    List<Order> getAll() throws DAOException;

    List<Order> getAllByAccount(String accountEmail) throws DAOException;

    void attachProductsToOrder(HashMap<Product, Integer> products, int orderId) throws DAOException;

    Optional<Order> readByTimeAndAccount(String time, Account account) throws DAOException;

    List<Order> getNotDeliveredOrders() throws DAOException;

    HashMap<Product, Integer> getOrderProducts(Integer orderId) throws DAOException;

    void setAccepted(int orderId) throws DAOException;

    void setDelivered(int orderId) throws DAOException;
}

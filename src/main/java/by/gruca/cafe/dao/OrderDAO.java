package by.gruca.cafe.dao;


import by.gruca.cafe.dao.exception.DAOException;
import by.gruca.cafe.model.Order;

import java.util.List;

public interface OrderDAO extends AbstractDAO<Order> {


    List<Order> readNotDeliveredOrders() throws DAOException;

    List<Order> readAllByAccount(long accountId) throws DAOException;

    List<Order> readAll(int itemsPerPage, int pageNumber) throws DAOException;

    int readOrdersCount() throws DAOException;

}

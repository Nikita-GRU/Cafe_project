package by.gruca.cafe.service.impl;

import by.gruca.cafe.dao.exception.DAOException;
import by.gruca.cafe.factory.DAOFactory;
import by.gruca.cafe.model.Order;
import by.gruca.cafe.model.Product;
import by.gruca.cafe.service.OrderService;
import by.gruca.cafe.service.exception.ServiceException;
import by.gruca.cafe.util.TimeConverter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.HashMap;
import java.util.List;

public class OrderServiceImpl implements OrderService {
    Logger logger = LogManager.getLogger(OrderServiceImpl.class);

    @Override
    public Order createOrder(Order order, HashMap<Product, Integer> products) throws ServiceException {
        try {
            DAOFactory.INSTANCE.getOrderDAO().create(order);
            Order order2 = DAOFactory.INSTANCE.getOrderDAO().readByTimeAndAccount(TimeConverter.convertFromLocalDateTimeToSQLDateTime(order.getDate()), order.getAccount()).get();
            DAOFactory.INSTANCE.getOrderDAO().attachProductsToOrder(products, order2);
        } catch (DAOException e) {
            logger.error(e);
        }
        return null;
    }

    @Override
    public List<Order> getNotDeliveredOrders() throws ServiceException {
        try {
            return DAOFactory.INSTANCE.getOrderDAO().getNotDeliveredOrders();
        } catch (DAOException e) {
            logger.error(e);
            throw new ServiceException(e);
        }
    }

    @Override
    public void setOrderAccepted(int orderId) throws ServiceException {
        try {
            DAOFactory.INSTANCE.getOrderDAO().setAccepted(orderId);
        } catch (DAOException e) {
            logger.error(e);
            throw new ServiceException(e);
        }
    }

    @Override
    public void setOrderDelivered(int orderId) throws ServiceException {
        try {
            DAOFactory.INSTANCE.getOrderDAO().setDelivered(orderId);
        } catch (DAOException e) {
            logger.error(e);
            throw new ServiceException(e);
        }
    }
}

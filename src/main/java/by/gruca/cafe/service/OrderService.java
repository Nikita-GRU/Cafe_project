package by.gruca.cafe.service;


import by.gruca.cafe.model.Order;
import by.gruca.cafe.model.Product;
import by.gruca.cafe.service.exception.ServiceException;

import java.util.HashMap;
import java.util.List;

public interface OrderService {
     Order createOrder(Order order, HashMap<Product, Integer> products) throws ServiceException;

     List<Order> getNotDeliveredOrders() throws ServiceException;


     void setOrderAccepted(int orderId) throws ServiceException;
     void setOrderDelivered(int orderId) throws ServiceException;
}

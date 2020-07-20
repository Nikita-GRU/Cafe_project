package by.gruca.cafe.service;


import by.gruca.cafe.model.Account;
import by.gruca.cafe.model.Order;
import by.gruca.cafe.model.Product;
import by.gruca.cafe.service.exception.ServiceException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public interface OrderService {
    Order createOrder(Order order, HashMap<Product, Integer> products) throws ServiceException;

    List<Order> getNotDeliveredOrders() throws ServiceException;

    void setOrderAccepted(int orderId) throws ServiceException;

    void setOrderDelivered(int orderId) throws ServiceException;

    List<Order> getAllOrders() throws ServiceException;

    void deleteOrder(int orderId) throws ServiceException;

    List<Order> getOrdersByAccount(Account account) throws ServiceException;

    void createOrder(ArrayList<Product> products, String param, String emailParam, String reviewParam, String bonusToPay,
                     String paymentType, String street, String apartment, String building, String deliveryType) throws ServiceException;

    void createGuestOrder(ArrayList<Product> products, String emailParam, String reviewParam, String street, String apartment,
                          String building, String deliveryType, String deliveryDate) throws ServiceException;
}

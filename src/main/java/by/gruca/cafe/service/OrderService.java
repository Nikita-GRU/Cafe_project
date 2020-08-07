package by.gruca.cafe.service;


import by.gruca.cafe.model.Account;
import by.gruca.cafe.model.Order;
import by.gruca.cafe.model.Product;
import by.gruca.cafe.service.exception.ServiceException;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

public interface OrderService {

    Order getOrder(String orderIdParam) throws ServiceException;

    List<Order> getAllOrders() throws ServiceException;

    void deleteOrder(int orderId) throws ServiceException;

    void createOrder(Map<Product, Integer> products, Account account, String noteParam, String addressParam, String apartment,
                     String deliveryType, String deliveryDate, BigDecimal price, String paymentType, String bonusToPay) throws ServiceException;

    void createGuestOrder(Map<Product, Integer> products, Account emailParam, String noteParam, String addressParam, String apartment,
                           String deliveryType, String deliveryDate, BigDecimal price) throws ServiceException;

    List<Order> getNotDeliveredOrders() throws ServiceException;

    List<Order> getOrdersByAccount(Account account) throws ServiceException;

    void updateOrderStatus(String orderIdToUpdate, String orderStatusParam) throws ServiceException;

    List<Order> getPaginatedOrders(int itemsPerPage, int pageNumber) throws ServiceException;

    int getOrdersCount() throws ServiceException;

    void updateOrderFeedback(String orderIdParam, String feedbackParam, String scoreParam) throws ServiceException;
}

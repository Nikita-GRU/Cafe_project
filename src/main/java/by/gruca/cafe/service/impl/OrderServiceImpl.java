package by.gruca.cafe.service.impl;

import by.gruca.cafe.dao.exception.DAOException;
import by.gruca.cafe.factory.DAOFactory;
import by.gruca.cafe.factory.ServiceFactory;
import by.gruca.cafe.model.*;
import by.gruca.cafe.service.OrderService;
import by.gruca.cafe.service.exception.ServiceException;
import by.gruca.cafe.util.RequestParameterConverter;
import by.gruca.cafe.util.UtilException;
import by.gruca.cafe.util.Validator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class OrderServiceImpl implements OrderService {
    public static final double BONUS_CONVERTER = 0.01;
    public static final int GUEST_START_BONUS = 0;
    public static final BigDecimal BONUS_TO_BALANCE_COEFFICIENT = BigDecimal.valueOf(0.01);
    public static final int FIRST_PAGE_NUMBER = 1;
    public static final RequestParameterConverter CONVERTER = RequestParameterConverter.INSTANCE;
    public static final int FIRST_PAGE = 1;
    public static final int DEFAULT_ITEMS_PER_PAGE = 10;
    Logger logger = LogManager.getLogger(OrderServiceImpl.class);

    @Override
    public List<Order> getNotDeliveredOrders() throws ServiceException {
        try {
            return DAOFactory.INSTANCE.getOrderDAO().readNotDeliveredOrders();
        } catch (DAOException e) {
            logger.error(e);
            throw new ServiceException(e);
        }
    }

    @Override
    public Order getOrder(String orderIdParam) throws ServiceException {
        Order order;
        try {
            long orderId = RequestParameterConverter.INSTANCE.valueOfLong(orderIdParam);
            order = DAOFactory.INSTANCE.getOrderDAO().read(orderId).orElseThrow(() -> new ServiceException("Order not found"));
        } catch (DAOException | UtilException e) {
            logger.error(e);
            throw new ServiceException(e);
        }
        return order;
    }

    @Override
    public List<Order> getOrdersByAccount(Account account) throws ServiceException {
        List<Order> orders;
        try {
            long accountId = account.getId();
            orders = DAOFactory.INSTANCE.getOrderDAO().readAllByAccount(accountId);
        } catch (DAOException e) {
            logger.error(e);
            throw new ServiceException(e);
        }
        return orders;
    }

    @Override
    public List<Order> getAllOrders() throws ServiceException {
        List<Order> orders;
        try {
            orders = DAOFactory.INSTANCE.getOrderDAO().readAll();
        } catch (DAOException e) {
            logger.error(e);
            throw new ServiceException(e);
        }
        return orders;
    }

    @Override
    public List<Order> getPaginatedOrders(int itemsPerPage, int pageNumber) throws ServiceException {
        List<Order> orders;
        try {
            orders = DAOFactory.INSTANCE.getOrderDAO().readAll(itemsPerPage, pageNumber);
        } catch (DAOException e) {
            logger.error(e);
            throw new ServiceException(e);
        }
        return orders;
    }

    @Override
    public int getOrdersCount() throws ServiceException {
        int ordersCount;
        try {
            ordersCount = DAOFactory.INSTANCE.getOrderDAO().readOrdersCount();
        } catch (DAOException e) {
            logger.error(e);
            throw new ServiceException(e);
        }
        return ordersCount;
    }

    @Override
    public void deleteOrder(int orderId) throws ServiceException {
        try {
            Optional<Order> order = DAOFactory.INSTANCE.getOrderDAO().read(orderId);
            DAOFactory.INSTANCE.getOrderDAO().delete(order.orElse(new Order()));
        } catch (DAOException e) {
            logger.error(e);
            throw new ServiceException(e);
        }

    }

    @Override
    public void createGuestOrder(Map<Product, Integer> products, Account account, String noteParam, String addressParam,
                                 String apartmentParam, String deliveryTypeParam, String deliveryDateParam,
                                 BigDecimal price) throws ServiceException {

        if (products != null && products.size() > 0 && Validator.validateReview(noteParam)) {
            try {
                DeliveryType deliveryType = CONVERTER.valueOfDeliveryTypeParam(deliveryTypeParam);
                LocalDateTime deliveryTime = CONVERTER.valueOfLocalDateTimeParam(deliveryDateParam);
                Order order = buildOrder(account, noteParam, addressParam,
                        apartmentParam, deliveryType, deliveryTime, price, PaymentType.CASH, OrderStatus.ORDERED, 0);
                long orderId = DAOFactory.INSTANCE.getOrderDAO().create(order);

                List<OrderDetail> orderDetails = buildOrderDetailList(products, orderId);
                for (OrderDetail orderDetail : orderDetails) {
                    DAOFactory.INSTANCE.getOrderDetailDAO().create(orderDetail);
                }
            } catch (DAOException | UtilException e) {
                logger.error(e);
                throw new ServiceException(e);
            }
        } else throw new ServiceException("Invalid order input");
    }


    @Override
    public void createOrder(Map<Product, Integer> products, Account account, String noteParam, String addressParam,
                            String apartmentParam, String deliveryTypeParam, String deliveryDateParam, BigDecimal price,
                            String paymentTypeParam, String bonusToPayParam) throws ServiceException {

        if (products != null && products.size() > 0 && Validator.validateReview(noteParam)) {
            try {
                PaymentType paymentType = CONVERTER.valueOfPaymentTypeParam(paymentTypeParam);
                DeliveryType deliveryType = CONVERTER.valueOfDeliveryTypeParam(deliveryTypeParam);
                LocalDateTime deliveryTime = CONVERTER.valueOfLocalDateTimeParam(deliveryDateParam);
                int bonusToPay = RequestParameterConverter.INSTANCE.valueOfInteger(bonusToPayParam);

                price = price.subtract(BigDecimal.valueOf(bonusToPay).multiply(BONUS_TO_BALANCE_COEFFICIENT));

                OrderStatus orderStatus =
                        (paymentType != PaymentType.CASH || price.equals(BigDecimal.ZERO)) ?
                                OrderStatus.PAID : OrderStatus.ORDERED;

                Order order = buildOrder(account, noteParam, addressParam,
                        apartmentParam,
                        deliveryType, deliveryTime, price, paymentType, orderStatus, bonusToPay);
                long orderId = DAOFactory.INSTANCE.getOrderDAO().create(order);

                List<OrderDetail> orderDetails = buildOrderDetailList(products, orderId);
                for (OrderDetail orderDetail : orderDetails) {
                    DAOFactory.INSTANCE.getOrderDetailDAO().create(orderDetail);
                }

                ServiceFactory.INSTANCE.getAccountService().updateAccountBalanceAndBonus(account, price, bonusToPay, paymentType);

            } catch (DAOException | UtilException e) {
                logger.error(e);
                throw new ServiceException(e);
            }
        } else throw new ServiceException("Invalid order input");
    }

    @Override
    public void updateOrderStatus(String orderIdToUpdate, String orderStatusParam) throws ServiceException {

        try {
            OrderStatus orderStatus = CONVERTER.valueOfOrderStatus(orderStatusParam);
            Order order = getOrder(orderIdToUpdate);
            order.setOrderStatus(orderStatus);
            DAOFactory.INSTANCE.getOrderDAO().update(order);
        } catch (DAOException | UtilException e) {
            logger.error(e);
            throw new ServiceException(e);
        }
    }

    @Override
    public void updateOrderFeedback(String orderIdParam, String feedbackParam, String scoreParam) throws ServiceException {
        try {
            int score = RequestParameterConverter.INSTANCE.valueOfInteger(scoreParam);
            Order order = getOrder(orderIdParam);
            order.setFeedback(feedbackParam);
            order.setScore(score);
            DAOFactory.INSTANCE.getOrderDAO().update(order);
        } catch (DAOException | UtilException e) {
            logger.error(e);
            throw new ServiceException(e);
        }
    }

    private List<OrderDetail> buildOrderDetailList(Map<Product, Integer> products, long orderId) {
        List<OrderDetail> orderDetails = new ArrayList<>();
        OrderDetail orderDetail;
        for (Map.Entry<Product, Integer> entry : products.entrySet()
        ) {
            orderDetail = new OrderDetail(orderId, entry.getKey(), entry.getValue());
            orderDetails.add(orderDetail);
        }
        return orderDetails;
    }

    private Order buildOrder(Account account, String noteParam, String address,
                             String apartment, DeliveryType deliveryType,
                             LocalDateTime deliveryDate, BigDecimal price,
                             PaymentType paymentType, OrderStatus orderStatus, int bonusToPay) {
        Order order = new Order();
        order.setAccount(account);
        order.setPaymentType(paymentType);
        order.setCreationDate(LocalDateTime.now());
        order.setDeliveryDate(deliveryDate);
        order.setPrice(price);
        order.setNote(noteParam);
        order.setOrderStatus(orderStatus);
        order.setBonusToPay(bonusToPay);
        if (deliveryType.equals(DeliveryType.DELIVERY)) {
            order.setAddress(address);
            order.setApartment(apartment);
        }


        return order;
    }
}

package by.gruca.cafe.service.impl;

import by.gruca.cafe.dao.exception.DAOException;
import by.gruca.cafe.factory.DAOFactory;
import by.gruca.cafe.model.Account;
import by.gruca.cafe.model.Address;
import by.gruca.cafe.model.Order;
import by.gruca.cafe.model.Product;
import by.gruca.cafe.service.OrderService;
import by.gruca.cafe.service.exception.ServiceException;
import by.gruca.cafe.util.Validator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

public class OrderServiceImpl implements OrderService {
    public static final double BONUS_CONVERTER = 0.01;
    public static final int GUEST_START_BONUS = 0;
    Logger logger = LogManager.getLogger(OrderServiceImpl.class);

    @Override
    public List<Order> getOrdersByAccount(Account account) throws ServiceException {
        List<Order> orders;
        try {
            orders = DAOFactory.INSTANCE.getOrderDAO().getAllByAccount(account.getEmail());
        } catch (DAOException e) {
            logger.error(e);
            throw new ServiceException(e);
        }
        return orders;
    }

    @Override
    public Order createOrder(Order order, HashMap<Product, Integer> products) throws ServiceException {
        try {
            int orderId = DAOFactory.INSTANCE.getOrderDAO().create(order);
            DAOFactory.INSTANCE.getOrderDAO().attachProductsToOrder(products, orderId);
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

    @Override
    public List<Order> getAllOrders() throws ServiceException {
        List<Order> orders;
        try {
            orders = DAOFactory.INSTANCE.getOrderDAO().getAll();
        } catch (DAOException e) {
            logger.error(e);
            throw new ServiceException(e);
        }
        return orders;
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
    public void createGuestOrder(ArrayList<Product> products, String emailParam, String reviewParam, String street, String apartment,
                                 String building, String deliveryType, String deliveryDate) throws ServiceException {
        if (products != null && products.size() > 0 && Validator.validateEmail(emailParam) && Validator.validateReview(reviewParam)) {
            try {
                Order order = buildOrder(emailParam, reviewParam, street, apartment, building, deliveryType, deliveryDate);
                order.setPrice(calculateOrderPrice(products, GUEST_START_BONUS));
                int orderId = DAOFactory.INSTANCE.getOrderDAO().create(order); //creating row in order table
                DAOFactory.INSTANCE.getOrderDAO().attachProductsToOrder(groupOrderProducts(products), orderId); //creating rows in order_detail table
            } catch (DAOException e) {
                logger.error(e);
                throw new ServiceException(e);
            }
        } else throw new ServiceException("Invalid order input");
    }

    @Override
    public void createOrder(ArrayList<Product> products, String emailParam, String reviewParam, String bonusToPay, String paymentType,
                            String street, String apartment, String building, String deliveryType, String deliveryDate) throws ServiceException {

        if (products != null && products.size() > 0 && Validator.validateEmail(emailParam) && Validator.validateReview(reviewParam)) {
            try {
                Order order = buildOrder(emailParam, reviewParam, street, apartment, building, deliveryType, deliveryDate);
                order.setPrice(calculateOrderPrice(products, Integer.parseInt(bonusToPay)));

                int orderId = DAOFactory.INSTANCE.getOrderDAO().create(order); //creating row in order table
                DAOFactory.INSTANCE.getOrderDAO().attachProductsToOrder(groupOrderProducts(products), orderId); //creating rows in order_detail table

                int accountBonus = DAOFactory.INSTANCE.getAccountDAO().getAccountBonus(emailParam);
                DAOFactory.INSTANCE.getAccountDAO().setAccountBonus(emailParam, accountBonus - Integer.parseInt(bonusToPay));
                if(paymentType.equals("client_balance")){
DAOFactory.INSTANCE.getOrderDAO().setAccepted(orderId);
//DAOFactory.INSTANCE.getAccountDAO().setAccountBalance();
                }

            } catch (DAOException e) {
                logger.error(e);
                throw new ServiceException(e);
            }
        } else throw new ServiceException("Invalid order input");
    }

    private Order buildOrder(String emailParam, String reviewParam, String street, String apartment, String building, String deliveryType, String deliveryDate) throws DAOException {
        Order order = new Order();
        Account account = DAOFactory.INSTANCE.getAccountDAO().read(emailParam).orElseThrow(() -> new DAOException("Account not found"));///!!
        order.setAccount(account);
        Address address = Address.DEFAULT;
        if (deliveryType.equals("delivery")) {
            address = new Address(street, building, apartment);
        }
        order.setAddress(address);
        order.setDeliveryDate(LocalDateTime.parse(deliveryDate));
        order.setDate(LocalDateTime.now());
        order.setReview(reviewParam);
        return order;
    }

    private double calculateOrderPrice(ArrayList<Product> products, int bonusToPay) {
        double totalPrice = 0.0;
        for (Product product : products) {
            totalPrice += product.getPrice();
        }
        return totalPrice - (bonusToPay * BONUS_CONVERTER);
    }

    private HashMap<Product, Integer> groupOrderProducts(ArrayList<Product> products) {
        HashMap<Product, Integer> result = new HashMap<Product, Integer>();
        Integer productCount = 0;
        for (Product product : products) {
            productCount = result.get(product);
            result.put(product, productCount == null ? 1 : productCount + 1);
        }

        return result;
    }
}

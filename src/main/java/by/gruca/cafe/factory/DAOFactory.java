package by.gruca.cafe.factory;

import by.gruca.cafe.dao.*;
import by.gruca.cafe.dao.impl.*;

public enum DAOFactory {
    INSTANCE;
    private final OrderDAO orderDAO;
    private final AccountDAO accountDAO;
    private final ProductDAO productDAO;

    private final OrderDetailDAO orderDetailDAO;
    private final OrderStatusDAO orderStatusDAO;
    private final CategoryDAO categoryDAO;


    DAOFactory() {
        accountDAO = new AccountDAOImpl();
        productDAO = new ProductDAOImpl();
        orderDAO = new OrderDAOImpl();

        orderDetailDAO = new OrderDetailDAOImpl();
        orderStatusDAO = new OrderStatusDAOImpl();
        categoryDAO = new CategoryDAOImpl();
    }



    public OrderDetailDAO getOrderDetailDAO() {
        return orderDetailDAO;
    }

    public OrderStatusDAO getOrderStatusDAO() {
        return orderStatusDAO;
    }

    public CategoryDAO getCategoryDAO() {
        return categoryDAO;
    }

    public OrderDAO getOrderDAO() {
        return orderDAO;
    }

    public AccountDAO getAccountDAO() {
        return accountDAO;
    }

    public ProductDAO getProductDAO() {
        return productDAO;
    }
}

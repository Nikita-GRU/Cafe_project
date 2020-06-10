package by.gruca.cafe.factory;

import by.gruca.cafe.dao.AccountDAO;
import by.gruca.cafe.dao.OrderDAO;
import by.gruca.cafe.dao.ProductDAO;
import by.gruca.cafe.dao.impl.AccountDAOImpl;
import by.gruca.cafe.dao.impl.OrderDAOImpl;
import by.gruca.cafe.dao.impl.ProductDAOImpl;

public enum DAOFactory {
    INSTANCE;
    private OrderDAO orderDAO;
    private AccountDAO accountDAO;
    private ProductDAO productDAO;

    private DAOFactory() {
        orderDAO = new OrderDAOImpl();
        accountDAO = new AccountDAOImpl();
        productDAO = new ProductDAOImpl();
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

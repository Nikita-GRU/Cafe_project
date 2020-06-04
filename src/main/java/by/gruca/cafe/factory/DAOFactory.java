package by.gruca.cafe.factory;

import by.gruca.cafe.dao.AccountDAO;
import by.gruca.cafe.dao.OrderDAO;
import by.gruca.cafe.dao.impl.AccountDAOImpl;
import by.gruca.cafe.dao.impl.OrderDAOImpl;

public enum DAOFactory {
    INSTANCE;
    private OrderDAO orderDAO;
    private AccountDAO accountDAO;

    private DAOFactory() {
        orderDAO = new OrderDAOImpl();
        accountDAO = new AccountDAOImpl();
    }

    public OrderDAO getOrderDAO() {
        return orderDAO;
    }

    public AccountDAO getAccountDAO() {
        return accountDAO;
    }
}

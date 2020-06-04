package by.gruca.cafe;

import by.gruca.cafe.dao.AccountDAO;
import by.gruca.cafe.dao.OrderDAO;
import by.gruca.cafe.dao.exception.DAOException;
import by.gruca.cafe.factory.DAOFactory;
import by.gruca.cafe.model.Order;

public class Main {
    public static void main(String[] args) {
        System.out.println("Hi!");



        AccountDAO accountDAO = DAOFactory.INSTANCE.getAccountDAO();

        OrderDAO orderDAO = DAOFactory.INSTANCE.getOrderDAO();
        Order order = new Order();
        order.setTotalPrice(300);
        order.setReview("best");
        try {
            order.setAccount(accountDAO.read("mikitka").get());
            orderDAO.create(order);
        } catch (DAOException e) {
            e.printStackTrace();
        }


    }
}

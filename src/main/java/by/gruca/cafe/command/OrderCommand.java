package by.gruca.cafe.command;

import by.gruca.cafe.configuration.UrlManager;
import by.gruca.cafe.factory.ServiceFactory;
import by.gruca.cafe.model.Account;
import by.gruca.cafe.model.Order;
import by.gruca.cafe.model.Product;
import by.gruca.cafe.model.Role;
import by.gruca.cafe.service.exception.ServiceException;
import by.gruca.cafe.util.HashGeneratorUtil;
import by.gruca.cafe.util.UtilException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;

public class OrderCommand implements ActionCommand {
    Logger logger = LogManager.getLogger(OrderCommand.class);

    @Override
    public String execute(HttpServletRequest req) {
        Order order = new Order();
        HashGeneratorUtil hashGeneratorUtil = new HashGeneratorUtil();
        String payment = req.getParameter("payment");
        String email = req.getParameter("email");
        String firstName = req.getParameter("firstname");
        String phoneNumber = req.getParameter("phonenumber");
        String review = req.getParameter("review");
        Account account = (Account) req.getSession().getAttribute("account");
        ArrayList<Product> products = (ArrayList<Product>) req.getSession().getAttribute("cart");
        req.setAttribute("payment", payment);
        req.setAttribute("email", email);
        req.setAttribute("firstname", firstName);
        req.setAttribute("phonenumber", phoneNumber);
        req.setAttribute("map", groupOrderProducts(products));
        if (account == null) {
            try {
                account = ServiceFactory.INSTANCE.getAccountService().getGuestAccountByEmail(email);
            } catch (ServiceException e) {
                logger.error(e);
            }
            if (account == null) {
                account = new Account();
                account.setRole(Role.GUEST);
                account.setEmail(email);
                try {
                    account.setPassword(hashGeneratorUtil.generateHash(String.valueOf((Math.random() * 2048))));
                } catch (UtilException e) {
                    logger.error(e);
                }
                account.setPhoneNumber(Long.parseLong(phoneNumber));
                account.setFirstName(firstName);
                try {
                    ServiceFactory.INSTANCE.getAccountService().createAccount(account);
                    account = ServiceFactory.INSTANCE.getAccountService().getGuestAccountByEmail(email);
                } catch (ServiceException e) {
                    logger.error(e);
                }
            }
        }
        order.setAccount(account);
        order.setPrice(calculateOrderPrice(products));
        order.setProducts(groupOrderProducts(products));
        order.setDate(LocalDateTime.now());
        order.setReview(review);
        try {
            ServiceFactory.INSTANCE.getOrderService().createOrder(order, order.getProducts());
        } catch (ServiceException e) {
            logger.error(e);
        }
        req.getSession().removeAttribute("cart");
        return UrlManager.getProperty("path.page.ordersuccess");
    }

    private double calculateOrderPrice(ArrayList<Product> products) {
        double totalPrice = 0.0;
        for (Product product : products) {
            totalPrice += product.getPrice();
        }
        return totalPrice;
    }

    public HashMap<Product, Integer> groupOrderProducts(ArrayList<Product> products) {
        HashMap<Product, Integer> result = new HashMap<Product, Integer>();
        Integer productCount = 0;
        for (Product product : products) {
            productCount = result.get(product);
            result.put(product, productCount == null ? 1 : productCount + 1);
        }

        return result;
    }
}

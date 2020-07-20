package by.gruca.cafe.controller.command.common;

import by.gruca.cafe.configuration.MessageManager;
import by.gruca.cafe.configuration.UrlManager;
import by.gruca.cafe.controller.command.ActionCommand;
import by.gruca.cafe.factory.ServiceFactory;
import by.gruca.cafe.model.Account;
import by.gruca.cafe.model.Product;
import by.gruca.cafe.service.exception.ServiceException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;

public class OrderPaymentCommand implements ActionCommand {
    Logger logger = LogManager.getLogger(OrderPaymentCommand.class);
    @Override
    public String execute(HttpServletRequest req) {
        String page;
        String deliveryType = req.getAttribute("delivery_option").toString();
        String paymentType = req.getAttribute("payment").toString();

        String street = req.getAttribute("street").toString();
        String building = req.getAttribute("building").toString();
        String apartment = req.getAttribute("apartment").toString();
        String bonusToPay = req.getAttribute("bonus_to_pay").toString();
        String deliveryDate = req.getAttribute("delivery_date").toString();

        String emailParam = req.getAttribute("email").toString();
        String reviewParam = req.getAttribute("review").toString();
        ArrayList<Product> products = (ArrayList<Product>) req.getSession().getAttribute("cart");


        try {
            ServiceFactory.INSTANCE.getOrderService().createOrder(products, emailParam, reviewParam,
                    bonusToPay, paymentType, street, apartment, building, deliveryType, deliveryDate);
            req.getSession().removeAttribute("cart");
            req.getSession().setAttribute("cart_count", 0);
            page = UrlManager.getProperty("path.page.ordersuccess");
        } catch (ServiceException e) {
            logger.error(e);
            req.setAttribute("input_error", MessageManager.getProperty("message.errorInputMessage"));
            page = UrlManager.getProperty("path.page.order");

        }

        return page;
    }
}

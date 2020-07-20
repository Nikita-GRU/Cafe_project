package by.gruca.cafe.controller.command.common;

import by.gruca.cafe.configuration.MessageManager;
import by.gruca.cafe.configuration.UrlManager;
import by.gruca.cafe.controller.UrlsEnum;
import by.gruca.cafe.controller.command.ActionCommand;
import by.gruca.cafe.factory.ServiceFactory;
import by.gruca.cafe.model.Account;
import by.gruca.cafe.model.Product;
import by.gruca.cafe.service.exception.ServiceException;
import com.google.api.client.http.HttpMethods;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;

public class OrderCommand implements ActionCommand {
    Logger logger = LogManager.getLogger(OrderCommand.class);

    @Override
    public String execute(HttpServletRequest req) {
        String page;
        String deliveryType = req.getParameter("delivery_option");
        String paymentType = req.getParameter("payment");

        String street = req.getParameter("street");
        String building = req.getParameter("building");
        String apartment = req.getParameter("apartment");
        String bonusToPay = req.getParameter("bonus_to_pay");
        String deliveryDate = req.getParameter("delivery_date");

        String emailParam = req.getParameter("email");
        String firstNameParam = req.getParameter("firstname");
        String phoneNumberParam = req.getParameter("phonenumber").replaceAll("\\+", "");
        String reviewParam = req.getParameter("review");

        Account account = (Account) req.getSession().getAttribute("account");
        ArrayList<Product> products = (ArrayList<Product>) req.getSession().getAttribute("cart");

        try {
            if (account == null) { // it could be guest(not logged in) account but I want to save orders that was bind to some email
                account = ServiceFactory.INSTANCE.getAccountService().getAccountByEmail(emailParam);//so we find
                //if we didn't find..
                // creating guest account by email with random pass( so when customer  sign up with  this
                // email, he would see all of his *guest status* orders )
                if (account.getEmail() == null) {
                    ServiceFactory.INSTANCE.getAccountService().createGuestAccount(emailParam, phoneNumberParam, firstNameParam);
                }
                ServiceFactory.INSTANCE.getOrderService().createGuestOrder(products, emailParam, reviewParam, street, apartment, building,
                        deliveryType, deliveryDate);
            } else {
                ServiceFactory.INSTANCE.getOrderService().createOrder(products, emailParam, reviewParam,
                        bonusToPay, paymentType, street, apartment, building, deliveryType, deliveryDate);
            }

            req.getSession().removeAttribute("cart");
            req.getSession().setAttribute("cart_count", 0);


            if (req.getMethod().equals(HttpMethods.POST)) {
                page = req.getContextPath() + UrlsEnum._ORDERSUCCESS.getUrl();
            } else {
                page = UrlManager.getProperty("path.page.order_success");
            }

        } catch (ServiceException e) {
            logger.error(e);
            req.getSession().setAttribute("errorMessage",
                    MessageManager.getProperty("message.errorInputMessage"));
            page = req.getContextPath() + UrlsEnum._ORDER.getUrl();

        }

        return page;
    }

}

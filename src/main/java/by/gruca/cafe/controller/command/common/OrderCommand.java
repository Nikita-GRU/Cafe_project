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
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

public class OrderCommand implements ActionCommand {
    Logger logger = LogManager.getLogger(OrderCommand.class);

    @Override
    public String execute(HttpServletRequest req) {
        String page;
        String deliveryTypeParam = req.getParameter("delivery_option");
        String paymentTypeParam = req.getParameter("payment");
        String addressParam = req.getParameter("address");
        String apartmentParam = req.getParameter("apartment");
        String deliveryDateParam = req.getParameter("delivery_date");
        String emailParam = req.getParameter("email");
        String firstNameParam = req.getParameter("first_name");
        String phoneNumberParam = req.getParameter("phone_number");
        String noteParam = req.getParameter("review");


        Account account = (Account) req.getSession().getAttribute("account");
        Map<Product, Integer> products = (HashMap<Product, Integer>) req.getSession().getAttribute("cart");
        BigDecimal price = BigDecimal.valueOf(Double.parseDouble(req.getParameter("price")));


        try {
            if (account == null) { // it could be guest(not logged in) account but I want to save orders that was bind to some email
                account = ServiceFactory.INSTANCE.getAccountService().getAccountByEmail(emailParam);//so we find
                //if we didn't find..
                // creating guest account by email with random pass( so when customer  sign up with  this
                // email, he would see all of his *guest status* orders )
                if (account.getEmail() == null) {
                    ServiceFactory.INSTANCE.getAccountService()
                            .createGuestAccount(emailParam, phoneNumberParam, firstNameParam);
                    account = ServiceFactory.INSTANCE.getAccountService().getAccountByEmail(emailParam);
                }
                ServiceFactory.INSTANCE.getOrderService().createGuestOrder(products, account,
                        noteParam, addressParam, apartmentParam, deliveryTypeParam, deliveryDateParam, price);
            } else {
                String bonusToPay = req.getParameter("bonus_to_pay");
                ServiceFactory.INSTANCE.getOrderService().createOrder(products, account, noteParam, addressParam, apartmentParam,
                        deliveryTypeParam, deliveryDateParam, price, paymentTypeParam, bonusToPay);
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
                    MessageManager.getProperty("message.error_input_message"));
            page = UrlManager.getProperty("path.page.order");

        }

        return page;
    }

}

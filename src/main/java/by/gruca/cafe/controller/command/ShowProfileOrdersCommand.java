package by.gruca.cafe.controller.command;

import by.gruca.cafe.configuration.MessageManager;
import by.gruca.cafe.configuration.UrlManager;
import by.gruca.cafe.controller.UrlsEnum;
import by.gruca.cafe.factory.ServiceFactory;
import by.gruca.cafe.model.Account;
import by.gruca.cafe.model.Order;
import by.gruca.cafe.service.exception.ServiceException;
import com.google.api.client.http.HttpMethods;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public class ShowProfileOrdersCommand implements ActionCommand {
    Logger logger = LogManager.getLogger(ShowProfileOrdersCommand.class);

    @Override
    public String execute(HttpServletRequest req) {
        String page;
        Account account = (Account) req.getSession().getAttribute("account");
        try {
            List<Order> orders = ServiceFactory.INSTANCE.getOrderService().getOrdersByAccount(account);
            req.setAttribute("orders", orders);
            if (req.getMethod().equals(HttpMethods.POST)) {
                page = req.getContextPath() + UrlsEnum._PROFILE_ORDERS.getUrl();
            } else {
                page = UrlManager.getProperty("path.page.profile");
            }
        } catch (ServiceException e) {
            logger.error(e);
            req.getSession().setAttribute("errorMessage",
                    MessageManager.getProperty("message.common_error_message"));
            page = req.getContextPath() + UrlsEnum._PROFILE_ORDERS.getUrl();

        }

        return page;
    }
}

package by.gruca.cafe.controller.command.admin;

import by.gruca.cafe.configuration.MessageManager;
import by.gruca.cafe.configuration.UrlManager;
import by.gruca.cafe.controller.UrlsEnum;
import by.gruca.cafe.controller.command.ActionCommand;
import by.gruca.cafe.factory.ServiceFactory;
import by.gruca.cafe.model.Order;
import by.gruca.cafe.service.exception.ServiceException;
import com.google.api.client.http.HttpMethods;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

public class ShowOrdersCommand implements ActionCommand {
    private Logger logger = LogManager.getLogger(ShowOrdersCommand.class);

    @Override
    public String execute(HttpServletRequest req) {
        String page;

        List<Order> orders = new ArrayList<Order>();
        try {
            orders = ServiceFactory.INSTANCE.getOrderService().getAllOrders();
            if (req.getMethod().equals(HttpMethods.POST)) {
                page = req.getContextPath() + UrlsEnum._ADMIN_ORDERS.getUrl();
            } else {
                page = UrlManager.getProperty("path.page.admin");
            }
        } catch (ServiceException e) {
            logger.error(e);
            req.getSession().setAttribute("errorMessage",
                    MessageManager.getProperty("message.common_error_message"));
            page = req.getContextPath() + UrlsEnum._ADMIN_ORDERS.getUrl();
        }

        req.setAttribute("orders", orders);
        return page;
    }

}

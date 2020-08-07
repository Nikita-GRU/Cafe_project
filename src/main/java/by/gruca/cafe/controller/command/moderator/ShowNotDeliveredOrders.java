package by.gruca.cafe.controller.command.moderator;

import by.gruca.cafe.configuration.MessageManager;
import by.gruca.cafe.configuration.UrlManager;
import by.gruca.cafe.controller.UrlsEnum;
import by.gruca.cafe.controller.command.ActionCommand;
import by.gruca.cafe.factory.ServiceFactory;
import by.gruca.cafe.model.Category;
import by.gruca.cafe.model.Order;
import by.gruca.cafe.model.OrderStatus;
import by.gruca.cafe.service.exception.ServiceException;
import com.google.api.client.http.HttpMethods;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

public class ShowNotDeliveredOrders implements ActionCommand {
    private Logger logger = LogManager.getLogger(ShowNotDeliveredOrders.class);

    @Override
    public String execute(HttpServletRequest req) {
        String page;
        List<OrderStatus> orderStatuses = List.of(OrderStatus.values());
        req.setAttribute("order_statuses", orderStatuses);
        List<Order> orders = new ArrayList<>();
        try {
            orders = ServiceFactory.INSTANCE.getOrderService().getNotDeliveredOrders();
            if (req.getMethod().equals(HttpMethods.POST)) {
                page = req.getContextPath() + UrlsEnum._MODERATOR.getUrl();
            } else {
                page = UrlManager.getProperty("path.page.moderator");
            }
        } catch (ServiceException e) {
            logger.error(e);
            req.getSession().setAttribute("errorMessage",
                    MessageManager.getProperty("message.common_error_message"));
            page = UrlManager.getProperty("path.page.moderator");
        }
        req.setAttribute("orders", orders);
        return page;


    }
}


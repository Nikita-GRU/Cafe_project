package by.gruca.cafe.controller.command.moderator;

import by.gruca.cafe.configuration.MessageManager;
import by.gruca.cafe.configuration.UrlManager;
import by.gruca.cafe.controller.UrlsEnum;
import by.gruca.cafe.controller.command.ActionCommand;
import by.gruca.cafe.factory.ServiceFactory;
import by.gruca.cafe.service.exception.ServiceException;
import com.google.api.client.http.HttpMethods;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;

public class OrderSetAcceptedCommand implements ActionCommand {
    private Logger logger = LogManager.getLogger(OrderSetAcceptedCommand.class);

    @Override
    public String execute(HttpServletRequest req) {
        int orderId = Integer.parseInt(req.getParameter("order_id_to_set_accepted"));
        String page;
        try {
            ServiceFactory.INSTANCE.getOrderService().setOrderAccepted(orderId);
            if (req.getMethod().equals(HttpMethods.POST)) {
                page = req.getContextPath() + UrlsEnum._MODERATOR_ORDERS.getUrl();
            } else {
                page = UrlManager.getProperty("path.action.moderator_show_orders");
            }
        } catch (ServiceException e) {
            logger.error(e);
            req.getSession().setAttribute("errorMessage",
                    MessageManager.getProperty("message.common_error_message"));
            page = req.getContextPath() + UrlsEnum._MODERATOR_ORDERS.getUrl();
        }
        return page;
    }
}

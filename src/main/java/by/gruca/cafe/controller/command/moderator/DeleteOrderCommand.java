package by.gruca.cafe.controller.command.moderator;

import by.gruca.cafe.configuration.MessageManager;
import by.gruca.cafe.controller.UrlsEnum;
import by.gruca.cafe.controller.command.ActionCommand;
import by.gruca.cafe.configuration.UrlManager;
import by.gruca.cafe.factory.ServiceFactory;
import by.gruca.cafe.service.exception.ServiceException;
import com.google.api.client.http.HttpMethods;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;

public class DeleteOrderCommand implements ActionCommand {
    private Logger logger = LogManager.getLogger(DeleteOrderCommand.class);

    @Override
    public String execute(HttpServletRequest req) {
        String page;
        String orderIdToDeleteParam = req.getParameter("order_id_to_delete");
        int orderId = Integer.parseInt(orderIdToDeleteParam);
        try {
            ServiceFactory.INSTANCE.getOrderService().deleteOrder(orderId);
            if (req.getMethod().equals(HttpMethods.POST)) {
                page = req.getContextPath() + UrlsEnum._MODERATOR_ORDERS.getUrl();
            } else {
                page = UrlManager.getProperty("path.page.moderator");
            }
        } catch (ServiceException e) {
            logger.error(e);
            req.getSession().setAttribute("errorMessage",
                    MessageManager.getProperty("message.common_error_message"));
            page = UrlManager.getProperty("path.page.moderator");
        }
        return page;
    }
}

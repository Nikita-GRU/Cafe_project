package by.gruca.cafe.command.moderator;

import by.gruca.cafe.command.ActionCommand;
import by.gruca.cafe.configuration.UrlManager;
import by.gruca.cafe.factory.ServiceFactory;
import by.gruca.cafe.service.exception.ServiceException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;

public class OrderSetDeliveredCommand implements ActionCommand {
    private Logger logger = LogManager.getLogger(OrderSetDeliveredCommand.class);

    @Override
    public String execute(HttpServletRequest req) {
        int orderId = Integer.parseInt(req.getParameter("order_id_to_set_delivered"));
        try {
            ServiceFactory.INSTANCE.getOrderService().setOrderDelivered(orderId);
        } catch (ServiceException e) {
            logger.error(e);
        }
        return UrlManager.getProperty("path.action.moderation");
    }
}

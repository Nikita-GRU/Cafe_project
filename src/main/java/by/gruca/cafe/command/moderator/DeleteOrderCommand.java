package by.gruca.cafe.command.moderator;

import by.gruca.cafe.command.ActionCommand;
import by.gruca.cafe.configuration.UrlManager;
import by.gruca.cafe.factory.ServiceFactory;
import by.gruca.cafe.service.exception.ServiceException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;

public class DeleteOrderCommand implements ActionCommand {
    private Logger logger = LogManager.getLogger(DeleteOrderCommand.class);

    @Override
    public String execute(HttpServletRequest req) {
        String orderIdParameter = req.getParameter("order_id_to_delete");
        int orderId = Integer.parseInt(orderIdParameter);
        try {
            ServiceFactory.INSTANCE.getOrderService().deleteOrder(orderId);
        } catch (ServiceException e) {
            logger.error(e);
        }
        return UrlManager.getProperty("path.action.moderation");
    }
}

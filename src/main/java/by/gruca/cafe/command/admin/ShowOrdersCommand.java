package by.gruca.cafe.command.admin;

import by.gruca.cafe.command.ActionCommand;
import by.gruca.cafe.configuration.UrlManager;
import by.gruca.cafe.factory.ServiceFactory;
import by.gruca.cafe.model.Order;
import by.gruca.cafe.service.exception.ServiceException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

public class ShowOrdersCommand implements ActionCommand {
    private Logger logger = LogManager.getLogger(ShowOrdersCommand.class);

    @Override
    public String execute(HttpServletRequest req) {


        List<Order> orders = new ArrayList<Order>();
        try {
            orders = ServiceFactory.INSTANCE.getOrderService().getAllOrders();
        } catch (ServiceException e) {
            logger.error(e);
        }

        req.setAttribute("orders", orders);
        return UrlManager.getProperty("path.page.admin");
    }

}

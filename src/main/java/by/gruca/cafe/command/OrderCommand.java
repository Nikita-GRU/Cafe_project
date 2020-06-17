package by.gruca.cafe.command;

import by.gruca.cafe.configuration.UrlManager;
import by.gruca.cafe.model.Order;

import javax.servlet.http.HttpServletRequest;

public class OrderCommand implements ActionCommand {
    @Override
    public String execute(HttpServletRequest req) {
        Order order = new Order();

        return UrlManager.getProperty("path.page.order");
    }
}

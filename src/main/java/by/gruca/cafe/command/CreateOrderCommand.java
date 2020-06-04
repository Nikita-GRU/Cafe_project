package by.gruca.cafe.command;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;

public class CreateOrderCommand implements ActionCommand {
    @Override
    public String execute(HttpServletRequest req) {
        String page = null;
        String orderDate = LocalDateTime.now().toString();
        Double price = Double.parseDouble(req.getParameter("price"));
        String login = req.getParameter("login");
        return null;
    }
}

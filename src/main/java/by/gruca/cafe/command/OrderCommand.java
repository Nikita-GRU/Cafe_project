package by.gruca.cafe.command;

import by.gruca.cafe.configuration.UrlManager;
import by.gruca.cafe.model.Order;

import javax.servlet.http.HttpServletRequest;

public class OrderCommand implements ActionCommand {
    @Override
    public String execute(HttpServletRequest req) {
        Order order = new Order();
        String payment = req.getParameter("payment");
        String email = req.getParameter("email");
        String firstName = req.getParameter("firstname");
        String lastName = req.getParameter("lastname");
        String phoneNumber = req.getParameter("phonenumber");

        req.setAttribute("payment", payment);
        req.setAttribute("email", email);
        req.setAttribute("firstname", firstName);
        req.setAttribute("lastname", lastName);
        req.setAttribute("phonenumber", phoneNumber);


        return UrlManager.getProperty("path.page.ordersuccess");
    }
}

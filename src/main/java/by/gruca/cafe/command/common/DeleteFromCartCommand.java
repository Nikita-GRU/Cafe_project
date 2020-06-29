package by.gruca.cafe.command.common;

import by.gruca.cafe.command.ActionCommand;
import by.gruca.cafe.configuration.UrlManager;
import by.gruca.cafe.model.Product;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;

public class DeleteFromCartCommand implements ActionCommand {
    @Override
    public String execute(HttpServletRequest req) {
        ArrayList<Product> cart = (ArrayList<Product>) req.getSession().getAttribute("cart");
        cart.remove(cart.stream()
                .filter(p -> p.getName().equals(req.getParameter("product_to_delete")))
                .findAny().get());
               return UrlManager.getProperty("path.page.cart");
    }
}

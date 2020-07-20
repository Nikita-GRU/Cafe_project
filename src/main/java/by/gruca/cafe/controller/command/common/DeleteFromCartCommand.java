package by.gruca.cafe.controller.command.common;

import by.gruca.cafe.configuration.UrlManager;
import by.gruca.cafe.controller.UrlsEnum;
import by.gruca.cafe.controller.command.ActionCommand;
import by.gruca.cafe.model.Product;
import com.google.api.client.http.HttpMethods;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;

public class DeleteFromCartCommand implements ActionCommand {
    @Override
    public String execute(HttpServletRequest req) {
        String page;
        ArrayList<Product> cart = (ArrayList<Product>) req.getSession().getAttribute("cart");
        cart.remove(cart.stream()
                .filter(p -> p.getName().equals(req.getParameter("product_to_delete")))
                .findAny().get());
        req.getSession().setAttribute("cart_count", cart.size());

        if (req.getMethod().equals(HttpMethods.POST)) {
            page = req.getContextPath() + UrlsEnum._CART.getUrl();
        } else {
            page = UrlManager.getProperty("path.page.cart");
        }
        return page;
    }
}

package by.gruca.cafe.controller.command.common;

import by.gruca.cafe.configuration.UrlManager;
import by.gruca.cafe.controller.UrlsEnum;
import by.gruca.cafe.controller.command.ActionCommand;
import by.gruca.cafe.model.Product;
import com.google.api.client.http.HttpMethods;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Map;

public class DeleteFromCartCommand implements ActionCommand {
    @Override
    public String execute(HttpServletRequest req) {
        String page;
        String productToDeleteParam = req.getParameter("product_to_delete");

        Map<Product, Integer> productsInCart = (Map<Product, Integer>) req.getSession().getAttribute("cart");

        productsInCart.remove(productsInCart.keySet().stream()
                .filter(product -> product.getName().equals(productToDeleteParam))
                .findAny()
                .get());

        req.getSession().setAttribute("cart_count",
                productsInCart.values().stream()
                .mapToInt(Integer::intValue)
                .sum());

        if (req.getMethod().equals(HttpMethods.POST)) {
            page = req.getContextPath() + UrlsEnum._CART.getUrl();
        } else {
            page = UrlManager.getProperty("path.page.cart");
        }
        return page;
    }
}

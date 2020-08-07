package by.gruca.cafe.controller.command.common;

import by.gruca.cafe.configuration.UrlManager;
import by.gruca.cafe.controller.UrlsEnum;
import by.gruca.cafe.controller.command.ActionCommand;
import by.gruca.cafe.model.Product;
import com.google.api.client.http.HttpMethods;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

public class ToOrderCommand implements ActionCommand {
    @Override
    public String execute(HttpServletRequest req) {
        String page;
        HashMap<Product, Integer> productsInCart = (HashMap<Product, Integer>) req.getSession().getAttribute("cart");
        BigDecimal price = BigDecimal.valueOf(0);
        for (Map.Entry<Product, Integer> entry : productsInCart.entrySet()
        ) {
            price = price.add(entry.getKey().getPrice().multiply(BigDecimal.valueOf(entry.getValue())));
        }
        req.setAttribute("price", price);
        if (req.getMethod().equals(HttpMethods.POST)) {
            page = req.getContextPath() + UrlsEnum._ORDER.getUrl();
        } else {
            page = UrlManager.getProperty("path.page.order");
        }
        return page;
    }
}

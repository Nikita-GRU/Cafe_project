package by.gruca.cafe.controller.command.common;

import by.gruca.cafe.configuration.MessageManager;
import by.gruca.cafe.configuration.UrlManager;
import by.gruca.cafe.controller.UrlsEnum;
import by.gruca.cafe.controller.command.ActionCommand;
import by.gruca.cafe.factory.ServiceFactory;
import by.gruca.cafe.model.Product;
import by.gruca.cafe.service.exception.ServiceException;
import com.google.api.client.http.HttpMethods;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ToCartCommand implements ActionCommand {
    Logger logger = LogManager.getLogger(ToCartCommand.class);

    @Override
    public String execute(HttpServletRequest req) {
        String page;
        String checkedProductNameParam = req.getParameter("checked_product");
        Map<Product, Integer> productsInCart = (Map<Product, Integer>) req.getSession().getAttribute("cart");

        if (productsInCart == null) {
            productsInCart = new HashMap<>();
        }
        try {
            Product checkedProduct = ServiceFactory.INSTANCE.getProductService().getProductByName(checkedProductNameParam);
            if (productsInCart.containsKey(checkedProduct)) {
                int newCount = productsInCart.get(checkedProduct);
                newCount++;
                productsInCart.put(checkedProduct, newCount);
            } else {
                productsInCart.put(checkedProduct, 1);
            }
           
            req.getSession().setAttribute("cart", productsInCart);
            req.getSession().setAttribute("cart_count",
                    productsInCart.values().stream()
                            .mapToInt(Integer::intValue)
                            .sum());

            if (req.getMethod().equals(HttpMethods.POST)) {
                page = req.getParameter("redirect_uri") + "?" + req.getParameter("redirect_query");
            } else {
                page = UrlManager.getProperty("path.page.menu");
            }
        } catch (ServiceException e) {
            logger.error(e);
            req.getSession().setAttribute("errorMessage",
                    MessageManager.getProperty("message.common_error_message"));
            page = UrlManager.getProperty("path.page.menu");
        }
        return page;
    }
}
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

public class ShowMenuCommand implements ActionCommand {
    Logger logger = LogManager.getLogger(ShowMenuCommand.class);

    @Override
    public String execute(HttpServletRequest req) {
        String checkedProduct = req.getParameter("checkedproduct");
        Product product = null;
        ArrayList<Product> productsInCart = (ArrayList<Product>) req.getSession().getAttribute("cart");
        String page;
        if (productsInCart == null) {
            productsInCart = new ArrayList<>();
        }
        try {
            if (checkedProduct != null) {
                product = ServiceFactory.INSTANCE.getProductService().getProductByName(checkedProduct);
            }
            if (product != null) {
                productsInCart.add(product);
                req.getSession().setAttribute("cart_count", productsInCart.size());
            }
            req.getSession().setAttribute("cart", productsInCart);

            if (req.getMethod().equals(HttpMethods.POST)) {
                page = req.getContextPath() + UrlsEnum._MENU.getUrl();
            } else {
                page = UrlManager.getProperty("path.page.menu");
            }
        } catch (ServiceException e) {
            logger.error(e);
            req.getSession().setAttribute("errorMessage",
                    MessageManager.getProperty("message.common_error_message"));
            page = req.getContextPath() + UrlsEnum._MENU.getUrl();
        }
        return page;
    }
}

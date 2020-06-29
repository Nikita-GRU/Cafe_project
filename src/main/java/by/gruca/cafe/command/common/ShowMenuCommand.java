package by.gruca.cafe.command.common;

import by.gruca.cafe.command.ActionCommand;
import by.gruca.cafe.configuration.MessageManager;
import by.gruca.cafe.configuration.UrlManager;
import by.gruca.cafe.factory.ServiceFactory;
import by.gruca.cafe.model.Product;
import by.gruca.cafe.service.exception.ServiceException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

public class ShowMenuCommand implements ActionCommand {
    Logger logger = LogManager.getLogger(ShowMenuCommand.class);

    @Override
    public String execute(HttpServletRequest req) {
        String checkedProduct = req.getParameter("checkedproduct");
        Product product = null;
        ArrayList<Product> productsInCart = (ArrayList<Product>) req.getSession().getAttribute("cart");
        String page;
        try {
            List<Product> products = ServiceFactory.INSTANCE.getProductService().getAllProducts();
            req.setAttribute("products", products);
            try {
                product = ServiceFactory.INSTANCE.getProductService().getProductByName(checkedProduct);//!!!!!!!!

            } catch (ServiceException e) {
                logger.error(e);
            }
            logger.info("checkedproduct= " + req.getParameter("checkedproduct"));
            logger.info("command= " + req.getParameter("command"));
            if (productsInCart == null) {
                productsInCart = new ArrayList<>();
            }
            if (product != null) {
                productsInCart.add(product);
                req.getSession().setAttribute("cart_count", productsInCart.size());

            }
            req.getSession().setAttribute("cart", productsInCart);


            page = UrlManager.getProperty("path.page.menu");
        } catch (ServiceException e) {
            req.setAttribute("errorLoginPassMessage",
                    MessageManager.getProperty("message.loginerror"));
            page = UrlManager.getProperty("path.page.error");
        }

        return page;
    }
}

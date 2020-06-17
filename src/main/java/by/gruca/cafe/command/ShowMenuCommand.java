package by.gruca.cafe.command;

import by.gruca.cafe.configuration.MessageManager;
import by.gruca.cafe.configuration.UrlManager;
import by.gruca.cafe.model.Product;
import by.gruca.cafe.service.exception.ServiceException;
import by.gruca.cafe.service.impl.ProductServiceImpl;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

public class ShowMenuCommand implements ActionCommand {
    @Override
    public String execute(HttpServletRequest req) {
        String checkedProduct = req.getParameter("checkedproduct");
        Product product = null;
        ArrayList productsInCart = (ArrayList) req.getSession().getAttribute("cart");
        String page;
        ProductServiceImpl productService = new ProductServiceImpl();
        try {
            List<Product> products = productService.getProducts();
            req.setAttribute("products", products);
            try {
                product = productService.getProductByName(checkedProduct);
                // req.setAttribute("product", product);
            } catch (ServiceException e) {
                //logger.error(e);
            }
            // logger.info(req.getParameter("checkedproduct"));
            // logger.info(req.getParameter("command"));
            if (productsInCart == null) {
                productsInCart = new ArrayList<>();
            }
            productsInCart.add(product);
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

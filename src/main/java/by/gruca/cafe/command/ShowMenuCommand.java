package by.gruca.cafe.command;

import by.gruca.cafe.configuration.ConfigurationManager;
import by.gruca.cafe.configuration.MessageManager;
import by.gruca.cafe.model.Product;
import by.gruca.cafe.service.exception.ServiceException;
import by.gruca.cafe.service.impl.ProductServiceImpl;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public class ShowMenuCommand implements ActionCommand {
    @Override
    public String execute(HttpServletRequest req) {
        String page = ConfigurationManager.getProperty("path.page.main");
        ProductServiceImpl productService = new ProductServiceImpl();
        try {
            List<Product> products = productService.getProducts();
            req.setAttribute("products", products);
            page = ConfigurationManager.getProperty("path.page.menu");
        } catch (ServiceException e) {
            req.setAttribute("errorLoginPassMessage",
                    MessageManager.getProperty("message.loginerror"));
            page = ConfigurationManager.getProperty("path.page.main");
        }

        return page;
    }
}

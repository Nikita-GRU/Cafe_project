package by.gruca.cafe.command.admin;


import by.gruca.cafe.command.ActionCommand;
import by.gruca.cafe.configuration.UrlManager;
import by.gruca.cafe.factory.ServiceFactory;
import by.gruca.cafe.model.Product;
import by.gruca.cafe.service.exception.ServiceException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

public class ShowProductsCommand implements ActionCommand {
    private Logger logger = LogManager.getLogger(ShowProductsCommand.class);

    @Override
    public String execute(HttpServletRequest req) {
        String subCommand = req.getParameter("sub_command");
        Product product = new Product();
        if (subCommand != null && !subCommand.isEmpty() && subCommand.equals("update")) {
            int productId = Integer.parseInt(req.getParameter("product_id_to_update"));
            String newName = req.getParameter("new_name");
            double newPrice = Double.parseDouble(req.getParameter("new_price"));
            String newDescription = req.getParameter("new_description");
            product.setName(newName);
            product.setPrice(newPrice);
            product.setDescription(newDescription);
            try {
                ServiceFactory.INSTANCE.getProductService().updateProduct(product,productId);
            } catch (ServiceException e) {
                logger.error(e);
            }

        }

        List<Product> products = new ArrayList<Product>();
        try {
            products = ServiceFactory.INSTANCE.getProductService().getAllProducts();
        } catch (ServiceException e) {
            logger.error(e);
        }
        req.setAttribute("products", products);
        return UrlManager.getProperty("path.page.admin");
    }
}

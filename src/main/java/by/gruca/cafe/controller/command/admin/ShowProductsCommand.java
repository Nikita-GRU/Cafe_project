package by.gruca.cafe.controller.command.admin;


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
import java.util.List;

public class ShowProductsCommand implements ActionCommand {
    private Logger logger = LogManager.getLogger(ShowProductsCommand.class);

    @Override
    public String execute(HttpServletRequest req) {
        String page;
        String subCommand = req.getParameter("sub_command");
        Product product = new Product();
        if (subCommand != null && !subCommand.isEmpty() && subCommand.equals("update")) {
            int productId = Integer.parseInt(req.getParameter("product_id_to_update"));
            String newName = req.getParameter("new_name");
            double newPrice = Double.parseDouble(req.getParameter("new_price"));
            String newImageUri = req.getParameter("new_image_uri");
            int newBonus = Integer.parseInt(req.getParameter("new_bonus"));
            String newDescription = req.getParameter("new_description");
            product.setName(newName);
            product.setPrice(newPrice);
            product.setDescription(newDescription);
            product.setImageUri(newImageUri);
            product.setBonus(newBonus);
            try {
                ServiceFactory.INSTANCE.getProductService().updateProduct(product, productId);
            } catch (ServiceException e) {
                logger.error(e);
            }

        }

        List<Product> products = new ArrayList<Product>();
        try {
            products = ServiceFactory.INSTANCE.getProductService().getAllProducts();
            if (req.getMethod().equals(HttpMethods.POST)) {
                page = req.getContextPath() + UrlsEnum._ADMIN_PRODUCTS.getUrl();
            } else {
                page = UrlManager.getProperty("path.page.admin");
            }
        } catch (ServiceException e) {
            logger.error(e);
            req.getSession().setAttribute("errorMessage",
                    MessageManager.getProperty("message.common_error_message"));
            page = req.getContextPath() + UrlsEnum._ADMIN_PRODUCTS.getUrl();
        }
        req.setAttribute("products", products);
        return page;
    }
}

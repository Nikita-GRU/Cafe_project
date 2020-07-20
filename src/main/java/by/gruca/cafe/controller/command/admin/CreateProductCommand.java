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

public class CreateProductCommand implements ActionCommand {
    private Logger logger = LogManager.getLogger(CreateProductCommand.class);

    @Override
    public String execute(HttpServletRequest req) {
        String page;
        String name = req.getParameter("name");
        String priceString = req.getParameter("price");
        String description = req.getParameter("description");
        String imageUri = req.getParameter("image_uri");
        int bonus = Integer.parseInt(req.getParameter("bonus"));
        double price = Double.parseDouble(priceString);
        Product product = new Product();
        product.setName(name);
        product.setPrice(price);
        product.setDescription(description);
        product.setBonus(bonus);
        product.setImageUri(imageUri);
        try {
            ServiceFactory.INSTANCE.getProductService().addNewProduct(product);
            if (req.getMethod().equals(HttpMethods.POST)) {
                page = req.getContextPath() + UrlsEnum._ADMIN_PRODUCTS.toString().toLowerCase().replace("_", "/");
            } else {
                page = UrlManager.getProperty("path.action.show_products");
            }
        } catch (ServiceException e) {
            logger.error(e);
            req.setAttribute("errorInputMessage",
                    MessageManager.getProperty("message.errorInputMessage"));
            page = req.getContextPath() + UrlsEnum._ADMIN_PRODUCTS.toString().toLowerCase().replace("_", "/");
        }
        return page;
    }
}

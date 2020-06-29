package by.gruca.cafe.command.admin;

import by.gruca.cafe.command.ActionCommand;
import by.gruca.cafe.configuration.UrlManager;
import by.gruca.cafe.factory.ServiceFactory;
import by.gruca.cafe.model.Product;
import by.gruca.cafe.service.exception.ServiceException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;

public class CreateProductCommand implements ActionCommand {
    private Logger logger = LogManager.getLogger(CreateProductCommand.class);
    @Override
    public String execute(HttpServletRequest req) {
        String name = req.getParameter("name");
        String priceString = req.getParameter("price");
        String description = req.getParameter("description");
        double price = Double.parseDouble(priceString);
        Product product = new Product();
        product.setName(name);
        product.setPrice(price);
        product.setDescription(description);
        try {
            ServiceFactory.INSTANCE.getProductService().addNewProduct(product);
        } catch (ServiceException e) {
            logger.error(e);
        }
        return UrlManager.getProperty("path.action.show_products");
    }
}

package by.gruca.cafe.model;

import by.gruca.cafe.factory.ServiceFactory;
import by.gruca.cafe.service.exception.ServiceException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

public class ProductManager {
    private final Logger logger = LogManager.getLogger(ProductManager.class);
    private List<Product> products;

    public ProductManager() {
        try {
            products = ServiceFactory.INSTANCE.getProductService().getAllProducts();
        } catch (ServiceException e) {
            logger.error(e);
        }
    }

    public List<Product> getProducts() {
        return products;
    }

}

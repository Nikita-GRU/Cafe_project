package by.gruca.cafe.command;

import by.gruca.cafe.configuration.UrlManager;
import by.gruca.cafe.filter.UriFilter;
import by.gruca.cafe.service.ProductService;
import by.gruca.cafe.service.impl.ProductServiceImpl;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;

public class AddToCartCommand implements ActionCommand {
    Logger logger = LogManager.getLogger(UriFilter.class);

    @Override
    public String execute(HttpServletRequest req) {
        ProductService productService = new ProductServiceImpl();
        return UrlManager.getProperty("path.page.cart");
    }
}

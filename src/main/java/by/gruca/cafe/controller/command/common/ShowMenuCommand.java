package by.gruca.cafe.controller.command.common;

import by.gruca.cafe.configuration.MessageManager;
import by.gruca.cafe.configuration.UrlManager;
import by.gruca.cafe.controller.UrlsEnum;
import by.gruca.cafe.controller.command.ActionCommand;
import by.gruca.cafe.factory.ServiceFactory;
import by.gruca.cafe.model.Category;
import by.gruca.cafe.model.Product;
import by.gruca.cafe.service.exception.ServiceException;
import com.google.api.client.http.HttpMethods;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public class ShowMenuCommand implements ActionCommand {
    Logger logger = LogManager.getLogger(ShowMenuCommand.class);

    @Override
    public String execute(HttpServletRequest req) {
        String page;
        List<Category> categories = List.of(Category.values());
        req.setAttribute("categories", categories);
        String category = req.getParameter("category");
        req.removeAttribute("products");
        try {
            List<Product> productList;
            if (category == null || category.equals("all")) {
                productList = ServiceFactory.INSTANCE.getProductService().getAllProducts();
            } else {
                productList = ServiceFactory.INSTANCE.getProductService().getProductsByCategory(category);
            }
            req.setAttribute("products", productList);

            if (req.getMethod().equals(HttpMethods.POST)) {
                page = req.getContextPath() + UrlsEnum._MENU.getUrl();
            } else {
                page = UrlManager.getProperty("path.page.menu");
            }
        } catch (ServiceException e) {
            logger.error(e);
            req.getSession().setAttribute("errorMessage",
                    MessageManager.getProperty("message.common_error_message"));
            page = UrlManager.getProperty("path.page.menu");
        }
        return page;
    }
}

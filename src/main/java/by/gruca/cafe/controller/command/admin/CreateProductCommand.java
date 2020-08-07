package by.gruca.cafe.controller.command.admin;

import by.gruca.cafe.configuration.MessageManager;
import by.gruca.cafe.configuration.UrlManager;
import by.gruca.cafe.controller.UrlsEnum;
import by.gruca.cafe.controller.command.ActionCommand;
import by.gruca.cafe.factory.ServiceFactory;
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
        String nameParam = req.getParameter("name");
        String priceParam = req.getParameter("price");
        String descriptionParam = req.getParameter("description");
        String imageUriParam = req.getParameter("image_uri");
        String bonusParam = req.getParameter("bonus");
        String categoryParam = req.getParameter("category");
        try {
            ServiceFactory.INSTANCE.getProductService().addNewProduct(nameParam,priceParam,
                    descriptionParam,imageUriParam,bonusParam,categoryParam);
            if (req.getMethod().equals(HttpMethods.POST)) {
                page = req.getContextPath() + UrlsEnum._ADMIN_PRODUCTS.getUrl();
            } else {
                page = UrlManager.getProperty("path.action.show_products");
            }
        } catch (ServiceException e) {
            logger.error(e);
            req.setAttribute("errorInputMessage",
                    MessageManager.getProperty("message.error_input_message"));
            page = req.getContextPath() + UrlsEnum._ADMIN_PRODUCTS.getUrl();
        }
        return page;
    }
}

package by.gruca.cafe.controller.command.admin;


import by.gruca.cafe.configuration.MessageManager;
import by.gruca.cafe.configuration.UrlManager;
import by.gruca.cafe.controller.UrlsEnum;
import by.gruca.cafe.controller.command.ActionCommand;
import by.gruca.cafe.factory.ServiceFactory;
import by.gruca.cafe.model.Product;
import by.gruca.cafe.service.exception.ServiceException;
import by.gruca.cafe.util.Paginator;
import by.gruca.cafe.util.UtilException;
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
        String productIdToUpdate = req.getParameter("product_id_to_update");
        String newNameParam = req.getParameter("new_name");
        String newPriceParam = req.getParameter("new_price");
        String newImageUriParam = req.getParameter("new_image_uri");
        String newBonusParam = req.getParameter("new_bonus");
        String newDescriptionParam = req.getParameter("new_description");
        if (subCommand != null && !subCommand.isEmpty() && subCommand.equals("update")) {

            try {
                ServiceFactory.INSTANCE.getProductService().updateProduct(productIdToUpdate, newNameParam, newPriceParam
                        , newImageUriParam, newDescriptionParam, newBonusParam);
            } catch (ServiceException e) {
                logger.error(e);
            }

        }

        List<Product> products = new ArrayList<>();
        try {
            int productsCount = ServiceFactory.INSTANCE.getProductService().getProductsCount();
            Paginator paginator = new Paginator(req, productsCount);
            products = ServiceFactory.INSTANCE.getProductService().getPaginatedProducts(paginator.getItemsPerPage(), paginator.getPageNumber());
            if (req.getMethod().equals(HttpMethods.POST)) {
                page = req.getParameter("redirect_uri") + "?" + req.getParameter("redirect_query");
            } else {
                page = UrlManager.getProperty("path.page.admin");
            }
        } catch (ServiceException | UtilException e) {
            logger.error(e);
            req.getSession().setAttribute("errorMessage",
                    MessageManager.getProperty("message.common_error_message"));
            page = UrlManager.getProperty("path.page.admin");
        }
        req.setAttribute("products", products);
        return page;
    }
}

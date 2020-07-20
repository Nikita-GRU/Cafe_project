package by.gruca.cafe.controller.command.common;

import by.gruca.cafe.controller.command.ActionCommand;
import by.gruca.cafe.configuration.UrlManager;
import by.gruca.cafe.controller.filter.UriFilter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;

public class AddToCartCommand implements ActionCommand {
    Logger logger = LogManager.getLogger(UriFilter.class);

    @Override
    public String execute(HttpServletRequest req) {
        return UrlManager.getProperty("path.page.cart");
    }
}

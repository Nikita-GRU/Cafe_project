package by.gruca.cafe.command;

import by.gruca.cafe.configuration.UrlManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;

public class EmptyCommand implements ActionCommand {

    Logger logger = LogManager.getLogger(EmptyCommand.class);

    @Override
    public String execute(HttpServletRequest request) {
        String page = UrlManager.getProperty("path.page.main");
        logger.info(request.getMethod());
        logger.info("reqURI=" + request.getRequestURI());
        return page;
    }
}

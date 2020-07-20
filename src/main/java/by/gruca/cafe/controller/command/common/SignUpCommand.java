package by.gruca.cafe.controller.command.common;


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

public class SignUpCommand implements ActionCommand {
    private static final String PARAM_NAME_PASSWORD = "password";
    private static final String PARAM_NAME_EMAIL = "email";
    private static final String PARAM_NAME_FIRSTNAME = "firstname";
    private static final String PARAM_NAME_PHONE_NUMBER = "phonenumber";
    private static final String PARAM_NAME_PASSWORD_VERIFY = "password_verify";
    Logger logger = LogManager.getLogger(SignUpCommand.class);

    @Override
    public String execute(HttpServletRequest req) {
        String page;
        String firstNameParam = req.getParameter(PARAM_NAME_FIRSTNAME);
        String phoneNumberParam = req.getParameter(PARAM_NAME_PHONE_NUMBER).replaceAll("\\+", "");
        String passwordParam = req.getParameter(PARAM_NAME_PASSWORD);
        String emailParam = req.getParameter(PARAM_NAME_EMAIL);

        try {
            ServiceFactory.INSTANCE.getAccountService().createAccount(emailParam, passwordParam, phoneNumberParam, firstNameParam);

            if (req.getMethod().equals(HttpMethods.POST)) {
                page = req.getContextPath() + UrlsEnum._MAIN.getUrl();
            } else {
                page = UrlManager.getProperty("path.page.main");
            }

        } catch (ServiceException e) {
            logger.error(e);
            req.getSession().setAttribute("errorInputMessage",
                    MessageManager.getProperty("message.errorInputMessage"));
            page = req.getContextPath() + UrlsEnum._SIGNUP.getUrl();
        }
        return page;
    }
}

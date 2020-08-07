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

public class CreateAccountCommand implements ActionCommand {
    private Logger logger = LogManager.getLogger(CreateAccountCommand.class);

    @Override
    public String execute(HttpServletRequest req) {
        String page;
        String phoneNumberParam = req.getParameter("phone_number");
        String emailParam = req.getParameter("email");
        String passwordParam = req.getParameter("password");
        String firstNameParam = req.getParameter("first_name");
        String roleParam = req.getParameter("role");
        try {

            ServiceFactory.INSTANCE.getAccountService().createAccount(emailParam,
                    passwordParam, phoneNumberParam, firstNameParam, roleParam);

            if (req.getMethod().equals(HttpMethods.POST)) {
                page = req.getContextPath() + UrlsEnum._ADMIN_ACCOUNTS.getUrl();
            } else {
                page = UrlManager.getProperty("path.action.show_accounts");
            }
        } catch (ServiceException e) {
            logger.error(e);
            req.getSession().setAttribute("errorMessage",
                    MessageManager.getProperty("message.common_error_message"));
            page = UrlManager.getProperty("path.action.show_accounts");
        }

        return page;
    }
}

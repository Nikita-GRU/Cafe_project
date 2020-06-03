package by.gruca.cafe.command;

import by.gruca.cafe.configuration.ConfigurationManager;
import by.gruca.cafe.configuration.MessageManager;
import by.gruca.cafe.service.AccountService;
import by.gruca.cafe.service.exception.ServiceException;
import by.gruca.cafe.service.impl.AccountServiceImpl;

import javax.servlet.http.HttpServletRequest;

public class LoginCommand implements ActionCommand {
    private static final String PARAM_NAME_LOGIN = "login";
    private static final String PARAM_NAME_PASSWORD = "password";

    @Override
    public String execute(HttpServletRequest request) {
        String page = null;
        String login = request.getParameter(PARAM_NAME_LOGIN);
        String pass = request.getParameter(PARAM_NAME_PASSWORD);
        AccountService accountService = new AccountServiceImpl();
        try {
            accountService.getAccountByLogin(login);
            request.setAttribute("username", login);
            page = ConfigurationManager.getProperty("path.page.main");
        } catch (ServiceException e) {
            request.setAttribute("errorLoginPassMessage",
                    MessageManager.getProperty("message.loginerror"));
            page = ConfigurationManager.getProperty("path.page.login");
        }

        return page;
    }
}

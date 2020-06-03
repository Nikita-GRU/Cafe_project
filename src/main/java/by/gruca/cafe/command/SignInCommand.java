package by.gruca.cafe.command;

import by.gruca.cafe.dao.config.ConnectionPoolProperties;
import by.gruca.cafe.configuration.ConfigurationManager;
import by.gruca.cafe.configuration.MessageManager;
import by.gruca.cafe.service.exception.ServiceException;
import by.gruca.cafe.service.impl.AccountServiceImpl;

import javax.servlet.http.HttpServletRequest;

public class SignInCommand implements ActionCommand {
    private static final String PARAM_NAME_LOGIN = "login";
    private static final String PARAM_NAME_PASSWORD = "password";

    @Override
    public String execute(HttpServletRequest request) {

        String page;
        String login = request.getParameter(PARAM_NAME_LOGIN);
        String pass = request.getParameter(PARAM_NAME_PASSWORD);
        AccountServiceImpl accountService = new AccountServiceImpl();
        ConnectionPoolProperties instance = ConnectionPoolProperties.INSTANCE;
        request.setAttribute("url1", instance.getUrl());
        try {
            accountService.createAccount(login, pass);
            page = ConfigurationManager.getProperty("path.page.main");
        } catch (ServiceException e) {
            request.setAttribute("errorLoginPassMessage",
                    MessageManager.getProperty("message.loginerror"));
            page = ConfigurationManager.getProperty("path.page.main");
        }
        return page;
    }
}

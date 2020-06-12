package by.gruca.cafe.command;


import by.gruca.cafe.configuration.ConfigurationManager;
import by.gruca.cafe.configuration.MessageManager;
import by.gruca.cafe.service.exception.ServiceException;
import by.gruca.cafe.service.impl.AccountServiceImpl;

import javax.servlet.http.HttpServletRequest;

public class SignUpCommand implements ActionCommand {
    private static final String PARAM_NAME_LOGIN = "login";
    private static final String PARAM_NAME_PASSWORD = "password";
    private static final String PARAM_NAME_EMAIL ="email" ;

    @Override
    public String execute(HttpServletRequest request) {

        String page;
        String login = request.getParameter(PARAM_NAME_LOGIN);
        String pass = request.getParameter(PARAM_NAME_PASSWORD);
        String email = request.getParameter(PARAM_NAME_EMAIL);
        AccountServiceImpl accountService = new AccountServiceImpl();
        try {
            accountService.createAccount(login, pass,email,null,null);
            page = ConfigurationManager.getProperty("path.page.main");
        } catch (ServiceException e) {
            request.setAttribute("errorLoginPassMessage",
                    MessageManager.getProperty("message.loginerror"));
            page = "/";
        }
        return page;
    }
}

package by.gruca.cafe.command;

import by.gruca.cafe.configuration.ConfigurationManager;
import by.gruca.cafe.configuration.MessageManager;
import by.gruca.cafe.model.Account;
import by.gruca.cafe.service.AccountService;
import by.gruca.cafe.service.exception.ServiceException;
import by.gruca.cafe.service.impl.AccountServiceImpl;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class LoginCommand implements ActionCommand {
    private static final String PARAM_NAME_LOGIN = "login";
    private static final String PARAM_NAME_PASSWORD = "password";

    @Override
    public String execute(HttpServletRequest request) {
        HttpSession session;
        Account account;
        String page = null;
        String login = request.getParameter(PARAM_NAME_LOGIN);
        String pass = request.getParameter(PARAM_NAME_PASSWORD);
        AccountService accountService = new AccountServiceImpl();
        try {
            account = accountService.getAccountByLogin(login);
            request.setAttribute("username", account.getLogin());
////            session = request.getSession();
////            if(session.getAttribute("role")== null){
////                session.setAttribute("role", )

//            }
            page = ConfigurationManager.getProperty("path.page.main");
        } catch (ServiceException e) {
            request.setAttribute("errorLoginPassMessage",
                    MessageManager.getProperty("message.loginerror"));
            page = ConfigurationManager.getProperty("path.page.login");
        }

        return page;
    }
}

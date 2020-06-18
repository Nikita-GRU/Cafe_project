package by.gruca.cafe.command;

import by.gruca.cafe.configuration.MessageManager;
import by.gruca.cafe.configuration.UrlManager;
import by.gruca.cafe.model.Account;
import by.gruca.cafe.service.AccountService;
import by.gruca.cafe.service.exception.ServiceException;
import by.gruca.cafe.service.impl.AccountServiceImpl;
import by.gruca.cafe.util.HashGeneratorUtil;

import javax.servlet.http.HttpServletRequest;

public class LoginCommand implements ActionCommand {
    private static final String PARAM_NAME_EMAIL = "email";
    private static final String PARAM_NAME_PASSWORD = "password";

    @Override
    public String execute(HttpServletRequest request) {
        HashGeneratorUtil hashGeneratorUtil = new HashGeneratorUtil();
        Account account;
        String page = null;
        String email = request.getParameter(PARAM_NAME_EMAIL);
        String pass = request.getParameter(PARAM_NAME_PASSWORD);
        AccountService accountService = new AccountServiceImpl();
        try {
            account = accountService.getAccountByEmail(email, pass);
            request.getSession().setAttribute("username", account.getFirstName());
            request.getSession().setAttribute("account", account);
            request.getSession().setAttribute("role", account.getRole().getRole());
            page = UrlManager.getProperty("path.page.main");
        } catch (ServiceException e) {
            request.setAttribute("errorLoginPassMessage",
                    MessageManager.getProperty("message.loginerror"));
            page = UrlManager.getProperty("path.page.login");
        }

        return page;
    }
}

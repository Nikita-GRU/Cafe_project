package by.gruca.cafe.controller.command.common;

import by.gruca.cafe.controller.UrlsEnum;
import by.gruca.cafe.controller.command.ActionCommand;
import by.gruca.cafe.configuration.MessageManager;
import by.gruca.cafe.configuration.UrlManager;
import by.gruca.cafe.factory.ServiceFactory;
import by.gruca.cafe.model.Account;
import by.gruca.cafe.service.AccountService;
import by.gruca.cafe.service.exception.ServiceException;
import by.gruca.cafe.service.impl.AccountServiceImpl;
import by.gruca.cafe.util.HashGeneratorUtil;
import com.google.api.client.http.HttpMethods;

import javax.servlet.http.HttpServletRequest;

public class LoginCommand implements ActionCommand {
    private static final String PARAM_NAME_EMAIL = "email";
    private static final String PARAM_NAME_PASSWORD = "password";

    @Override
    public String execute(HttpServletRequest req) {
        Account account;
        String page = null;
        String email = req.getParameter(PARAM_NAME_EMAIL);
        String pass = req.getParameter(PARAM_NAME_PASSWORD);
        AccountService accountService = new AccountServiceImpl();
        try {
            account = accountService.getAccountByEmailAndPassword(email, pass);
            req.getSession().setAttribute("username", account.getFirstName());
            req.getSession().setAttribute("account", account);
            req.getSession().setAttribute("role", account.getRole().getRoleValue());
            req.getSession().setAttribute("balance", ServiceFactory.INSTANCE.getAccountService().getBalance(account.getEmail()));
            req.getSession().setAttribute("credit_status", true);
            req.setAttribute("role", account.getRole().getRoleValue());

            if (req.getMethod().equals(HttpMethods.POST)) {
                page = req.getContextPath() + UrlsEnum._MAIN.getUrl();
            } else {
                page = UrlManager.getProperty("path.page.main");
            }

        } catch (ServiceException e) {
            req.getSession().setAttribute("errorMessage",
                    MessageManager.getProperty("message.loginerror"));
            page = req.getContextPath() + UrlsEnum._LOGIN.getUrl();
        }

        return page;
    }
}

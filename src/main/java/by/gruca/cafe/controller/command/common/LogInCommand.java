package by.gruca.cafe.controller.command.common;

import by.gruca.cafe.configuration.MessageManager;
import by.gruca.cafe.configuration.UrlManager;
import by.gruca.cafe.controller.UrlsEnum;
import by.gruca.cafe.controller.command.ActionCommand;
import by.gruca.cafe.model.Account;
import by.gruca.cafe.model.Role;
import by.gruca.cafe.service.AccountService;
import by.gruca.cafe.service.exception.ServiceException;
import by.gruca.cafe.service.impl.AccountServiceImpl;
import com.google.api.client.http.HttpMethods;

import javax.servlet.http.HttpServletRequest;

public class LogInCommand implements ActionCommand {
    private static final String PARAM_NAME_EMAIL = "email";
    private static final String PARAM_NAME_PASSWORD = "password";

    @Override
    public String execute(HttpServletRequest req) {
        req.getSession().removeAttribute("errorMessage");
        Account account;
        String page = null;
        String emailParam = req.getParameter(PARAM_NAME_EMAIL);
        String passwordParam = req.getParameter(PARAM_NAME_PASSWORD);
        AccountService accountService = new AccountServiceImpl();
        try {
            account = accountService.getAccountByEmailAndPassword(emailParam, passwordParam);
            req.getSession().setAttribute("account", account);
            req.getSession().setAttribute("role", account.getRole().getRoleValue());

            if (req.getMethod().equals(HttpMethods.POST)) {
                page = req.getContextPath() + UrlsEnum._MAIN.getUrl();
            } else {
                page = UrlManager.getProperty("path.page.main");
            }

        } catch (ServiceException e) {
            req.getSession().setAttribute("errorMessage",
                    MessageManager.getProperty("message.login_error"));
            page = req.getContextPath() + UrlsEnum._LOGIN.getUrl();
        }

        return page;
    }
}

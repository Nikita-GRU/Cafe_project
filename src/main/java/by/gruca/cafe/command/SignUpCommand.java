package by.gruca.cafe.command;


import by.gruca.cafe.configuration.MessageManager;
import by.gruca.cafe.configuration.UrlManager;
import by.gruca.cafe.factory.ServiceFactory;
import by.gruca.cafe.model.Account;
import by.gruca.cafe.service.exception.ServiceException;

import javax.servlet.http.HttpServletRequest;

public class SignUpCommand implements ActionCommand {

    private static final String PARAM_NAME_PASSWORD = "password";
    private static final String PARAM_NAME_EMAIL = "email";
    private static final String PARAM_NAME_FIRSTNAME = "firstname";
    private static final String PARAM_NAME_LASTNAME = "lastname";
    private static final String PARAM_NAME_PHONE_NUMBER = "phonenumber";

    @Override
    public String execute(HttpServletRequest request) {
        String page;
        Account account = new Account();
        account.setEmail(request.getParameter(PARAM_NAME_EMAIL));
        account.setPassword(request.getParameter(PARAM_NAME_PASSWORD));
        account.setPhoneNumber(Long.parseLong(request.getParameter(PARAM_NAME_PHONE_NUMBER)));
        account.setFirstName(request.getParameter(PARAM_NAME_FIRSTNAME));
        try {
            ServiceFactory.INSTANCE.getAccountService().createAccount(account);
            page = UrlManager.getProperty("path.page.main");
        } catch (ServiceException e) {
            request.setAttribute("errorLoginPassMessage",
                    MessageManager.getProperty("message.loginerror"));
            page = UrlManager.getProperty("path.page.main");
        }
        return page;
    }
}

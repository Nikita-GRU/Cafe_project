package by.gruca.cafe.controller.command.admin;

import by.gruca.cafe.configuration.MessageManager;
import by.gruca.cafe.configuration.UrlManager;
import by.gruca.cafe.controller.UrlsEnum;
import by.gruca.cafe.controller.command.ActionCommand;
import by.gruca.cafe.factory.ServiceFactory;
import by.gruca.cafe.model.Account;
import by.gruca.cafe.model.Role;
import by.gruca.cafe.service.exception.ServiceException;
import by.gruca.cafe.util.HashGeneratorUtil;
import by.gruca.cafe.util.UtilException;
import com.google.api.client.http.HttpMethods;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;

public class CreateAccountCommand implements ActionCommand {
    private Logger logger = LogManager.getLogger(CreateAccountCommand.class);

    @Override
    public String execute(HttpServletRequest req) {
        String page;
        HashGeneratorUtil hashGeneratorUtil = new HashGeneratorUtil();
        String phoneNumber = req.getParameter("phonenumber");
        String email = req.getParameter("email");
        String password = req.getParameter("password");
        String firstName = req.getParameter("firstname");
        String roleParameter = req.getParameter("role");
        Role role = Arrays.stream(Role.values())
                .filter(r -> r.getRoleValue().equals(roleParameter))
                .findAny()
                .orElse(Role.GUEST);
        Account account = new Account();
        account.setFirstName(firstName);
        account.setEmail(email);
        account.setPhoneNumber(Long.parseLong(phoneNumber));
        account.setRole(role);
        try {
            account.setPassword(hashGeneratorUtil.generateHash(password));
            ServiceFactory.INSTANCE.getAccountService().createAccount(account);
            if (req.getMethod().equals(HttpMethods.POST)) {
                page = req.getContextPath() + UrlsEnum._ADMIN_ACCOUNTS.getUrl();
            } else {
                page = UrlManager.getProperty("path.action.show_accounts");
            }
        } catch (ServiceException | UtilException e) {
            logger.error(e);
            req.getSession().setAttribute("errorMessage",
                    MessageManager.getProperty("message.common_error_message"));
            page = req.getContextPath() + UrlsEnum._ADMIN_NEWACCOUNT.getUrl();
        }

        return page;
    }
}

package by.gruca.cafe.command.admin;

import by.gruca.cafe.command.ActionCommand;
import by.gruca.cafe.configuration.UrlManager;
import by.gruca.cafe.factory.ServiceFactory;
import by.gruca.cafe.model.Account;
import by.gruca.cafe.model.Role;
import by.gruca.cafe.service.exception.ServiceException;
import by.gruca.cafe.util.HashGeneratorUtil;
import by.gruca.cafe.util.UtilException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;

public class CreateAccountCommand implements ActionCommand {
    private Logger logger = LogManager.getLogger(CreateAccountCommand.class);

    @Override
    public String execute(HttpServletRequest req) {
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
        } catch (UtilException e) {
            logger.error(e);
        }
        try {
            ServiceFactory.INSTANCE.getAccountService().createAccount(account);
        } catch (ServiceException e) {
            logger.error(e);
        }
        return UrlManager.getProperty("path.action.show_accounts");
    }
}

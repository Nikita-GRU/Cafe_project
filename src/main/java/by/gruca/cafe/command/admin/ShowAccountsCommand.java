package by.gruca.cafe.command.admin;

import by.gruca.cafe.command.ActionCommand;
import by.gruca.cafe.configuration.UrlManager;
import by.gruca.cafe.factory.ServiceFactory;
import by.gruca.cafe.model.Account;
import by.gruca.cafe.model.Role;
import by.gruca.cafe.service.exception.ServiceException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ShowAccountsCommand implements ActionCommand {
    private Logger logger = LogManager.getLogger(ShowAccountsCommand.class);

    @Override
    public String execute(HttpServletRequest req) {
        String subCommand = req.getParameter("sub_command");
        if (subCommand != null && !subCommand.isEmpty() && subCommand.equals("update")) {
            String email = req.getParameter("email_to_update");
            int bonusPoints = Integer.parseInt(req.getParameter("new_bonus"));
            String newRole = req.getParameter("new_role");
            Role role = Arrays.stream(Role.values())
                    .filter(r -> r.getRoleValue().equals(newRole))
                    .findAny().orElse(Role.GUEST);
            Account account = new Account();
            account.setBonusPoints(bonusPoints);
            account.setRole(role);

            try {
                ServiceFactory.INSTANCE.getAccountService().updateAccount(account, email);
            } catch (ServiceException e) {
                logger.error(e);
            }
        }
        List<Account> accounts = new ArrayList<Account>();
        try {
            accounts = ServiceFactory.INSTANCE.getAccountService().getAllAccounts();
        } catch (ServiceException e) {
            logger.error(e);
        }
        req.setAttribute("accounts", accounts);

        return UrlManager.getProperty("path.page.admin");
    }
}

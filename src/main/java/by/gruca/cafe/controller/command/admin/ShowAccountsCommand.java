package by.gruca.cafe.controller.command.admin;

import by.gruca.cafe.configuration.MessageManager;
import by.gruca.cafe.configuration.UrlManager;
import by.gruca.cafe.controller.UrlsEnum;
import by.gruca.cafe.controller.command.ActionCommand;
import by.gruca.cafe.factory.ServiceFactory;
import by.gruca.cafe.model.Account;
import by.gruca.cafe.model.Role;
import by.gruca.cafe.service.exception.ServiceException;
import com.google.api.client.http.HttpMethods;
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
        String page;
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
            if (req.getMethod().equals(HttpMethods.POST)) {
                page = req.getContextPath() + UrlsEnum._ADMIN_ACCOUNTS.getUrl();
            } else {
                page = UrlManager.getProperty("path.page.admin");
            }
        } catch (ServiceException e) {
            logger.error(e);
            req.getSession().setAttribute("errorMessage",
                    MessageManager.getProperty("message.common_error_message"));
            page = req.getContextPath() + UrlsEnum._ADMIN_ACCOUNTS.getUrl();
        }
        req.setAttribute("accounts", accounts);

        return page;
    }
}

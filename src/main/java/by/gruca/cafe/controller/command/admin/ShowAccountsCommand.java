package by.gruca.cafe.controller.command.admin;

import by.gruca.cafe.configuration.MessageManager;
import by.gruca.cafe.configuration.UrlManager;
import by.gruca.cafe.controller.UrlsEnum;
import by.gruca.cafe.controller.command.ActionCommand;
import by.gruca.cafe.factory.ServiceFactory;
import by.gruca.cafe.model.Account;
import by.gruca.cafe.model.Role;
import by.gruca.cafe.service.exception.ServiceException;
import by.gruca.cafe.util.Paginator;
import by.gruca.cafe.util.UtilException;
import com.google.api.client.http.HttpMethods;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

public class ShowAccountsCommand implements ActionCommand {
    private final Logger logger = LogManager.getLogger(ShowAccountsCommand.class);

    @Override
    public String execute(HttpServletRequest req) {
        String page;
        List<Role> roles = List.of(Role.values());
        req.setAttribute("roles", roles);
        String subCommand = req.getParameter("sub_command");

        if (subCommand != null && !subCommand.isEmpty() && subCommand.equals("update")) {
            String email = req.getParameter("email_to_update");
            String newRole = req.getParameter("new_role");
            String newBonus = req.getParameter("new_bonus");

            try {
                ServiceFactory.INSTANCE.getAccountService().updateAccount(email, newRole, newBonus);
            } catch (ServiceException e) {
                logger.error(e);
            }
        }

        List<Account> accounts = new ArrayList<>();
        try {
            int accountsCount = ServiceFactory.INSTANCE.getAccountService().getAccountsCount();
            Paginator paginator = new Paginator(req, accountsCount);
            accounts = ServiceFactory.INSTANCE.getAccountService().getPaginatedAccounts(paginator.getItemsPerPage(), paginator.getPageNumber());

            if (req.getMethod().equals(HttpMethods.POST)) {
                page = req.getParameter("redirect_uri") + "?" + req.getParameter("redirect_query");
            } else {
                page = UrlManager.getProperty("path.page.admin");
            }
        } catch (ServiceException | UtilException e) {
            logger.error(e);
            req.getSession().setAttribute("errorMessage",
                    MessageManager.getProperty("message.common_error_message"));
            page = UrlManager.getProperty("path.page.admin");
        }
        req.setAttribute("accounts", accounts);

        return page;
    }
}

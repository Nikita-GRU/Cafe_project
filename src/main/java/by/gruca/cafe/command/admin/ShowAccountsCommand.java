package by.gruca.cafe.command.admin;

import by.gruca.cafe.command.ActionCommand;
import by.gruca.cafe.configuration.UrlManager;
import by.gruca.cafe.factory.ServiceFactory;
import by.gruca.cafe.model.Account;
import by.gruca.cafe.service.exception.ServiceException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

public class ShowAccountsCommand implements ActionCommand {
    private Logger logger = LogManager.getLogger(ShowAccountsCommand.class);

    @Override
    public String execute(HttpServletRequest req) {
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

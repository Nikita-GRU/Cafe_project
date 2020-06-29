package by.gruca.cafe.command.admin;

import by.gruca.cafe.command.ActionCommand;
import by.gruca.cafe.configuration.UrlManager;

import javax.servlet.http.HttpServletRequest;

public class AddNewProductCommand implements ActionCommand {
    @Override
    public String execute(HttpServletRequest req) {

        return UrlManager.getProperty("path.page.admin");
    }
}

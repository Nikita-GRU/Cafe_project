package by.gruca.cafe.controller.command.admin;

import by.gruca.cafe.controller.command.ActionCommand;
import by.gruca.cafe.configuration.UrlManager;

import javax.servlet.http.HttpServletRequest;

public class AddNewAccountCommand implements ActionCommand {
    @Override
    public String execute(HttpServletRequest req) {
        return UrlManager.getProperty("path.page.admin");
    }
}

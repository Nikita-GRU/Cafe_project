package by.gruca.cafe.command.common;

import by.gruca.cafe.command.ActionCommand;
import by.gruca.cafe.configuration.UrlManager;

import javax.servlet.http.HttpServletRequest;

public class LogoutCommand implements ActionCommand {
    @Override
    public String execute(HttpServletRequest request) {
        String page = UrlManager.getProperty("path.page.index");
        request.getSession().invalidate();
        return page;
    }
}

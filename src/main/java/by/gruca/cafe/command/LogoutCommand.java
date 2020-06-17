package by.gruca.cafe.command;

import by.gruca.cafe.configuration.UrlManager;

import javax.servlet.http.HttpServletRequest;

public class LogoutCommand implements ActionCommand {
    @Override
    public String execute(HttpServletRequest request) {
        String page = UrlManager.getProperty("path.page.index");
// уничтожение сессии
        request.getSession().invalidate();
        return page;
    }
}

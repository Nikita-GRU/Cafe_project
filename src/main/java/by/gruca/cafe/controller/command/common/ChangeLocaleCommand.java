package by.gruca.cafe.controller.command.common;

import by.gruca.cafe.configuration.UrlManager;
import by.gruca.cafe.controller.UrlsEnum;
import by.gruca.cafe.controller.command.ActionCommand;
import com.google.api.client.http.HttpMethods;

import javax.servlet.http.HttpServletRequest;

public class ChangeLocaleCommand implements ActionCommand {
    @Override
    public String execute(HttpServletRequest req) {
        String page;
        String locale = req.getParameter("new_locale");
        req.getSession().setAttribute("locale", locale);
        if (req.getMethod().equals(HttpMethods.POST)) {
            page = req.getContextPath() + UrlsEnum._MAIN.getUrl();
        } else {
            page = UrlManager.getProperty("path.page.main");
        }
        return page;

    }
}

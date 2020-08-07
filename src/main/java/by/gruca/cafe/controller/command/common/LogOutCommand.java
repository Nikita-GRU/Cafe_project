package by.gruca.cafe.controller.command.common;

import by.gruca.cafe.configuration.UrlManager;
import by.gruca.cafe.controller.UrlsEnum;
import by.gruca.cafe.controller.command.ActionCommand;
import by.gruca.cafe.model.Role;
import com.google.api.client.http.HttpMethods;

import javax.servlet.http.HttpServletRequest;

public class LogOutCommand implements ActionCommand {
    @Override
    public String execute(HttpServletRequest req) {

        req.getSession().invalidate();
        req.getSession().setAttribute("role", Role.GUEST.getRoleValue());
        return req.getContextPath() + UrlsEnum._MAIN.getUrl();
    }
}

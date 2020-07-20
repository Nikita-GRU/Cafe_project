package by.gruca.cafe.controller.command.common;

import by.gruca.cafe.controller.UrlsEnum;
import by.gruca.cafe.controller.command.ActionCommand;
import by.gruca.cafe.model.Role;

import javax.servlet.http.HttpServletRequest;

public class LogoutCommand implements ActionCommand {
    @Override
    public String execute(HttpServletRequest req) {
        String page = req.getContextPath() + UrlsEnum._MAIN.getUrl();
        req.getSession().invalidate();
        req.getSession().setAttribute("role", Role.GUEST.getRoleValue());
        return page;
    }
}

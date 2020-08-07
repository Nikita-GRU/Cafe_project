package by.gruca.cafe.controller.command.admin;

import by.gruca.cafe.controller.command.ActionCommand;
import by.gruca.cafe.configuration.UrlManager;
import by.gruca.cafe.model.Category;
import by.gruca.cafe.model.Role;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public class AddNewAccountCommand implements ActionCommand {
    @Override
    public String execute(HttpServletRequest req) {
        List<Role> roles = List.of(Role.values());
        req.setAttribute("roles", roles);
        return UrlManager.getProperty("path.page.admin");
    }
}

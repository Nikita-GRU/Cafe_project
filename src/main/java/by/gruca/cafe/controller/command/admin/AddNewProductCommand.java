package by.gruca.cafe.controller.command.admin;

import by.gruca.cafe.configuration.UrlManager;
import by.gruca.cafe.controller.command.ActionCommand;
import by.gruca.cafe.model.Category;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public class AddNewProductCommand implements ActionCommand {
    @Override
    public String execute(HttpServletRequest req) {
        List<Category> categories = List.of(Category.values());
        req.setAttribute("categories", categories);
        return UrlManager.getProperty("path.page.admin");
    }
}

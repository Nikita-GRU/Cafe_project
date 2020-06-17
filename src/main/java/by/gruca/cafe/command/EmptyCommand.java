package by.gruca.cafe.command;

import by.gruca.cafe.configuration.UrlManager;

import javax.servlet.http.HttpServletRequest;

public class EmptyCommand  implements ActionCommand {
    @Override
    public String execute(HttpServletRequest request) {
        String page = UrlManager.getProperty("path.page.main");
        return page;
    }
}

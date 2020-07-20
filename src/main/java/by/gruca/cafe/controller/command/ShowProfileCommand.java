package by.gruca.cafe.controller.command;

import by.gruca.cafe.configuration.UrlManager;
import by.gruca.cafe.controller.UrlsEnum;
import com.google.api.client.http.HttpMethods;

import javax.servlet.http.HttpServletRequest;

public class ShowProfileCommand implements ActionCommand {
    @Override
    public String execute(HttpServletRequest req) {

        String page;
        if (req.getMethod().equals(HttpMethods.POST)) {
            page = req.getContextPath() + UrlsEnum._PROFILE_ORDERS.getUrl();
        } else {
            page = UrlManager.getProperty("path.page.profile");
        }
        return page;
    }
}

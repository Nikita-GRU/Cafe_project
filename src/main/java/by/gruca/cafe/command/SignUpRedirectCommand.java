package by.gruca.cafe.command;

import by.gruca.cafe.configuration.ConfigurationManager;

import javax.servlet.http.HttpServletRequest;

public class SignUpRedirectCommand implements ActionCommand {
    @Override
    public String execute(HttpServletRequest req) {
        return ConfigurationManager.getProperty("path.page.signup");
    }
}

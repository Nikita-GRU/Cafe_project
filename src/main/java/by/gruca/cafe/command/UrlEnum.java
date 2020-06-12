package by.gruca.cafe.command;

import by.gruca.cafe.configuration.ConfigurationManager;

public enum UrlEnum {
    SIGNUP {{
        this.page = ConfigurationManager.getProperty("path.page.signup");
    }}, LOGIN {{
        this.page = ConfigurationManager.getProperty("path.page.login");
    }}, ORDER {{
        this.page = ConfigurationManager.getProperty("path.page.order");
    }}, ACCOUNT {{
        this.page = ConfigurationManager.getProperty("path.page.account");
    }}, MENU {{
        this.page = ConfigurationManager.getProperty("path.page.menu");
    }}, CONTROLLER {{
        this.page = ConfigurationManager.getProperty("path.page.controller");
    }};

    String page;

    public String getPage() {
        return page;
    }
}

package by.gruca.cafe.command;

import by.gruca.cafe.configuration.UrlManager;

public enum UrlsEnum {
    ADMIN {{
        this.page = UrlManager.getProperty("path.page.admin");
    }},
    MODERATOR {{
        this.page = UrlManager.getProperty("path.action.moderation");
    }},
    ORDERSUCCESS {{
        this.page = UrlManager.getProperty("path.page.ordersuccess");
    }},
    CART {{
        this.page = UrlManager.getProperty("path.action.showcart");
    }},
    SIGNUP {{
        this.page = UrlManager.getProperty("path.page.signup");
    }},

    LOGIN {{
        this.page = UrlManager.getProperty("path.page.login");
    }},

    ORDER {{
        this.page = UrlManager.getProperty("path.page.order");
    }},

    ACCOUNT {{
        this.page = UrlManager.getProperty("path.page.account");
    }},

    MENU {{
        this.page = UrlManager.getProperty("path.action.showmenu");
    }},
    MAIN {{
        this.page = UrlManager.getProperty("path.action.login");
    }};

    String page;

    public String getPage() {
        return page;
    }
}
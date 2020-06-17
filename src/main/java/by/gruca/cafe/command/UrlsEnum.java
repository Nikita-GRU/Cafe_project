package by.gruca.cafe.command;

import by.gruca.cafe.configuration.UrlManager;

public enum UrlsEnum {
    CART {{
        this.page = UrlManager.getProperty("path.page.showcart");
    }},

    SIGNUP {
        {
            this.page = UrlManager.getProperty("path.page.signup");
        }
    },

    LOGIN {
        {
            this.page = UrlManager.getProperty("path.page.login");
        }
    },

    ORDER {
        {
            this.page = UrlManager.getProperty("path.page.order");
        }
    },

    ACCOUNT {
        {
            this.page = UrlManager.getProperty("path.page.account");
        }
    },

    MENU {
        {
            this.page = UrlManager.getProperty("path.page.showmenu");
        }
    },

    CONTROLLER {
        {
            this.page = UrlManager.getProperty("path.page.controller");
        }
    };


    String page;

    public String getPage() {
        return page;
    }
}
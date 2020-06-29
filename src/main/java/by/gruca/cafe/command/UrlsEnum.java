package by.gruca.cafe.command;

import by.gruca.cafe.configuration.UrlManager;

public enum UrlsEnum {
    IMAGE{{
        this.page = UrlManager.getProperty("path.action.image");
    }},
    MODERATOR_ORDERS {{
        this.page = UrlManager.getProperty("path.action.moderator_show_orders");
    }},
    MODERATOR_NEWORDER {{
        this.page = UrlManager.getProperty("path.action.moderator_add_new_order");
    }},

    ADMIN_ACCOUNTS {{
        this.page = UrlManager.getProperty("path.action.show_accounts");
    }},
    ADMIN_ORDERS {{
        this.page = UrlManager.getProperty("path.action.show_orders");
    }},
    ADMIN_PRODUCTS {{
        this.page = UrlManager.getProperty("path.action.show_products");
    }},
    ADMIN_NEWPRODUCT {{
        this.page = UrlManager.getProperty("path.action.add_new_product");
    }},
    ADMIN_NEWACCOUNT {{
        this.page = UrlManager.getProperty("path.action.add_new_account");
    }},
    ADMIN {{
        this.page = UrlManager.getProperty("path.page.admin");
    }},
    MODERATOR {{
        this.page = UrlManager.getProperty("path.page.moderator");
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
        this.page = UrlManager.getProperty("path.page.main");
    }};

    String page;

    public String getPage() {
        return page;
    }
}
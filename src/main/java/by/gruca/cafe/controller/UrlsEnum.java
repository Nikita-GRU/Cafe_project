package by.gruca.cafe.controller;

import by.gruca.cafe.configuration.UrlManager;

public enum UrlsEnum {
    _PROFILE(UrlManager.getProperty("path.page.profile")),
    _PROFILE_INFO(UrlManager.getProperty("path.action.show_profile")),
    _PROFILE_ORDERS(UrlManager.getProperty("path.action.show_profile_orders")),
    _IMAGE(UrlManager.getProperty("path.action.image")),
    _MODERATOR_ORDERS(UrlManager.getProperty("path.action.moderator_show_orders")),
    _MODERATOR_NEWORDER(UrlManager.getProperty("path.action.moderator_add_new_order")),
    _ADMIN_ACCOUNTS(UrlManager.getProperty("path.action.show_accounts")),
    _ADMIN_ORDERS(UrlManager.getProperty("path.action.show_orders")),
    _ADMIN_PRODUCTS(UrlManager.getProperty("path.action.show_products")),
    _ADMIN_NEWPRODUCT(UrlManager.getProperty("path.action.add_new_product")),
    _ADMIN_NEWACCOUNT(UrlManager.getProperty("path.action.add_new_account")),
    _ADMIN(UrlManager.getProperty("path.page.admin")),
    _MODERATOR(UrlManager.getProperty("path.page.moderator")),
    _ORDERSUCCESS(UrlManager.getProperty("path.page.order_success")),
    _CART(UrlManager.getProperty("path.action.show_cart")),
    _SIGNUP(UrlManager.getProperty("path.page.signup")),
    _LOGIN(UrlManager.getProperty("path.page.login")),
    _ORDER(UrlManager.getProperty("path.page.order")),
    _ACCOUNT(UrlManager.getProperty("path.page.account")),
    _MENU(UrlManager.getProperty("path.action.show_menu")),
    _MAIN(UrlManager.getProperty("path.page.main"));

    String page;
    String url;

    UrlsEnum(String page) {
        this.url = this.name().toLowerCase().replace("_", "/");
        this.page = page;
    }

    public String getUrl() {
        return url;
    }

    public String getPage() {
        return page;
    }
}
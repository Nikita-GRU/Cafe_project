package by.gruca.cafe.controller.command;

import by.gruca.cafe.controller.command.admin.*;
import by.gruca.cafe.controller.command.common.*;
import by.gruca.cafe.controller.command.moderator.DeleteOrderCommand;
import by.gruca.cafe.controller.command.moderator.OrderSetAcceptedCommand;
import by.gruca.cafe.controller.command.moderator.OrderSetDeliveredCommand;
import by.gruca.cafe.controller.command.moderator.ShowNotDeliveredOrders;

public enum CommandEnum {
    SHOW_PROFILE_ORDERS(new ShowProfileOrdersCommand()),
    SHOW_PROFILE(new ShowProfileCommand()),
    CHANGE_LOCALE(new ChangeLocaleCommand()),
    DOWNLOAD_IMAGE(new DownloadImage()),
    MODERATOR_SHOW_ORDERS(new ShowNotDeliveredOrders()),
    MODERATOR_ADD_ORDER(null),
    CREATE_PRODUCT(new CreateProductCommand()),
    CREATE_ACCOUNT(new CreateAccountCommand()),
    ADD_NEW_ACCOUNT(new AddNewAccountCommand()),
    ADD_NEW_PRODUCT(new AddNewProductCommand()),
    SHOW_PRODUCTS(new ShowProductsCommand()),
    SHOW_ORDERS(new ShowOrdersCommand()),
    ORDER_SET_ACCEPTED(new OrderSetAcceptedCommand()),
    ORDER_SET_DELIVERED(new OrderSetDeliveredCommand()),
    DELETE_FROM_CART(new DeleteFromCartCommand()),
    ORDER_DELETE(new DeleteOrderCommand()),
    EDIT_ACCOUNT(new EditAccountCommand()),
    BAN_ACCOUNT(new BanAccountCommand()),
    DELETE_ACCOUNT(new DeleteAccountCommand()),
    SHOW_ACCOUNTS(new ShowAccountsCommand()),
    ORDER(new OrderCommand()),
    SHOW_CART(new AddToCartCommand()),
    LOGIN(new LoginCommand()),
    LOGOUT(new LogoutCommand()),
    SIGNUP(new SignUpCommand()),
    SHOW_MENU(new ShowMenuCommand());

    ActionCommand command;

    CommandEnum(ActionCommand command) {
        this.command = command;
    }

    public ActionCommand getCurrentCommand() {
        return command;
    }
}

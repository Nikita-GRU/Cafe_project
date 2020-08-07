package by.gruca.cafe.controller.command;

import by.gruca.cafe.controller.command.admin.*;
import by.gruca.cafe.controller.command.common.*;
import by.gruca.cafe.controller.command.moderator.DeleteOrderCommand;
import by.gruca.cafe.controller.command.moderator.SetOrderStatus;
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
    SET_ORDER_STATUS(new SetOrderStatus()),
    DELETE_FROM_CART(new DeleteFromCartCommand()),
    ORDER_DELETE(new DeleteOrderCommand()),
    SHOW_ACCOUNTS(new ShowAccountsCommand()),
    ORDER(new OrderCommand()),
    SHOW_CART(new AddToCartCommand()),
    LOG_IN(new LogInCommand()),
    LOG_OUT(new LogOutCommand()),
    SIGN_UP(new SignUpCommand()),
    SHOW_MENU(new ShowMenuCommand()),
    TO_ORDER(new ToOrderCommand()),
    TO_CART(new ToCartCommand()),
    SET_ORDER_FEEDBACK(new SetOrderFeedbackCommand());

    ActionCommand command;

    CommandEnum(ActionCommand command) {
        this.command = command;
    }

    public ActionCommand getCurrentCommand() {
        return command;
    }
}

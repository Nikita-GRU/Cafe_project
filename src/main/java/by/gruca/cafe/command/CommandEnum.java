package by.gruca.cafe.command;

import by.gruca.cafe.command.admin.*;
import by.gruca.cafe.command.common.*;
import by.gruca.cafe.command.moderator.DeleteOrderCommand;
import by.gruca.cafe.command.moderator.OrderSetAcceptedCommand;
import by.gruca.cafe.command.moderator.OrderSetDeliveredCommand;
import by.gruca.cafe.command.moderator.ShowNotDeliveredOrders;

public enum CommandEnum {
    DOWNLOAD_IMAGE {{
        this.command = new DownloadImage();
    }},
    MODERATOR_SHOW_ORDERS {{
        this.command = new ShowNotDeliveredOrders();
    }},
    MODERATOR_ADD_ORDER {{
        this.command = null;
    }},
    CREATE_PRODUCT {{
        this.command = new CreateProductCommand();
    }},
    CREATE_ACCOUNT {{
        this.command = new CreateAccountCommand();
    }},
    ADD_NEW_ACCOUNT {{
        this.command = new AddNewAccountCommand();
    }},
    ADD_NEW_PRODUCT {{
        this.command = new AddNewProductCommand();
    }},
    SHOW_PRODUCTS {{
        this.command = new ShowProductsCommand();
    }},
    SHOW_ORDERS {{
        this.command = new ShowOrdersCommand();
    }},
    ORDER_SET_ACCEPTED {{
        this.command = new OrderSetAcceptedCommand();
    }},
    ORDER_SET_DELIVERED {{
        this.command = new OrderSetDeliveredCommand();
    }},
    DELETE_FROM_CART {{
        this.command = new DeleteFromCartCommand();
    }},
    ORDER_DELETE {{
        this.command = new DeleteOrderCommand();
    }},
    EDIT_ACCOUNT {{
        this.command = new EditAccountCommand();
    }},
    BAN_ACCOUNT {{
        this.command = new BanAccountCommand();
    }},
    DELETE_ACCOUNT {{
        this.command = new DeleteAccountCommand();
    }},
    SHOW_ACCOUNTS {{
        this.command = new ShowAccountsCommand();
    }},
    ORDER {{
        this.command = new OrderCommand();
    }},
    SHOWCART {{
        this.command = new AddToCartCommand();
    }},
    LOGIN {{
        this.command = new LoginCommand();
    }},
    LOGOUT {{
        this.command = new LogoutCommand();
    }},
    SIGNUP {{
        this.command = new SignUpCommand();
    }},
    SHOWMENU {{
        this.command = new ShowMenuCommand();
    }};
    ActionCommand command;

    public ActionCommand getCurrentCommand() {
        return command;
    }
}

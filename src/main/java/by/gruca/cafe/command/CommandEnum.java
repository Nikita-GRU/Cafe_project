package by.gruca.cafe.command;

import by.gruca.cafe.command.admin.*;
import by.gruca.cafe.command.moderator.*;

public enum CommandEnum {
    ORDER_SET_ACCEPTED {{
        this.command = new OrderSetAcceptedCommand();
    }},
    ORDER_SET_DELIVERED {{
        this.command = new OrderSetDeliveredCommand();
    }},
    DELETE_FROM_CART {{
        this.command = new DeleteFromCartCommand();
    }},
    EDIT_ORDER {{
        this.command = new EditOrderCommand();
    }},
    DELETE_ORDER {{
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
    SHOW_NOT_DELIVERED_ORDERS {{
        this.command = new ShowNotDeliveredOrders();
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

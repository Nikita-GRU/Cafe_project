package by.gruca.cafe.command;

public enum CommandEnum {
    ORDER {{
        this.command = new OrderCommand();
    }},
    SHOWCART {{
        this.command = new AddToCartCommand();
    }},
    LOGIN {
        {
            this.command = new LoginCommand();
        }
    },
    LOGOUT {
        {
            this.command = new LogoutCommand();
        }
    },
    SIGNUP {
        {
            this.command = new SignUpCommand();
        }
    },
    SHOWMENU {
        {
            this.command = new ShowMenuCommand();
        }
    };
    ActionCommand command;

    public ActionCommand getCurrentCommand() {
        return command;
    }
}

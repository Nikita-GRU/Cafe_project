package by.gruca.cafe.command;

public enum CommandEnum {
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
    SIGNIN {
        {
            this.command = new SignInCommand();
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

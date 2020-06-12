package by.gruca.cafe.command;

public enum CommandEnum {
    SIGNUPREDIRECT {{
        this.command = new SignUpRedirectCommand();
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

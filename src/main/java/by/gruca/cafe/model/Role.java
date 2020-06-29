package by.gruca.cafe.model;

public enum Role {
    ADMIN {{
        this.roleValue = "admin";
    }},
    MODERATOR {{
        this.roleValue = "moderator";
    }}, USER {{
        this.roleValue = "user";
    }}, GUEST {{
        this.roleValue = "guest";
    }}, BANNED {{
        this.roleValue = "banned";
    }};

    String roleValue;

    Role() {
    }

    public String getRoleValue() {
        return roleValue;
    }
}

package by.gruca.cafe.model;

public enum Role {
    ADMIN("admin"),
    MODERATOR("moderator"),
    USER("user"),
    GUEST("guest");

    String roleValue;

    Role(String role) {
        this.roleValue = role;
    }

    public String getRoleValue() {
        return roleValue;
    }
}

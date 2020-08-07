package by.gruca.cafe.model;

public enum Role {
    ADMIN(1,"admin"),
    MODERATOR(2,"moderator"),
    USER(3,"user"),
    GUEST(4,"guest");

    String roleValue;
    int id;

    Role(int id,String role) {
        this.roleValue = role;
        this.id = id;
    }

    public String getRoleValue() {
        return roleValue;
    }

    public int getId() {
        return id;
    }
}

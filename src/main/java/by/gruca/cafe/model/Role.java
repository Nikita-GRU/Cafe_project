package by.gruca.cafe.model;

public class Role {
    private String role;
    private int roleId;

    public Role() {
    }

    public Role(int roleId, String role) {
        this.role = role;
        this.roleId = roleId;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public int getRoleId() {
        return roleId;
    }

    public void setRoleId(int roleId) {
        this.roleId = roleId;
    }
}

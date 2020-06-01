package by.gruca.cafe.model;

import java.util.ArrayList;
import java.util.List;

public class Account {
    private int id;
    private String login;
    private String password; //hash!
    private String email;
    private String firstName;
    private String lastName;
    private Role role;
    private boolean isEnabled;

    public Account() {
    }

    public Account(String login, String password, String email, String firstName, String lastName, Role role, boolean isEnabled) {
        this.login = login;
        this.password = password;
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.role = role;
        this.isEnabled = isEnabled;
    }

    public boolean isEnabled() {
        return isEnabled;
    }

    public void setEnabled(boolean enabled) {
        isEnabled = enabled;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    @Override
    public String toString() {
        return "Account{" +
                "id=" + id +
                ", login='" + login + '\'' +
                ", password='" + password + '\'' +
                ", email='" + email + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", role=" + role.getRole() +
                ", isEnabled=" + isEnabled +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Account)) return false;

        Account account = (Account) o;

        if (getId() != account.getId()) return false;
        if (isEnabled() != account.isEnabled()) return false;
        if (!getLogin().equals(account.getLogin())) return false;
        if (!getPassword().equals(account.getPassword())) return false;
        if (!getEmail().equals(account.getEmail())) return false;
        if (!getFirstName().equals(account.getFirstName())) return false;
        if (!getLastName().equals(account.getLastName())) return false;
        return getRole().equals(account.getRole());
    }

    @Override
    public int hashCode() {
        int result = getId();
        result = 31 * result + getLogin().hashCode();
        result = 31 * result + getPassword().hashCode();
        result = 31 * result + getEmail().hashCode();
        result = 31 * result + getFirstName().hashCode();
        result = 31 * result + getLastName().hashCode();
        result = 31 * result + getRole().hashCode();
        result = 31 * result + (isEnabled() ? 1 : 0);
        return result;
    }
}

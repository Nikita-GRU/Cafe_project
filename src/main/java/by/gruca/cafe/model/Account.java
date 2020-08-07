package by.gruca.cafe.model;

import java.io.Serializable;
import java.math.BigDecimal;

public class Account implements Serializable {
    private static final long serialVersionUID = 42L;
    private long id;
    private String phoneNumber;
    private String password;
    private String email;
    private String firstName;
    private Role role;
    private BigDecimal balance;
    private int bonus;

    public int getBonus() {
        return bonus;
    }

    public void setBonus(int bonus) {
        this.bonus = bonus;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
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

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Account)) return false;

        Account account = (Account) o;

        if (getId() != account.getId()) return false;
        if (getBonus() != account.getBonus()) return false;
        if (getPhoneNumber() != null ? !getPhoneNumber().equals(account.getPhoneNumber()) : account.getPhoneNumber() != null)
            return false;
        if (getPassword() != null ? !getPassword().equals(account.getPassword()) : account.getPassword() != null)
            return false;
        if (!getEmail().equals(account.getEmail())) return false;
        if (getFirstName() != null ? !getFirstName().equals(account.getFirstName()) : account.getFirstName() != null)
            return false;
        if (getRole() != account.getRole()) return false;
        return getBalance() != null ? getBalance().equals(account.getBalance()) : account.getBalance() == null;
    }

    @Override
    public int hashCode() {
        int result = (int) (getId() ^ (getId() >>> 32));
        result = 31 * result + (getPhoneNumber() != null ? getPhoneNumber().hashCode() : 0);
        result = 31 * result + (getPassword() != null ? getPassword().hashCode() : 0);
        result = 31 * result + getEmail().hashCode();
        result = 31 * result + (getFirstName() != null ? getFirstName().hashCode() : 0);
        result = 31 * result + getRole().hashCode();
        result = 31 * result + (getBalance() != null ? getBalance().hashCode() : 0);
        result = 31 * result + getBonus();
        return result;
    }

    @Override
    public String toString() {
        return "Account{" +
                "id=" + id +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", password='" + password + '\'' +
                ", email='" + email + '\'' +
                ", firstName='" + firstName + '\'' +
                ", role=" + role +
                ", balance=" + balance +
                ", bonus=" + bonus +
                '}';
    }
}
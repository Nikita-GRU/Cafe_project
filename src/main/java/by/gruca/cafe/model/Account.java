package by.gruca.cafe.model;

public class Account {
    private int id;
    private int phoneNumber;
    private String password;
    private String email;
    private String firstName;
    private String lastName;
    private Role role;
    private boolean isEnabled;

    public Account() {
    }

    public int getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(int phoneNumber) {
        this.phoneNumber = phoneNumber;
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
                ", phoneNumber=" + phoneNumber +
                ", password='" + password + '\'' +
                ", email='" + email + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", role=" + role +
                ", isEnabled=" + isEnabled +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Account)) return false;

        Account account = (Account) o;

        if (getId() != account.getId()) return false;
        if (getPhoneNumber() != account.getPhoneNumber()) return false;
        if (isEnabled() != account.isEnabled()) return false;
        if (!getPassword().equals(account.getPassword())) return false;
        if (!getEmail().equals(account.getEmail())) return false;
        if (getFirstName() != null ? !getFirstName().equals(account.getFirstName()) : account.getFirstName() != null)
            return false;
        if (getLastName() != null ? !getLastName().equals(account.getLastName()) : account.getLastName() != null)
            return false;
        return getRole().equals(account.getRole());
    }

    @Override
    public int hashCode() {
        int result = getId();
        result = 31 * result + getPhoneNumber();
        result = 31 * result + getPassword().hashCode();
        result = 31 * result + getEmail().hashCode();
        result = 31 * result + (getFirstName() != null ? getFirstName().hashCode() : 0);
        result = 31 * result + (getLastName() != null ? getLastName().hashCode() : 0);
        result = 31 * result + getRole().hashCode();
        result = 31 * result + (isEnabled() ? 1 : 0);
        return result;
    }
}
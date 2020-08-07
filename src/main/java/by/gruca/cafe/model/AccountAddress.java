package by.gruca.cafe.model;

public class AccountAddress {
    private long id;
    private String street;
    private String building;
    private String apartment;
    private long accountId;

    public AccountAddress() {
    }

    public AccountAddress(String street, String building, String apartment, long accountId) {
        this.street = street;
        this.building = building;
        this.apartment = apartment;
        this.accountId = accountId;
    }


    public long getAccountId() {
        return accountId;
    }

    public void setAccountId(long accountId) {
        this.accountId = accountId;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getBuilding() {
        return building;
    }

    public void setBuilding(String building) {
        this.building = building;
    }

    public String getApartment() {
        return apartment;
    }

    public void setApartment(String apartment) {
        this.apartment = apartment;
    }

    @Override
    public String toString() {
        return "AccountAddress{" +
                "id=" + id +
                ", street='" + street + '\'' +
                ", building='" + building + '\'' +
                ", apartment='" + apartment + '\'' +
                ", accountId=" + accountId +
                '}';
    }
}

package by.gruca.cafe.model;

public class Address {
    public static final Address DEFAULT = new Address("default","default","default");
    private String street;
    private String building;
    private String apartment;

    public Address() {
    }

    public Address(String street, String building, String apartment) {
        this.street = street;
        this.building = building;
        this.apartment = apartment;
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
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Address)) return false;

        Address address = (Address) o;

        if (getStreet() != null ? !getStreet().equals(address.getStreet()) : address.getStreet() != null) return false;
        if (getBuilding() != null ? !getBuilding().equals(address.getBuilding()) : address.getBuilding() != null)
            return false;
        return getApartment() != null ? getApartment().equals(address.getApartment()) : address.getApartment() == null;
    }

    @Override
    public int hashCode() {
        int result = getStreet() != null ? getStreet().hashCode() : 0;
        result = 31 * result + (getBuilding() != null ? getBuilding().hashCode() : 0);
        result = 31 * result + (getApartment() != null ? getApartment().hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Address{" +
                "street='" + street + '\'' +
                ", building='" + building + '\'' +
                ", apartment='" + apartment + '\'' +
                '}';
    }
}

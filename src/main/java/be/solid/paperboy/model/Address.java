package be.solid.paperboy.model;

import static com.google.common.base.Preconditions.checkNotNull;

public class Address {
    private final String street;
    private final String houseNr;

    private Address(String street, String houseNr) {
        checkNotNull(street);
        checkNotNull(houseNr);
        this.street = street;
        this.houseNr = houseNr;
    }

    public String getStreet() {
        return street;
    }

    public String getHouseNr() {
        return houseNr;
    }

    @Override
    public String toString() {
        return "Address{" +
                "street='" + street + '\'' +
                ", houseNr='" + houseNr + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Address address = (Address) o;

        if (!street.equals(address.street)) return false;
        return houseNr.equals(address.houseNr);

    }

    @Override
    public int hashCode() {
        int result = street.hashCode();
        result = 31 * result + houseNr.hashCode();
        return result;
    }

    static Address createAddress(String street, String houseNr) {
        return new Address(street, houseNr);
    }

}

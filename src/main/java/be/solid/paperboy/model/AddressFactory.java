package be.solid.paperboy.model;

class AddressFactory {

    private static final String NOT_SPECIFIED = "NOT_SPECIFIED";

    private AddressFactory() {
    }

    public static Address createAddress() {
        final Address address = new Address();
        address.setStreet(NOT_SPECIFIED);
        address.setHouseNr(NOT_SPECIFIED);
        return address;
    }
}

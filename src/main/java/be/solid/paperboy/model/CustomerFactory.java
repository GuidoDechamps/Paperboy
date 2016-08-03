package be.solid.paperboy.model;

import static be.solid.paperboy.model.AddressFactory.createAddress;

public class CustomerFactory {

    private static final String NOT_SPECIFIED = "NOT_SPECIFIED";
    private final WalletFactory walletFactory;

    public CustomerFactory(WalletFactory walletFactory) {
        this.walletFactory = walletFactory;
    }

    public Customer createCustomer() {
        return createCustomer(NOT_SPECIFIED, 0, NOT_SPECIFIED);
    }


    public Customer createCustomer(String streetName, int moneyAMount, String houseNr) {
        final Customer customer = new Customer();
        customer.setWallet(walletFactory.createWallet(moneyAMount));
        final Address address = createAddress();
        address.setStreet(streetName);
        address.setHouseNr(houseNr);
        customer.setAddress(address);
        return customer;
    }

}

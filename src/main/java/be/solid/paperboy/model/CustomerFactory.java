package be.solid.paperboy.model;

import static be.solid.paperboy.model.Address.createAddress;

public class CustomerFactory {

    private final WalletFactory walletFactory;

    public CustomerFactory(WalletFactory walletFactory) {
        this.walletFactory = walletFactory;
    }


    public Customer createCustomer(String streetName, int moneyAMount, String houseNr) {
        final Wallet wallet = walletFactory.createWallet(moneyAMount);
        final Address address = createAddress(streetName, houseNr);
        return new Customer(address, wallet);
    }

}

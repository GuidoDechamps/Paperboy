package be.solid.paperboy.model;

import javax.money.CurrencyUnit;
import javax.money.Monetary;

public class WalletFactory {

    private final CurrencyUnit currencyCode;

    public WalletFactory(String currencyCode) {
        this.currencyCode = Monetary.getCurrency(currencyCode);
    }


    public Wallet createWallet() {
        return createWallet(0);
    }

    public Wallet createWallet(int amount) {
        return new Wallet(amount, currencyCode);
    }


}

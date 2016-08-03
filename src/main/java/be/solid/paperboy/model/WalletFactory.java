package be.solid.paperboy.model;

import org.javamoney.moneta.Money;

public class WalletFactory {

    private final String currencyCode;

    public WalletFactory(String currencyCode) {
        this.currencyCode = currencyCode;
    }


    public Wallet createWallet() {
        return createWallet(0);
    }

    public Wallet createWallet(int amount) {
        final Wallet wallet = new Wallet();
        wallet.setMoney(createMoney(amount));
        return wallet;
    }

    private Money createMoney(int amount) {
        return Money.of(amount, currencyCode);
    }

}

package be.solid.paperboy.model;

import be.solid.paperboy.service.FactoriesForTest;
import org.javamoney.moneta.Money;
import org.junit.Test;

import javax.money.MonetaryAmount;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

public class WalletTest {

    private static final int AMOUNT = 10;
    private static final WalletFactory WALLET_FACTORY = new WalletFactory(FactoriesForTest.DEFAULT_CURRENCY_CODE);
    private static final Money MONEY = FactoriesForTest.createMoney(AMOUNT);

    @Test
    public void takeAllTheMoney() throws Exception {
        final Wallet wallet = WALLET_FACTORY.createWallet(AMOUNT);
        final MonetaryAmount monetaryAmount = wallet.takeMoney(MONEY.getNumber()).get();
        assertEquals(AMOUNT, getMoneyAmount(monetaryAmount));
        assertEquals(0, getMoneyAmount(wallet));
    }

    @Test
    public void takeHalfTheMoney() throws Exception {
        final Wallet wallet = WALLET_FACTORY.createWallet(AMOUNT);
        final MonetaryAmount monetaryAmount = wallet.takeMoney(MONEY.divide(2).getNumber()).get();
        assertEquals(AMOUNT / 2, getMoneyAmount(monetaryAmount));
        assertEquals(AMOUNT / 2, getMoneyAmount(wallet));
    }

    @Test
    public void takeToMuchMoney() throws Exception {
        final Wallet wallet = WALLET_FACTORY.createWallet(AMOUNT);
        final Optional<MonetaryAmount> amount = wallet.takeMoney(MONEY.multiply(2).getNumber());
        assertFalse(amount.isPresent());
        assertEquals(AMOUNT, getMoneyAmount(wallet));
    }

    @Test
    public void addMoney() throws Exception {
        final Wallet wallet = WALLET_FACTORY.createWallet(AMOUNT);
        wallet.add(MONEY);
        assertEquals(AMOUNT * 2, getMoneyAmount(wallet));
    }

    @Test
    public void addNegativeMoney() throws Exception {
        final Wallet wallet = WALLET_FACTORY.createWallet(AMOUNT);
        wallet.add(MONEY.negate());
        assertEquals(AMOUNT, getMoneyAmount(wallet));
    }

    private int getMoneyAmount(MonetaryAmount monetaryAmount) {
        return monetaryAmount.getNumber().intValue();
    }

    private int getMoneyAmount(Wallet wallet) {
        return getMoneyAmount(wallet.getAmountOfMoney());
    }


}

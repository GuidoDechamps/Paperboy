package be.solid.paperboy.model;

import be.solid.paperboy.service.FactoriesForTest;
import com.google.common.collect.Lists;
import org.junit.Before;
import org.junit.Test;

import javax.money.MonetaryAmount;
import java.time.LocalDate;
import java.util.Optional;

import static org.junit.Assert.*;

public class CustomerTest {

    private static final String HOUSE_NR = "42";
    private static final int CUSTOMER_MONEY = 10;
    private static final Address ADDRESS = Address.createAddress(Streets.SEASAM_STREET, HOUSE_NR);
    private static final WalletFactory WALLET_FACTORY = new WalletFactory(FactoriesForTest.DEFAULT_CURRENCY_CODE);
    private Customer customer;
    private PaperBoy paperBoy;

    @Before
    public void setUp() {
        customer = new Customer(ADDRESS, WALLET_FACTORY.createWallet(CUSTOMER_MONEY));
        paperBoy = new PaperBoy(WALLET_FACTORY.createWallet(0));
    }

    @Test
    public void buyPaper() {
        dealPaper(CUSTOMER_MONEY - 1);

        customer.buyPaper(paperBoy);

        assertEquals(1, getCustomerMoneyAmount());
        assertTrue(customer.hasPaper());
        assertEquals(CUSTOMER_MONEY - 1, getPaperBoyMoneyAmount());

    }

    @Test
    public void canNotBuyOverpricedPaper() {
        dealPaper(CUSTOMER_MONEY + 1);

        customer.buyPaper(paperBoy);

        assertEquals(CUSTOMER_MONEY, getCustomerMoneyAmount());
        assertFalse(customer.hasPaper());
        assertEquals(0, getPaperBoyMoneyAmount());

    }

    @Test(expected = RuntimeException.class)
    public void buyPaperFromPaperLessPaperBoy() {
        customer.buyPaper(paperBoy);
    }

    @Test
    public void evilPaperBoy() {
        final PaperBoy noPaperForYouPaperBoy = createNoPaperForYouPaperBoy();
        dealPaperToPaperBoy(5, noPaperForYouPaperBoy);

        customer.buyPaper(noPaperForYouPaperBoy);

        assertEquals(CUSTOMER_MONEY, getCustomerMoneyAmount());
        assertFalse(customer.hasPaper());

    }

    private static PaperBoy createNoPaperForYouPaperBoy() {
        return new PaperBoy(WALLET_FACTORY.createWallet(0)) {
            @Override
            public Optional<Paper> sellPaper(MonetaryAmount money) {
                return Optional.empty();
            }
        };
    }

    private void dealPaper(int paperPrice) {
        dealPaperToPaperBoy(paperPrice, paperBoy);
    }

    private void dealPaperToPaperBoy(int paperPrice, PaperBoy paperBoy) {
        final Paper paper = createPaper(paperPrice);
        paperBoy.loadPapers(Lists.newArrayList(paper));
    }

    private int getCustomerMoneyAmount() {
        return getMoneyAmount(customer.getAmountOfMoney());
    }

    private int getPaperBoyMoneyAmount() {
        return getMoneyAmount(paperBoy.getAmountOfMoney());
    }

    private int getMoneyAmount(MonetaryAmount amountOfMoney) {
        return amountOfMoney.getNumber().intValueExact();
    }

    private Paper createPaper(int amount) {
        return Paper.create(LocalDate.MIN, FactoriesForTest.createMoney(amount));
    }

}


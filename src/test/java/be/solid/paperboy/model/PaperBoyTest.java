package be.solid.paperboy.model;

import be.solid.paperboy.service.FactoriesForTest;
import com.google.common.collect.Lists;
import org.javamoney.moneta.Money;
import org.junit.Before;
import org.junit.Test;

import javax.money.MonetaryAmount;
import java.time.LocalDate;
import java.util.Optional;

import static org.junit.Assert.*;

public class PaperBoyTest {
    private static final int PAPER_PRICE = 10;
    private static final Money PAPER_PRICE_MONEY = FactoriesForTest.createMoney(PAPER_PRICE);
    private static final WalletFactory WALLET_FACTORY = new WalletFactory(FactoriesForTest.DEFAULT_CURRENCY_CODE);
    private PaperBoy paperBoy;

    @Before
    public void setUp() {
        paperBoy = new PaperBoy(WALLET_FACTORY.createWallet(0));
    }

    @Test
    public void sellPaper() {
        dealPaper();

        final Optional<Paper> paper = paperBoy.sellPaper(PAPER_PRICE_MONEY);

        assertTrue(paper.isPresent());
        assertEquals(PAPER_PRICE, getPaperBoyMoneyAmount());

    }

    @Test
    public void canNotSellBelowPrice() {
        dealPaper();

        final Optional<Paper> paper = paperBoy.sellPaper(PAPER_PRICE_MONEY.divide(2));

        assertFalse(paper.isPresent());
        assertEquals(0, getPaperBoyMoneyAmount());

    }

    @Test
    public void canSellAbovePrice() {
        dealPaper();

        final Optional<Paper> paper = paperBoy.sellPaper(PAPER_PRICE_MONEY.multiply(2));

        assertTrue(paper.isPresent());
        assertEquals(PAPER_PRICE * 2, getPaperBoyMoneyAmount());

    }

    private void dealPaper() {
        dealPaperToPaperBoy(paperBoy);
    }

    private void dealPaperToPaperBoy(PaperBoy paperBoy) {
        final Paper paper = createPaper();
        paperBoy.loadPapers(Lists.newArrayList(paper));
    }

    private int getPaperBoyMoneyAmount() {
        return getMoneyAmount(paperBoy.getAmountOfMoney());
    }

    private int getMoneyAmount(MonetaryAmount amountOfMoney) {
        return amountOfMoney.getNumber().intValueExact();
    }

    private Paper createPaper() {
        return Paper.create(LocalDate.MIN, PAPER_PRICE_MONEY);
    }
}


package be.solid.paperboy.model;

import be.solid.paperboy.service.PaperPriceService;
import org.javamoney.moneta.Money;

import javax.money.MonetaryAmount;
import java.time.LocalDate;

import static com.google.common.base.Preconditions.checkState;

public class FixedPaperPriceService implements PaperPriceService {

    private final String currency;
    private int price;

    private FixedPaperPriceService(int price, String currency) {
        this.price = price;
        this.currency = currency;
    }

    public static FixedPaperPriceService createDefault() {
        return new FixedPaperPriceService(1, "EUR");
    }

    @Override
    public MonetaryAmount getPaperPrice(LocalDate date) {
        return Money.of(price, currency);
    }

    public void setPrice(int price) {
        checkState(price > 0);
        this.price = price;
    }
}

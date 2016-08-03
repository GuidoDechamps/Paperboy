package be.solid.paperboy.service;

import be.solid.paperboy.model.CustomerRepository;
import org.javamoney.moneta.Money;

public class FactoriesForTest {
    public static final String DEFAULT_CURRENCY_CODE = "EUR";

    private FactoriesForTest() {
    }

    public static Money createMoney(int amount) {
        return Money.of(amount, DEFAULT_CURRENCY_CODE);
    }

    public static OnePaperBoyPerStreetStrategy createDeliveryStrategy(
            PaperBoyRoundService paperBoyRoundService,
            CustomerRepository customerRepository,
            LoadPaperService loadPaperService) {
        return new OnePaperBoyPerStreetStrategy(paperBoyRoundService, loadPaperService, customerRepository);
    }

}

package be.solid.paperboy;

import be.solid.paperboy.model.*;
import be.solid.paperboy.service.FactoriesForTest;
import org.javamoney.moneta.Money;
import org.javamoney.moneta.function.MonetaryFunctions;

import javax.money.MonetaryAmount;


class MoneyInCirculationService {

    private static final Money NO_MONEY = Money.of(0, FactoriesForTest.DEFAULT_CURRENCY_CODE);
    private final PaperBoyRepository paperBoyRepository;
    private final CustomerRepository customerRepository;

    MoneyInCirculationService(PaperBoyRepository paperBoyRepository, CustomerRepository customerRepository) {
        this.paperBoyRepository = paperBoyRepository;
        this.customerRepository = customerRepository;
    }


    MonetaryAmount countAllTheMoneyInCirculation() {
        final MonetaryAmount totalCustomerMoney = countAllTheCustomerMoney();
        final MonetaryAmount totalPaperBoyMoney = countAllThePaperBoyMoney();
        return totalCustomerMoney.add(totalPaperBoyMoney);
    }

    private MonetaryAmount countAllThePaperBoyMoney() {
        return paperBoyRepository.getAll().stream()
                .map(PaperBoy::getWallet)
                .map(Wallet::getMoney)
                .reduce(MonetaryFunctions.sum())
                .orElse(NO_MONEY);
    }

    private MonetaryAmount countAllTheCustomerMoney() {
        return customerRepository.getAll().stream()
                .map(Customer::getWallet)
                .map(Wallet::getMoney)
                .reduce(MonetaryFunctions.sum())
                .orElse(NO_MONEY);
    }
}

package be.solid.paperboy.model;

import org.javamoney.moneta.Money;

import javax.money.CurrencyUnit;
import javax.money.MonetaryAmount;
import javax.money.NumberValue;
import java.util.Optional;

import static org.javamoney.moneta.Money.of;

public class Wallet {
    private MonetaryAmount money;

    Wallet(Number amount, CurrencyUnit currency) {
        this.money = of(amount, currency);
    }

    public Optional<MonetaryAmount> takeMoney(NumberValue amount) {
        final Money extractedMoney = of(amount, money.getCurrency());
        if (money.isGreaterThanOrEqualTo(extractedMoney)) {
            this.money = money.subtract(extractedMoney);
            return Optional.of(extractedMoney);
        } else
            return Optional.empty();
    }


    public boolean containsAmount(MonetaryAmount customerMoney) {
        return money.isGreaterThanOrEqualTo(customerMoney);
    }

    public void add(MonetaryAmount toAdd) {
        if (toAdd.isPositive())
            this.money = this.money.add(toAdd);
    }

    public MonetaryAmount getAmountOfMoney() {
        return Money.from(money);
    }


}

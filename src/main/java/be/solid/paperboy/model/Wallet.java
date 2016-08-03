package be.solid.paperboy.model;

import javax.money.MonetaryAmount;

public class Wallet {
    private MonetaryAmount money;

    public MonetaryAmount getMoney() {
        return money;
    }

    public void setMoney(MonetaryAmount money) {
        this.money = money;
    }

}

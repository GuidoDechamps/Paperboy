package be.solid.paperboy.model;

import javax.money.MonetaryAmount;
import java.util.Optional;

import static com.google.common.base.Preconditions.checkNotNull;

public class Customer {

    private final Wallet wallet;
    private final Address address;
    private Paper paper;//TODO Nullable. Use of optional not advised by java designers for fields.


    Customer(Address address, Wallet wallet) {
        checkNotNull(address);
        checkNotNull(wallet);
        this.address = address;
        this.wallet = wallet;
    }

    public void buyPaper(PaperBoy paperBoy) {
        final MonetaryAmount price = paperBoy.getPaperPrice();
        if (wantsPaper(price)) {
            final MonetaryAmount money = wallet.takeMoney(price.getNumber());
            final Optional<Paper> paper = paperBoy.sellPaper(money);//TODO test case if money was taken but no paper returned, then money should be returned
            paper.ifPresent(this::setPaper);
        }
    }

    public boolean livesHere(Address address) {
        return this.address.equals(address);
    }

    public boolean hasMoney(MonetaryAmount customerMoney) {
        return wallet.containsAmount(customerMoney);
    }

    public boolean hasPaper() {
        return paper != null;
    }

    public MonetaryAmount getAmountOfMoney() {
        return wallet.getAmountOfMoney();
    }

    @Override
    public String toString() {
        return "Customer{" +
                "address=" + address +
                '}';
    }

    public Address getAddress() {
        return address;
    }

    private void setPaper(Paper x) {
        this.paper = x;
    }

    private boolean wantsPaper(MonetaryAmount price) {
        return !hasPaper() && wallet.containsAmount(price);
    }

}

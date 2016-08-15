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
            final Optional<Paper> paper = paperBoy.sellPaper(money);
            if (paper.isPresent())
                this.setPaper(paper.get());
            else
                wallet.add(money);
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

    public Address getAddress() {
        return address;
    }

    @Override
    public String toString() {
        return "Customer{" +
                "address=" + address +
                '}';
    }

    private void setPaper(Paper x) {
        this.paper = x;
    }

    private boolean wantsPaper(MonetaryAmount price) {
        return !hasPaper() && hasMoney(price);
    }

}

package be.solid.paperboy.model;

public class Customer {
    private Wallet wallet;
    private Paper paper;
    private Address address;


    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public Wallet getWallet() {
        return wallet;
    }

    public void setWallet(Wallet wallet) {
        this.wallet = wallet;
    }

    public Paper getPaper() {
        return paper;
    }

    public void setPaper(Paper paper) {
        this.paper = paper;
    }

    @Override
    public String toString() {
        return "Customer{" +
                "address=" + address +
                '}';
    }
}

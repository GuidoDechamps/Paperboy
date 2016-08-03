package be.solid.paperboy;

class CustomerDTO {
    private String street = "NONE";
    private String houseNr = "NONE";
    private int money = -1;
    private boolean ownsPaper = false;

    public String getHouseNr() {
        return houseNr;
    }

    public void setHouseNr(String houseNr) {
        this.houseNr = houseNr;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public int getMoney() {
        return money;
    }

    public void setMoney(int money) {
        this.money = money;
    }

    public boolean getOwnsPaper() {
        return ownsPaper;
    }

    public void setOwnsPaper(boolean ownsPaper) {
        this.ownsPaper = ownsPaper;
    }

    @Override
    public String toString() {
        return "CustomerDTO{" +
                "street='" + street + '\'' +
                ", houseNr='" + houseNr + '\'' +
                ", money=" + money +
                ", ownsPaper=" + ownsPaper +
                '}';
    }
}

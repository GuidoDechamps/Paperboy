package be.solid.paperboy.model;

import java.util.List;

public class PaperBoy {
    private Wallet wallet;
    private List<Paper> papers;


    public Wallet getWallet() {
        return wallet;
    }

    public void setWallet(Wallet wallet) {
        this.wallet = wallet;
    }

    public List<Paper> getPapers() {
        return papers;
    }

    public void setPapers(List<Paper> papers) {
        this.papers = papers;
    }

}

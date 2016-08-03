package be.solid.paperboy.service;


import be.solid.paperboy.model.Customer;
import be.solid.paperboy.model.Paper;
import be.solid.paperboy.model.PaperBoy;
import be.solid.paperboy.model.Wallet;

import javax.money.MonetaryAmount;
import java.util.Set;

public class PaperBoyRoundService {

    public void deliverPapers(PaperBoy paperBoy, Set<Customer> customers) {
        customers.stream().forEach(x -> deliverPaper(paperBoy, x));
    }


    private void deliverPaper(PaperBoy paperBoy, Customer customer) {
        final Paper paper = getPaper(paperBoy);
        final MonetaryAmount unitPriceOfPaper = paper.getUnitPriceOfPaper();
        final Wallet customerWallet = customer.getWallet();
        final MonetaryAmount beforeBuyCustomerMoney = customerWallet.getMoney();

        if (beforeBuyCustomerMoney.isGreaterThanOrEqualTo(unitPriceOfPaper)) {
            customer.setPaper(paper);
            paperBoy.getPapers().remove(0);

            final MonetaryAmount afterBuyCustomerMoney = beforeBuyCustomerMoney.subtract(unitPriceOfPaper);
            customerWallet.setMoney(afterBuyCustomerMoney);

            final Wallet paperBoyWallet = paperBoy.getWallet();
            final MonetaryAmount beforeBuyPaperBoyMoney = paperBoyWallet.getMoney();
            final MonetaryAmount afterBuyPaperBoyMoney = beforeBuyPaperBoyMoney.add(unitPriceOfPaper);
            paperBoyWallet.setMoney(afterBuyPaperBoyMoney);
        }
    }

    private Paper getPaper(PaperBoy paperBoy) {
        if (paperBoy.getPapers().isEmpty())
            throw new RuntimeException(paperBoy + " is out of papers!");
        else
            return paperBoy.getPapers().get(0);
    }
}

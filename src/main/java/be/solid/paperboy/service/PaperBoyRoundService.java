package be.solid.paperboy.service;


import be.solid.paperboy.model.Customer;
import be.solid.paperboy.model.PaperBoy;

import java.util.Set;

public class PaperBoyRoundService {

    public void deliverPapers(PaperBoy paperBoy, Set<Customer> customers) {
        customers.forEach(x -> x.buyPaper(paperBoy));
    }

}

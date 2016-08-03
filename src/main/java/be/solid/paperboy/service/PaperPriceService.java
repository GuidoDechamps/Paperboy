package be.solid.paperboy.service;

import javax.money.MonetaryAmount;
import java.time.LocalDate;

public interface PaperPriceService {

    MonetaryAmount getPaperPrice(LocalDate date);
}

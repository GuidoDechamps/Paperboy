package be.solid.paperboy.model;

import javax.money.MonetaryAmount;
import java.time.LocalDate;

public class Paper {
    private LocalDate date;
    private MonetaryAmount unitPriceOfPaper;

    public MonetaryAmount getUnitPriceOfPaper() {
        return unitPriceOfPaper;
    }

    public void setUnitPriceOfPaper(MonetaryAmount unitPriceOfPaper) {
        this.unitPriceOfPaper = unitPriceOfPaper;
    }

    public LocalDate getDateTime() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }
}

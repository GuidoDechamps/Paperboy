package be.solid.paperboy.service;

public interface DeliveryStrategy {
    void deliverPapers(DeliverPapersCommand command);
}

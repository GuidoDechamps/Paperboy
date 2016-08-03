package be.solid.paperboy.service;

import be.solid.paperboy.model.Customer;
import be.solid.paperboy.model.CustomerRepository;
import be.solid.paperboy.model.PaperBoy;
import com.google.common.collect.Iterables;

import java.util.Iterator;
import java.util.Set;
import java.util.function.Consumer;

class OnePaperBoyPerStreetStrategy implements DeliveryStrategy {
    private final PaperBoyRoundService paperBoyRoundService;
    private final LoadPaperService loadPaperService;
    private final CustomerRepository customerRepository;

    public OnePaperBoyPerStreetStrategy(PaperBoyRoundService paperBoyRoundService,
                                        LoadPaperService loadPaperService,
                                        CustomerRepository customerRepository) {
        this.paperBoyRoundService = paperBoyRoundService;
        this.loadPaperService = loadPaperService;
        this.customerRepository = customerRepository;
    }

    @Override
    public void deliverPapers(DeliverPapersCommand command) {
        final OnePaperBoyPerStreetConsumer consumer = new OnePaperBoyPerStreetConsumer(command.getPaperBoys());
        command.getStreets()
                .stream()
                .map(customerRepository::getAll)
                .forEach(consumer);
    }


    private class OnePaperBoyPerStreetConsumer implements Consumer<Set<Customer>> {
        private final Iterator<PaperBoy> iterator;

        public OnePaperBoyPerStreetConsumer(Set<PaperBoy> paperBoys) {
            this.iterator = Iterables.cycle(paperBoys).iterator();
        }

        @Override
        public void accept(Set<Customer> customers) {
            final PaperBoy paperBoy = iterator.next();
            loadPaperService.loadPapers(paperBoy, customers.size());
            paperBoyRoundService.deliverPapers(paperBoy, customers);
        }
    }
}

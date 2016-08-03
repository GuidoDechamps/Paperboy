package be.solid.paperboy;


import be.solid.paperboy.model.PaperBoyRepository;
import be.solid.paperboy.service.DeliverPapersCommand;
import be.solid.paperboy.service.DeliveryStrategy;

import java.util.Set;

class ApplicationService implements PaperDeliveryApplicationService {
    private final DeliveryStrategy deliveryStrategy;
    private final PaperBoyRepository paperBoyRepository;

    ApplicationService(
            DeliveryStrategy deliveryStrategy,
            PaperBoyRepository paperBoyRepository) {
        this.deliveryStrategy = deliveryStrategy;
        this.paperBoyRepository = paperBoyRepository;
    }

    @Override
    public void deliverPapers(Set<String> streets) {
        final DeliverPapersCommand c = buildCommand(streets);
        deliveryStrategy.deliverPapers(c);
    }

    private DeliverPapersCommand buildCommand(Set<String> streets) {
        return DeliverPapersCommand.builder()
                .withStreets(streets)
                .withPaperBoys(paperBoyRepository.getAll())
                .build();
    }


}

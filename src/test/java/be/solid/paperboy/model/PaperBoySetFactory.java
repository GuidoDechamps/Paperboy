package be.solid.paperboy.model;

import com.google.common.collect.ImmutableSet;


public class PaperBoySetFactory {
    private final PaperBoyFactory paperBoyFactory;

    public PaperBoySetFactory(PaperBoyFactory paperBoyFactory) {
        this.paperBoyFactory = paperBoyFactory;
    }


    public ImmutableSet<PaperBoy> buildPaperBoys() {
        return ImmutableSet.<PaperBoy>builder()
                .add(createPaperBoy())
                .add(createPaperBoy())
                .add(createPaperBoy())
                .build();
    }

    private PaperBoy createPaperBoy() {
        return paperBoyFactory.createPaperBoy();
    }


}

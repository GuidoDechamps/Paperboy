package be.solid.paperboy.service;

import be.solid.paperboy.model.Paper;
import be.solid.paperboy.model.PaperBoy;
import be.solid.paperboy.model.PaperFactory;
import com.google.common.collect.Lists;

import java.time.LocalDate;
import java.util.List;

public class LoadPaperService {
    private final PaperFactory paperFactory;

    public LoadPaperService(PaperFactory paperFactory) {
        this.paperFactory = paperFactory;
    }


    public void loadPapers(PaperBoy paperBoy, int nrOfPapersNeeded) {
        final List<Paper> papers = paperFactory.printPapers(nrOfPapersNeeded, LocalDate.now());
        initPaperBoyPapers(paperBoy);
        paperBoy.getPapers().addAll(papers);
    }

    private void initPaperBoyPapers(PaperBoy paperBoy) {
        if (paperBoy.getPapers() == null)
            paperBoy.setPapers(Lists.newArrayList());
    }
}

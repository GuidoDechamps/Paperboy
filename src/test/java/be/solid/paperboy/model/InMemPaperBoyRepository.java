package be.solid.paperboy.model;

import com.google.common.collect.ImmutableSet;

import java.util.Set;

import static com.google.common.base.Preconditions.checkNotNull;

public class InMemPaperBoyRepository implements PaperBoyRepository {


    private Set<PaperBoy> paperBoys = null;

    @Override
    public Set<PaperBoy> getAll() {
        if (paperBoys == null)
            return throwNotInitializedError();
        else
            return paperBoys;
    }

    public void setPaperBoys(ImmutableSet<PaperBoy> paperBoys) {
        checkNotNull(paperBoys);
        if (this.paperBoys == null)
            this.paperBoys = paperBoys;
        else
            throwAlreadyInitializedError();
    }

    private Set<PaperBoy> throwNotInitializedError() {
        throw new RuntimeException("InMemPaperBoyRepository was not initialized");
    }

    private void throwAlreadyInitializedError() {
        throw new RuntimeException("Paperboys were already initialised");
    }
}

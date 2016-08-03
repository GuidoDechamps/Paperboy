package be.solid.paperboy.model;

import com.google.common.collect.ImmutableSet;

public class PaperBoyFactory {
    private final WalletFactory walletFactory;

    public PaperBoyFactory(WalletFactory walletFactory) {
        this.walletFactory = walletFactory;
    }

    public PaperBoy createPaperBoy() {
        final PaperBoy paperBoy = new PaperBoy();
        final Wallet wallet = walletFactory.createWallet();
        paperBoy.setWallet(wallet);
        return paperBoy;
    }

    public ImmutableSet<PaperBoy> createPaperBoys(int nrOfPaperBoys) {
        final ImmutableSet.Builder<PaperBoy> builder = ImmutableSet.builder();
        for (int i = 0; i < nrOfPaperBoys; i++) {
            final PaperBoy paperBoy = createPaperBoy();
            builder.add(paperBoy);
        }
        return builder.build();
    }

}

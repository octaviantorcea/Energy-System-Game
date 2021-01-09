package strategies;

import database.ProducerDatabase;
import entities.Producer;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import static utils.Constants.FIRST;

public final class PriceStrategy extends AbstractStrategy{
    public PriceStrategy(final int energyNeededKW, final ProducerDatabase producerDatabase) {
        super(energyNeededKW, producerDatabase);
    }

    @Override
    public List<Producer> getNecessaryProducers() {
        List<Producer> necessaryProducers = new ArrayList<>();
        int energyStillNeeded = energyNeededKW;
        List<Producer> availableProducers = new ArrayList<>(producerDatabase.getProducers());

        Comparator<Producer> comparePriceQuantity = Comparator.comparing(Producer::getPriceKW)
                .thenComparing(Producer::getEnergyPerDistributor).thenComparing(Producer::getId);

        availableProducers.sort(comparePriceQuantity);

        getNextAvailableProducer(energyStillNeeded, necessaryProducers, availableProducers);

        return necessaryProducers;
    }
}

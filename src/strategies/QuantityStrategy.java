package strategies;

import database.ProducerDatabase;
import entities.Producer;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public final class QuantityStrategy extends AbstractStrategy {
    public QuantityStrategy(final int energyNeededKW, final ProducerDatabase producerDatabase) {
        super(energyNeededKW, producerDatabase);
    }

    @Override
    public List<Producer> getNecessaryProducers() {
        List<Producer> necessaryProducers = new ArrayList<>();
        int energyStillNeeded = energyNeededKW;
        List<Producer> availableProducers = new ArrayList<>(producerDatabase.getProducers());

        Comparator<Producer> compareQuantity = Comparator
                .comparing(Producer::getEnergyPerDistributor, Comparator.reverseOrder())
                .thenComparing(Producer::getId);

        availableProducers.sort(compareQuantity);

        getNextAvailableProducer(energyStillNeeded, necessaryProducers, availableProducers);

        return necessaryProducers;
    }
}

package strategies;

import database.ProducerDatabase;
import entities.Producer;

import java.util.ArrayList;
import java.util.List;

import static utils.Constants.FIRST;

public final class GreenStrategy extends AbstractStrategy{
    public GreenStrategy(final int energyNeededKW, final ProducerDatabase producerDatabase) {
        super(energyNeededKW, producerDatabase);
    }

    @Override
    public List<Producer> getNecessaryProducers() {
        List<Producer> necessaryProducers = new ArrayList<>();
        int energyStillNeeded = energyNeededKW;
        List<Producer> availableProducers = new ArrayList<>(producerDatabase.getGreenProducers());

        while (energyStillNeeded > 0 && !availableProducers.isEmpty()) {
            Producer addedProducer = availableProducers.get(FIRST);

            if (addedProducer.canHaveMoreDistributors()) {
                necessaryProducers.add(addedProducer);
                energyStillNeeded -= addedProducer.getEnergyPerDistributor();
            }

            availableProducers.remove(FIRST);
        }

        availableProducers = new ArrayList<>(producerDatabase.getNonGreenProducers());

        getNextAvailableProducer(energyStillNeeded, necessaryProducers, availableProducers);

        return necessaryProducers;
    }
}

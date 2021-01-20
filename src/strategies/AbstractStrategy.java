package strategies;

import database.ProducerDatabase;
import entities.Producer;

import java.util.List;

import static utils.Constants.FIRST;

/**
 * Provides the skeleton for the strategies the distributors use
 */
public abstract class AbstractStrategy implements ChooseProducerStrategy {
    /**
     * the amount of energy a distributors needs every month
     */
    protected int energyNeededKW;
    /**
     * the producer database from where a distributor will choose the producers
     */
    protected ProducerDatabase producerDatabase;

    protected AbstractStrategy(final int energyNeededKW, final ProducerDatabase producerDatabase) {
        this.energyNeededKW = energyNeededKW;
        this.producerDatabase = producerDatabase;
    }

    protected final void getNextAvailableProducer(int energyStillNeeded,
                                            final List<Producer> necessaryProducers,
                                            final List<Producer> availableProducers) {
        while (energyStillNeeded > 0) {
            Producer addedProducer = availableProducers.get(FIRST);

            if (addedProducer.canHaveMoreDistributors()) {
                necessaryProducers.add(addedProducer);
                energyStillNeeded -= addedProducer.getEnergyPerDistributor();
            }

            availableProducers.remove(FIRST);
        }
    }

    @Override
    public abstract List<Producer> getNecessaryProducers();
}

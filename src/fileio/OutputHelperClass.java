package fileio;

import database.ConsumerDatabase;
import database.DistributorDatabase;
import database.ProducerDatabase;
import entities.Consumer;
import entities.Distributor;
import entities.Producer;

import java.util.ArrayList;
import java.util.List;

/**
 * Class that contains all the necessary data for output
 */
public final class OutputHelperClass {
    /**
     * List of all consumers
     */
    private List<Consumer> consumers;
    /**
     * List of special type of distributors
     * @see DistributorOutputClass
     */
    private List<DistributorOutputClass> distributors = new ArrayList<>();
    /**
     * List of all energyProducers
     */
    private List<Producer> energyProducers;

    public OutputHelperClass(final ConsumerDatabase consumerDatabase,
                             final DistributorDatabase distributorDatabase,
                             final ProducerDatabase producerDatabase) {
        consumers = consumerDatabase.getConsumers();

        for (Distributor distributor : distributorDatabase.getDistributors()) {
            distributors.add(new DistributorOutputClass(distributor));
        }

        energyProducers = producerDatabase.getProducers();
    }

    public List<Consumer> getConsumers() {
        return consumers;
    }

    public void setConsumers(List<Consumer> consumers) {
        this.consumers = consumers;
    }

    public List<DistributorOutputClass> getDistributors() {
        return distributors;
    }

    public void setDistributors(List<DistributorOutputClass> distributors) {
        this.distributors = distributors;
    }

    public List<Producer> getEnergyProducers() {
        return energyProducers;
    }

    public void setEnergyProducers(List<Producer> energyProducers) {
        this.energyProducers = energyProducers;
    }
}

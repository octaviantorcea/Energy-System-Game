package fileio;

import java.util.List;

/**
 * Class that contains all the changes for the start of a round
 */
public final class MonthlyUpdates {
    /**
     * List of new consumers added
     */
    private List<ConsumerInput> newConsumers;
    /**
     * List of changes for distributors
     */
    private List<DistributorChanges> distributorChanges;
    /**
     * List of changes for producers
     */
    private List<ProducerChanges> producerChanges;

    public List<ConsumerInput> getNewConsumers() {
        return newConsumers;
    }

    public void setNewConsumers(final List<ConsumerInput> newConsumers) {
        this.newConsumers = newConsumers;
    }

    public List<DistributorChanges> getDistributorChanges() {
        return distributorChanges;
    }

    public void setDistributorChanges(final List<DistributorChanges> distributorChanges) {
        this.distributorChanges = distributorChanges;
    }

    public List<ProducerChanges> getProducerChanges() {
        return producerChanges;
    }

    public void setProducerChanges(final List<ProducerChanges> producerChanges) {
        this.producerChanges = producerChanges;
    }

    //for debugging
    @Override
    public String toString() {
        return "\nnewConsumers=" + newConsumers +
                "\ndistributorChanges=" + distributorChanges +
                "\nproducerChanges=" + producerChanges +
                "}\n";
    }
}

package fileio;

import database.ConsumerDatabase;
import database.DistributorDatabase;
import database.ProducerDatabase;
import entities.Consumer;
import entities.Distributor;
import entities.Producer;

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

    /**
     * applies all cost changes
     * @param distributorDB distributor database
     * @see Distributor#modifyInfCost
     */
    public void changeInfCosts(final DistributorDatabase distributorDB) {
        distributorChanges.forEach(costsChange -> {
            Distributor modifiedDistr = distributorDB.getDistributors().get(costsChange.getId());
            modifiedDistr.modifyInfCost(costsChange);
        });
    }

    public void addNewConsumers(final ConsumerDatabase consumerDB) {
        newConsumers.forEach(newConsumer ->
                consumerDB.getConsumers().add(new Consumer(newConsumer)));
    }

    public void modifyProducers(final ProducerDatabase producerDB,
                                final DistributorDatabase distributorDB) {
        producerChanges.forEach(producerChange -> {
            Producer modifiedProducer = producerDB.getProducers().get(producerChange.getId());
            modifiedProducer.modifyProducer(producerChange, distributorDB);
        });
    }

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
}

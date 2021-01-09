package fileio;

import java.util.List;

/**
 * Class that contains all the initial data parsed from the input file
 */
public final class InitialData {
    /**
     * List of initial consumers
     */
    private List<ConsumerInput> consumers;
    /**
     * List of initial distributors
     */
    private List<DistributorInput> distributors;
    /**
     * List of initial producers
     */
    private List<ProducerInput> producers;

    public List<ConsumerInput> getConsumers() {
        return consumers;
    }

    public void setConsumers(final List<ConsumerInput> consumers) {
        this.consumers = consumers;
    }

    public List<DistributorInput> getDistributors() {
        return distributors;
    }

    public void setDistributors(final List<DistributorInput> distributors) {
        this.distributors = distributors;
    }

    public List<ProducerInput> getProducers() {
        return producers;
    }

    public void setProducers(final List<ProducerInput> producers) {
        this.producers = producers;
    }
}

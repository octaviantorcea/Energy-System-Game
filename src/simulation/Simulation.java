package simulation;

import database.ConsumerDatabase;
import database.DistributorDatabase;
import database.ProducerDatabase;
import entities.Consumer;
import entities.Distributor;
import entities.Producer;
import fileio.InitialData;
import fileio.InputDataLoader;

public final class Simulation {
    private static Simulation instance = null;

    private Simulation() { }

    /**
     * executes the whole simulation
     * @param allData data from input file
     * @param consumerDB consumer database
     * @param distributorDB distributor database
     * @param producerDB producer database
     */
    public void run(final InputDataLoader allData,
                    final ConsumerDatabase consumerDB,
                    final DistributorDatabase distributorDB,
                    final ProducerDatabase producerDB) {
        initialization(allData, consumerDB, distributorDB, producerDB);

    }

    /**
     * constructs the databases with the initial consumers and distributors
     * @param allData data from input file
     * @param consumerDB consumer database
     * @param distributorDB distributor database
     * @param producerDB producer database
     */
    private void initialization(final InputDataLoader allData,
                                final ConsumerDatabase consumerDB,
                                final DistributorDatabase distributorDB,
                                final ProducerDatabase producerDB) {
        InitialData initialData = allData.getInitialData();

        initialData.getConsumers().forEach(consumerInput ->
                consumerDB.getConsumers().add(new Consumer(consumerInput)));

        initialData.getDistributors().forEach(distributorInput ->
                distributorDB.getDistributors().add(new Distributor(distributorInput)));

        initialData.getProducers().forEach(producerInput ->
                producerDB.getProducers().add(new Producer(producerInput)));
    }

    private void simulateInitRound(final ConsumerDatabase consumerDB,
                                    final DistributorDatabase distributorDB,
                                    final ProducerDatabase producerDB) {

    }

    /**
     * @return an instance of the simulation
     */
    public static Simulation getSimulationInstance() {
        if (instance == null) {
            instance = new Simulation();
        }

        return instance;
    }
}

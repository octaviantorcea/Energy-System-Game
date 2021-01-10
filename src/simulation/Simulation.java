package simulation;

import database.ConsumerDatabase;
import database.DistributorDatabase;
import database.ProducerDatabase;
import entities.Consumer;
import entities.Distributor;
import entities.MonthlyStats;
import entities.Producer;
import fileio.InitialData;
import fileio.InputDataLoader;
import fileio.MonthlyUpdates;

import java.util.ArrayList;
import java.util.List;

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
        simulateInitRound(consumerDB, distributorDB, producerDB);

        //mockup
        for (MonthlyUpdates monthlyUpdates : allData.getMonthlyUpdates()) {
            simulateInitRound(consumerDB, distributorDB, producerDB);

            //need to delete below this; it's just for debugging
            producerDB.getProducers().forEach(producer -> {
                List<MonthlyStats> monthlyStats = producer.getMonthlyStats();
                List<Distributor> monthlyDistributors = producer.getSubscribedDistributors();
                List<Integer> ids = new ArrayList<>();
                monthlyDistributors.forEach(distributor -> ids.add(distributor.getId()));

                monthlyStats.add(new MonthlyStats(69, ids));
                producer.getSubscribedDistributors().clear();
            });
            producerDB.getProducers().forEach(producer -> producer.setNrOfSubbedDistributors(0));
        }
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

        initialData.getProducers().forEach(producerInput -> {
            Producer newProducer = new Producer(producerInput);
            producerDB.getProducers().add(newProducer);

            if (producerInput.getEnergyType().isRenewable()) {
                producerDB.getGreenProducers().add(newProducer);
            } else {
                producerDB.getNonGreenProducers().add(newProducer);
            }
        });

        distributorDB.getDistributors().forEach(distributor -> distributor.setStrategy(producerDB));
    }

    private void simulateInitRound(final ConsumerDatabase consumerDB,
                                    final DistributorDatabase distributorDB,
                                    final ProducerDatabase producerDB) {
        distributorDB.chooseProducers(producerDB);
        distributorDB.computeProductionCosts();
        distributorDB.computeContractPrices();
        consumerDB.addAllIncomes();
        consumerDB.signContracts(distributorDB);
        consumerDB.payContracts();
        distributorDB.payAllFees();
        consumerDB.verifyBankruptcies();
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

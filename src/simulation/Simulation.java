package simulation;

import database.ConsumerDatabase;
import database.DistributorDatabase;
import database.ProducerDatabase;
import entities.Consumer;
import entities.Distributor;
import entities.Producer;
import fileio.InitialData;
import fileio.InputDataLoader;
import fileio.MonthlyUpdates;

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

        for (int i = 1; i <= allData.getNumberOfTurns(); i++) {
            simulateNormalRound(consumerDB, distributorDB, producerDB,
                    allData.getMonthlyUpdates().get(i - 1), i);
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
        distributorDB.declareAllBankruptcies();
    }

    private void simulateNormalRound(final ConsumerDatabase consumerDB,
                                     final DistributorDatabase distributorDB,
                                     final ProducerDatabase producerDB,
                                     final MonthlyUpdates monthlyUpdates,
                                     final int month) {
        monthlyUpdates.changeInfCosts(distributorDB);
        monthlyUpdates.addNewConsumers(consumerDB);
        distributorDB.computeContractPrices();
        consumerDB.addAllIncomes();
        consumerDB.signContracts(distributorDB);
        consumerDB.payContracts();
        distributorDB.payAllFees();
        //<------------???why not here???-----------------------producerDB.saveMonthlyStats(month);
        consumerDB.verifyBankruptcies();
        distributorDB.declareAllBankruptcies();
        monthlyUpdates.modifyProducers(producerDB, distributorDB);
        ///////////////////////////////////////////////////////////distributorDB.chooseProducers(producerDB);
        distributorDB.chooseNewProducers(producerDB);
        distributorDB.getNeedNewProducers().clear();
        distributorDB.computeProductionCosts();
        producerDB.saveMonthlyStats(month);
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

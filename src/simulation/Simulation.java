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
        simulateInitRound(consumerDB, distributorDB);

        for (int i = 1; i <= allData.getNumberOfTurns(); i++) {
            simulateNormalRound(consumerDB, distributorDB, producerDB,
                    allData.getMonthlyUpdates().get(i - 1), i);

            // if all distributors are bankrupt, the simulation stops
            if (distributorDB.allBankrupt()) {
                break;
            }
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

        // sets the strategy for every distributor
        distributorDB.getDistributors().forEach(distributor -> distributor.setStrategy(producerDB));
    }

    /**
     * simulates the initial round (where there are no monthly changes)
     * @see Simulation#baseOperations
     */
    private void simulateInitRound(final ConsumerDatabase consumerDB,
                                    final DistributorDatabase distributorDB) {
        // 1. each distributor chooses the necessary producers
        distributorDB.chooseInitialProducers();
        // 2. each distributor computes the production cost
        distributorDB.computeProductionCosts();
        // 3. applies base operations
        baseOperations(consumerDB, distributorDB);
    }

    /**
     * simulates a normal round (where there are changes at the start of the round)
     * @see Simulation#baseOperations
     */
    private void simulateNormalRound(final ConsumerDatabase consumerDB,
                                     final DistributorDatabase distributorDB,
                                     final ProducerDatabase producerDB,
                                     final MonthlyUpdates monthlyUpdates,
                                     final int month) {
        // 1. infrastructure cost changes
        monthlyUpdates.changeInfCosts(distributorDB);
        // 2. adds new consumers
        monthlyUpdates.addNewConsumers(consumerDB);
        // 3. applies base operations
        baseOperations(consumerDB, distributorDB);
        // 4. applies changes for producers
        monthlyUpdates.modifyProducers(producerDB, distributorDB);
        // 5. distributors choose new producers (if necessary)
        distributorDB.chooseProducers();
        // 6. clear the list with all the distributors that needed new producers
        distributorDB.getNeedNewProducers().clear();
        // 7. each distributor computes the new production cost
        distributorDB.computeProductionCosts();
        // 8. producers save the monthly stats
        producerDB.saveMonthlyStats(month);
    }

    /**
     * operations that are common for both an initial round and for a normal round
     */
    private void baseOperations(final ConsumerDatabase consumerDB,
                                final DistributorDatabase distributorDB) {
        // 1. each distributor updates contract prices
        distributorDB.computeContractPrices();
        // 2. add income for each consumer
        consumerDB.addAllIncomes();
        // 3. sign contract (where necessary) for consumers
        consumerDB.signContracts(distributorDB);
        // 4. each consumer pays the contract
        consumerDB.payContracts();
        // 5. each distributor pays the fee
        distributorDB.payAllFees();
        // 6. verifies for bankruptcy every consumer
        consumerDB.verifyBankruptcies();
        // 7. declares bankruptcy (where necessary) for distributors
        distributorDB.declareAllBankruptcies();
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

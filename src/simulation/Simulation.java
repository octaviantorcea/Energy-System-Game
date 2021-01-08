package simulation;

import database.ConsumerDatabase;
import database.DistributorDatabase;
import database.ProducerDatabase;
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

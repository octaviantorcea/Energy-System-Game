import com.fasterxml.jackson.databind.ObjectMapper;
import database.ConsumerDatabase;
import database.DistributorDatabase;
import database.ProducerDatabase;
import fileio.InputDataLoader;
import simulation.Simulation;

import java.io.File;

/**
 * Entry point to the simulation
 */
public final class Main {
    /**
     * for coding style
     */
    private Main() { }

    /**
     * Main function which reads the input file and starts simulation
     *
     * @param args input and output files
     * @throws Exception might error when reading/writing/opening files, parsing JSON
     */
    public static void main(final String[] args) throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        InputDataLoader allData = objectMapper.readValue(new File(args[0]), InputDataLoader.class);

        Simulation game = Simulation.getSimulationInstance();
        ConsumerDatabase consumerDatabase = new ConsumerDatabase();
        DistributorDatabase distributorDatabase = new DistributorDatabase();
        ProducerDatabase producerDatabase = new ProducerDatabase();

        game.run(allData, consumerDatabase, distributorDatabase, producerDatabase);

        // for debugging
        System.out.println(consumerDatabase);
        System.out.println(distributorDatabase);
        System.out.println(producerDatabase);
    }
}

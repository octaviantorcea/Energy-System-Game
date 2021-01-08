import com.fasterxml.jackson.databind.ObjectMapper;
import fileio.InputDataLoader;

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

        // for debugging
        System.out.println(allData);
    }
}

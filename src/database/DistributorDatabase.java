package database;

import entities.Distributor;

import java.util.ArrayList;
import java.util.List;

/**
 * Class that contains all distributors <br>
 * Also contains methods that can be applied to all distributors <br>
 */
public final class DistributorDatabase {
    /**
     * List with all distributors
     */
    private final List<Distributor> distributors = new ArrayList<>();

    public List<Distributor> getDistributors() {
        return distributors;
    }
}

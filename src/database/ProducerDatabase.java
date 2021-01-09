package database;

import entities.Producer;

import java.util.ArrayList;
import java.util.List;

/**
 * Class that contains all producers <br>
 * Also contains methods that can be applied to all producers <br>
 */
public final class ProducerDatabase {
    /**
     * List with all producers
     */
    private final List<Producer> producers = new ArrayList<>();

    public List<Producer> getProducers() {
        return producers;
    }

    //for debugging
    @Override
    public String toString() {
        return "ProducerDatabase{" +
                "producers=" + producers +
                '}';
    }
}

package database;

import entities.Consumer;

import java.util.ArrayList;
import java.util.List;

/**
 * Class that contains all consumers <br>
 * Also contains methods that can be applied to all consumers <br>
 */
public final class ConsumerDatabase {
    /**
     * List with all consumers
     */
    private final List<Consumer> consumers = new ArrayList<>();

    public List<Consumer> getConsumers() {
        return consumers;
    }
}

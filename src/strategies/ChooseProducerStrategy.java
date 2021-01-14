package strategies;

import entities.Producer;

import java.util.List;

/**
 * Common interface for the three strategies that distributors use to choose
 * their producers
 */
public interface ChooseProducerStrategy {
    /**
     * @return a list with producers that a distributor had chosen based on a
     * particular strategy
     */
    List<Producer> getNecessaryProducers();
}

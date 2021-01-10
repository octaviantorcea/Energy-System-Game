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

    /**
     * Applies addIncome method for all consumers
     * @see Consumer#addIncome()
     */
    public void addAllIncomes() {
        consumers.forEach(Consumer::addIncome);
    }

    /**
     * Applies signContract method for all consumers
     * @param distributorDB distributor database from where it will get the
     *                      best contract
     * @see Consumer#signContract
     */
    public void signContracts(final DistributorDatabase distributorDB) {
        consumers.forEach(consumer -> consumer.signContract(distributorDB));
    }

    /**
     * Applies payContract method for all consumers
     * @see Consumer#payContract()
     */
    public void payContracts() {
        consumers.forEach(Consumer::payContract);
    }

    /**
     * Applies verifyBankruptcy method for all consumers
     * @see Consumer#verifyBankruptcy()
     */
    public void verifyBankruptcies() {
        consumers.forEach(Consumer::verifyBankruptcy);
    }

    public List<Consumer> getConsumers() {
        return consumers;
    }

    //for debugging
    @Override
    public String toString() {
        return "ConsumerDatabase{" +
                "consumers=" + consumers +
                '}';
    }
}

package entities;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * Class that contains data about a contract and specific methods
 */
public final class Contract {
    private int consumerId;
    private final int price;
    private int remainedContractMonths;
    /**
     * the consumer to whom the contract belongs
     */
    @JsonIgnore
    private final Consumer consumer;
    /**
     * the distributor to whom the contract belongs
     */
    @JsonIgnore
    private final Distributor distributor;

    public Contract(final Consumer consumer, final Distributor distributor) {
        this.consumer = consumer;
        this.distributor = distributor;
        this.price = distributor.getContractCost();
        this.remainedContractMonths = distributor.getContractLength();
        this.consumerId = consumer.getId();
    }

    /**
     * subtract the contract price + penalty from consumers budget
     */
    public void payOneRound() {
        remainedContractMonths--;
        consumer.setBudget(consumer.getBudget() - price - consumer.getPenalty());
        distributor.setBudget(distributor.getBudget() + price);

        /*
         * remove the penalty status
         */
        if (consumer.getHasPenalty()) {
            consumer.getPenaltyDistributor().setBudget(distributor.getBudget()
                    + consumer.getPenalty());
            consumer.setPenalty(0);
            consumer.setHasPenalty(false);
        }
    }

    public int getConsumerId() {
        return consumerId;
    }

    public void setConsumerId(int consumerId) {
        this.consumerId = consumerId;
    }

    public int getPrice() {
        return price;
    }

    public int getRemainedContractMonths() {
        return remainedContractMonths;
    }

    public void setRemainedContractMonths(int remainedContractMonths) {
        this.remainedContractMonths = remainedContractMonths;
    }

    public Consumer getConsumer() {
        return consumer;
    }

    public Distributor getDistributor() {
        return distributor;
    }
}

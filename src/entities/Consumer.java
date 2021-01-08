package entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import fileio.ConsumerInput;

/**
 * Class that contains data about a consumer and specific methods
 */
public final class Consumer {
    private int id;
    private boolean isBankrupt;
    private int budget;
    @JsonIgnore
    private final int monthlyIncome;
    /**
     * the contract a consumer has
     */
    @JsonIgnore
    private Contract contract;
    /**
     * if the consumer has a contract with more than 0 months remaining or not
     */
    @JsonIgnore
    private boolean hasContract;
    /**
     * penalty from previous month
     */
    @JsonIgnore
    private int penalty;
    /**
     * if the consumer has a penalty or not
     */
    @JsonIgnore
    private boolean hasPenalty;
    /**
     * the distributor to whom the penalty belongs
     */
    @JsonIgnore
    private Distributor penaltyDistributor;

    public Consumer(final ConsumerInput consumerInput) {
        this.id = consumerInput.getId();
        this.budget = consumerInput.getInitialBudget();
        this.monthlyIncome = consumerInput.getMonthlyIncome();
        this.isBankrupt = false;
        this.hasPenalty = false;
        this.hasContract = false;
        this.penalty = 0;
        this.penaltyDistributor = null;
    }

    public int getId() {
        return id;
    }

    public void setId(final int id) {
        this.id = id;
    }

    public boolean getIsBankrupt() {
        return isBankrupt;
    }

    public void setIsBankrupt(final boolean bankrupt) {
        isBankrupt = bankrupt;
    }

    public int getBudget() {
        return budget;
    }

    public void setBudget(final int budget) {
        this.budget = budget;
    }

    public void setHasContract(final boolean hasContract) {
        this.hasContract = hasContract;
    }

    public int getPenalty() {
        return penalty;
    }

    public void setPenalty(final int penalty) {
        this.penalty = penalty;
    }

    public boolean getHasPenalty() {
        return hasPenalty;
    }

    public void setHasPenalty(final boolean hasPenalty) {
        this.hasPenalty = hasPenalty;
    }

    public Distributor getPenaltyDistributor() {
        return penaltyDistributor;
    }
}

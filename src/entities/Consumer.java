package entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import database.DistributorDatabase;
import fileio.ConsumerInput;

import static utils.Constants.PENALTY_RATE;

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

    /**
     * adds monthly income to the total budget
     */
    public void addIncome() {
        if (!this.isBankrupt) {
            budget += monthlyIncome;
        }
    }

    /**
     * assigns the best contract to this consumer (only if it doesn't have a contract already)
     * @param distributorDB distributor database from where it will search for the best contract
     * @see Contract
     */
    public void signContract(final DistributorDatabase distributorDB) {
        if (!this.isBankrupt && !hasContract) {
            if (contract != null) {
                /*
                 * if the Consumer previously had a contract, remove that contract
                 */
                contract.getDistributor().getContracts().remove(this);
            }

            Distributor bestDistributor = distributorDB.findBestContract();
            this.contract = new Contract(this, bestDistributor);
            bestDistributor.getContracts().put(this, this.contract);
            hasContract = true;
        }
    }

    public void payContract() {
        if (!this.isBankrupt) {
            if (this.hasPenalty) { // if it has penalty
                if (this.penaltyDistributor == contract.getDistributor()) { // and has the same dis
                    if (contract.getPrice() + penalty > budget) { // and can't pay
                        isBankrupt = true;
                        return;
                    } else { // can pay
                        contract.payOneRound();
                    }
                } else { // and has another distributor
                    if (contract.getPrice() + penalty > budget) { // and can't pay
                        if (penalty > budget) { // and can't even pay only the penalty
                            isBankrupt = true;
                            return;
                        } else { // can pay at least the penalty
                            budget -= penalty;
                            penaltyDistributor.setBudget(penaltyDistributor.getBudget() + penalty);
                            penalty = (int) Math.round(Math.floor(PENALTY_RATE
                                                                    * contract.getPrice()));
                            penaltyDistributor = contract.getDistributor();
                            contract.setRemainedContractMonths(contract.getRemainedContractMonths()
                                                                    - 1);
                        }
                    } else { // can pay
                        contract.payOneRound();
                    }
                }
            } else { // doesn't have penalty
                if (contract.getPrice() > budget) { // and can't pay base contract
                    hasPenalty = true;
                    penalty = (int) Math.round(Math.floor(PENALTY_RATE * contract.getPrice()));
                    penaltyDistributor = contract.getDistributor();
                    contract.setRemainedContractMonths(contract.getRemainedContractMonths() - 1);
                } else {
                    contract.payOneRound();
                }
            }

            if (contract.getRemainedContractMonths() == 0) {
                hasContract = false;
            }
        }
    }

    /**
     * if the consumer became bankrupt and still has a contract, remove that contract
     */
    public void verifyBankruptcy() {
        if (isBankrupt && hasContract) {
            contract.getDistributor().getContracts().remove(this);
            hasContract = false;
        }
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

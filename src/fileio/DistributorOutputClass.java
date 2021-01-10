package fileio;

import entities.Contract;
import entities.Distributor;
import strategies.EnergyChoiceStrategyType;

import java.util.Collection;

/**
 * Special distributor class that contains only the necessary info for output
 */
public final class DistributorOutputClass {
    private int id;
    private int energyNeededKW;
    private int contractCost;
    private int budget;
    private EnergyChoiceStrategyType producerStrategy;
    private boolean isBankrupt;
    /**
     * a collection of contracts
     */
    private Collection<Contract> contracts;

    public DistributorOutputClass(final Distributor distributor) {
        this.id = distributor.getId();
        this.energyNeededKW = distributor.getEnergyNeededKW();
        this.contractCost = distributor.getContractCost();
        this.budget = distributor.getBudget();
        this.producerStrategy = distributor.getProducerStrategy();
        this.isBankrupt = distributor.isBankrupt();
        this.contracts = distributor.getContracts().values();
    }

    public int getId() {
        return id;
    }

    public void setId(final int id) {
        this.id = id;
    }

    public int getEnergyNeededKW() {
        return energyNeededKW;
    }

    public void setEnergyNeededKW(int energyNeededKW) {
        this.energyNeededKW = energyNeededKW;
    }

    public int getContractCost() {
        return contractCost;
    }

    public void setContractCost(int contractCost) {
        this.contractCost = contractCost;
    }

    public EnergyChoiceStrategyType getProducerStrategy() {
        return producerStrategy;
    }

    public void setProducerStrategy(EnergyChoiceStrategyType producerStrategy) {
        this.producerStrategy = producerStrategy;
    }

    public int getBudget() {
        return budget;
    }

    public void setBudget(final int budget) {
        this.budget = budget;
    }

    public boolean getIsBankrupt() {
        return isBankrupt;
    }

    public void setIsBankrupt(final boolean bankrupt) {
        isBankrupt = bankrupt;
    }

    public Collection<Contract> getContracts() {
        return contracts;
    }

    public void setContracts(final Collection<Contract> contracts) {
        this.contracts = contracts;
    }
}

package entities;

import database.ProducerDatabase;
import fileio.DistributorInput;
import strategies.ChooseProducerStrategy;
import strategies.EnergyChoiceStrategyType;
import strategies.StrategyFactory;

import java.util.*;

import static utils.Constants.PROFIT_RATE;

/**
 * Class that contains data about a distributor and specific methods
 */
@SuppressWarnings("deprecation")
public final class Distributor implements Observer {
    private int id;
    private int energyNeededKW;
    private int contractCost;
    private int budget;
    private EnergyChoiceStrategyType producerStrategy;
    private boolean isBankrupt;
    private Map<Consumer, Contract> contracts = new LinkedHashMap<>();
    private int contractLength;
    private int infrastructureCost;
    private int productionCost;
    private boolean needNewProducers;
    private ChooseProducerStrategy strategy;
    private List<Producer> energyProducers;

    public Distributor(final DistributorInput distributorInput) {
        this.id = distributorInput.getId();
        this.budget = distributorInput.getInitialBudget();
        this.contractLength = distributorInput.getContractLength();
        this.infrastructureCost = distributorInput.getInitialInfrastructureCost();
        this.energyNeededKW = distributorInput.getEnergyNeededKW();
        this.producerStrategy = distributorInput.getProducerStrategy();
        this.needNewProducers = true;
        this.isBankrupt = false;
    }

    public void setStrategy(ProducerDatabase producerDatabase) {
        this.strategy = StrategyFactory.getStrategyFactoryInstance()
                .createStrategy(producerStrategy, energyNeededKW, producerDatabase);
    }

    public void chooseProducers(ProducerDatabase producerDatabase) {
        if (this.needNewProducers && !this.isBankrupt) {
            energyProducers = this.strategy.getNecessaryProducers(); // puts every producer in list
            energyProducers.forEach(producer -> {
                producer.addObserver(this); // add distributor in ObserverList of every producer
                producer.setNrOfSubbedDistributors(producer.getNrOfSubbedDistributors() + 1);
                producer.getSubscribedDistributors().add(this);
            });
        }
    }

    public void computeProductionCost() {
        if (!this.isBankrupt) {
            double cost = 0;

            for (Producer producer : energyProducers) {
                cost += producer.getEnergyPerDistributor() * producer.getPriceKW();
            }

            productionCost = (int) Math.round(Math.floor(cost / 10));
        }
    }

    public void computeContractCost() {
        if (!this.isBankrupt) {
            int nrClients = contracts.size();
            int profit = (int) Math.round(Math.floor(productionCost * PROFIT_RATE));

            if (nrClients == 0) {
                contractCost = infrastructureCost + productionCost + profit;
            } else {
                contractCost = (int) Math.round(Math.floor((double) infrastructureCost / nrClients)
                        + productionCost + profit);
            }
        }
    }

    /**
     * subtract the monthly fee for a distributor;
     * <p>
     * if it cannot pay the fee, then it becomes bankrupt
     */
    public void payFees() {
        if (!isBankrupt) {
            int fee = computeFees();
            budget -= fee;

            if (budget < 0) {
                isBankrupt = true;
            }
        }
    }

    /**
     * removes all the contracts this distributor has
     */
    public void declareBankruptcy() {
        if (isBankrupt && !contracts.isEmpty()) {
            contracts.values().forEach(contract -> contract.getConsumer().setHasContract(false));

            contracts.clear();
        }
    }

    private int computeFees() {
        int nrClients = contracts.size();
        return infrastructureCost + productionCost * nrClients;
    }

    public int getId() {
        return id;
    }

    public int getContractCost() {
        return contractCost;
    }

    public int getBudget() {
        return budget;
    }

    public void setBudget(int budget) {
        this.budget = budget;
    }

    public boolean isBankrupt() {
        return isBankrupt;
    }

    public Map<Consumer, Contract> getContracts() {
        return contracts;
    }

    public int getContractLength() {
        return contractLength;
    }

    @Override
    public void update(Observable o, Object arg) {
        needNewProducers = true;
    }

    //for debugging
    @Override
    public String toString() {
        return "\nid=" + id +
                "\nenergyNeededKW=" + energyNeededKW +
                "\ncontractCost=" + contractCost +
                "\nbudget=" + budget +
                "\nproducerStrategy=" + producerStrategy +
                "\nisBankrupt=" + isBankrupt +
                "\ncontracts: " + contracts.values() +
                "}\n";
    }
}

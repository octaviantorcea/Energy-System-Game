package entities;

import database.DistributorDatabase;
import database.ProducerDatabase;
import fileio.DistributorChanges;
import fileio.DistributorInput;
import strategies.ChooseProducerStrategy;
import strategies.EnergyChoiceStrategyType;
import strategies.StrategyFactory;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Observable;
import java.util.Observer;

import static utils.Constants.PRODUCTION_COST_RATE;
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
        if (!this.isBankrupt) {
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

            productionCost = (int) Math.round(Math.floor(cost / PRODUCTION_COST_RATE));
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

        if (isBankrupt && !energyProducers.isEmpty()) {
            energyProducers.forEach(producer -> {
                producer.deleteObserver(this);
                producer.setNrOfSubbedDistributors(producer.getNrOfSubbedDistributors() - 1);
                producer.getSubscribedDistributors().remove(this);
            });

            energyProducers.clear();
        }
    }

    public void modifyInfCost(final DistributorChanges distributorChanges) {
        infrastructureCost = distributorChanges.getInfrastructureCost();
    }

    public void chooseNewProducers(ProducerDatabase producerDatabase) {
        energyProducers.forEach(producer -> {
            producer.deleteObserver(this);
            producer.setNrOfSubbedDistributors(producer.getNrOfSubbedDistributors() - 1);
            producer.getSubscribedDistributors().remove(this);
        });

        energyProducers.clear();

        chooseProducers(producerDatabase);
    }

    private int computeFees() {
        int nrClients = contracts.size();
        return infrastructureCost + productionCost * nrClients;
    }

    public int getId() {
        return id;
    }

    public int getEnergyNeededKW() {
        return energyNeededKW;
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

    public EnergyChoiceStrategyType getProducerStrategy() {
        return producerStrategy;
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
        ((DistributorDatabase) arg).getNeedNewProducers().add(this);
    }
}

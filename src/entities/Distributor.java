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
    private final int id;
    private final int energyNeededKW;
    private int contractCost;
    private int budget;
    private final EnergyChoiceStrategyType producerStrategy;
    private boolean isBankrupt;
    /**
     * A map of contracts <br>
     * key - consumer <br>
     * value - contract
     */
    private final Map<Consumer, Contract> contracts = new LinkedHashMap<>();
    private final int contractLength;
    private int infrastructureCost;
    private int productionCost;
    /**
     * the strategy a distributor uses to choose the producers
     */
    private ChooseProducerStrategy strategy;
    /**
     * a list with the producers to which this distributor is subscribed
     */
    private List<Producer> energyProducers;

    public Distributor(final DistributorInput distributorInput) {
        this.id = distributorInput.getId();
        this.budget = distributorInput.getInitialBudget();
        this.contractLength = distributorInput.getContractLength();
        this.infrastructureCost = distributorInput.getInitialInfrastructureCost();
        this.energyNeededKW = distributorInput.getEnergyNeededKW();
        this.producerStrategy = distributorInput.getProducerStrategy();
        this.isBankrupt = false;
    }

    /**
     * chooses the necessary producers for the initialization round
     * and adds this distributor as an observer for the chosen producers
     * <p>
     * also adds this distributor in the subscribed distributor list of the
     * chosen producers
     */
    public void chooseProducers() {
        if (!this.isBankrupt) {
            energyProducers = this.strategy.getNecessaryProducers();
            energyProducers.forEach(producer -> {
                producer.addObserver(this); // add distributor in ObserverList of every producer
                producer.setNrOfSubbedDistributors(producer.getNrOfSubbedDistributors() + 1);
                producer.getSubscribedDistributors().add(this);
            });
        }
    }

    /**
     * computes the production cost
     */
    public void computeProductionCost() {
        if (!this.isBankrupt) {
            double cost = 0;

            for (Producer producer : energyProducers) {
                cost += producer.getEnergyPerDistributor() * producer.getPriceKW();
            }

            productionCost = (int) Math.round(Math.floor(cost / PRODUCTION_COST_RATE));
        }
    }

    /**
     * computes the price of the contract
     */
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
     * removes all the contracts this distributor has and removes this
     * distributor from the distributor list of all its producers
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

    /**
     * modifies the data from the distributor based on the input it gets
     * @param distributorChanges data changes
     */
    public void modifyInfCost(final DistributorChanges distributorChanges) {
        infrastructureCost = distributorChanges.getInfrastructureCost();
    }

    /**
     * chooses the necessary producers for a normal round<p>
     * uses chooseProducer method but first it unsubscribes this distributor
     * from every producer to which was subscribed last month
     * @see Distributor#chooseProducers()
     */
    public void chooseNewProducers() {
        energyProducers.forEach(producer -> {
            producer.deleteObserver(this);
            producer.setNrOfSubbedDistributors(producer.getNrOfSubbedDistributors() - 1);
            producer.getSubscribedDistributors().remove(this);
        });

        energyProducers.clear();

        chooseProducers();
    }

    private int computeFees() {
        int nrClients = contracts.size();
        return infrastructureCost + productionCost * nrClients;
    }

    @Override
    public void update(Observable o, Object arg) {
        ((DistributorDatabase) arg).getNeedNewProducers().add(this);
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

    public void setStrategy(ProducerDatabase producerDatabase) {
        this.strategy = StrategyFactory.getStrategyFactoryInstance()
                .createStrategy(producerStrategy, energyNeededKW, producerDatabase);
    }

    public Map<Consumer, Contract> getContracts() {
        return contracts;
    }

    public int getContractLength() {
        return contractLength;
    }
}

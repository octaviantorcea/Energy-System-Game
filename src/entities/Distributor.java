package entities;

import database.ProducerDatabase;
import fileio.DistributorInput;
import strategies.ChooseProducerStrategy;
import strategies.EnergyChoiceStrategyType;
import strategies.StrategyFactory;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Observable;
import java.util.Observer;

/**
 * Class that contains data about a distributor and specific methods
 */
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
    private boolean needNewProducers;
    private ChooseProducerStrategy strategy;

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

    @Override
    public void update(Observable o, Object arg) {
        needNewProducers = true;
    }

    //for debugging
    @Override
    public String toString() {
        return "\nid=" + id +
                "\nbudget=" + budget +
                "\ncontractLength=" + contractLength +
                "\ninfrastructureCost=" + infrastructureCost +
                "\nenergyNeededKW=" + energyNeededKW +
                "\nproducerStrategy=" + producerStrategy +
                "}\n";
    }
}

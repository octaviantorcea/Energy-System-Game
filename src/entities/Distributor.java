package entities;

import fileio.DistributorInput;
import strategies.EnergyChoiceStrategyType;

/**
 * Class that contains data about a distributor and specific methods
 */
public final class Distributor {
    private int id;
    private int budget;
    private int contractLength;
    private int infrastructureCost;
    private int energyNeededKW;
    private EnergyChoiceStrategyType producerStrategy;

    public Distributor(final DistributorInput distributorInput) {
        this.id = distributorInput.getId();
        this.budget = distributorInput.getInitialBudget();
        this.contractLength = distributorInput.getContractLength();
        this.infrastructureCost = distributorInput.getInitialInfrastructureCost();
        this.energyNeededKW = distributorInput.getEnergyNeededKW();
        this.producerStrategy = distributorInput.getProducerStrategy();
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

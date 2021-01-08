package entities;

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
}

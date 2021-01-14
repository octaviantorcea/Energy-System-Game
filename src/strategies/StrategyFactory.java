package strategies;

import database.ProducerDatabase;

/**
 * Factory for strategies
 */
public final class StrategyFactory {
    private static StrategyFactory instance = null;

    private StrategyFactory() { }

    /**
     * Creates a new strategy of type "strategyType" for a distributor that
     * needs "energyNeededKW" amount of energy
     * @param strategyType type of strategy returned
     * @param energyNeededKW the amount of energy needed for a distributor
     * @param producerDatabase the database from where it will choose the
     *                         necessary producers
     * @return a new strategy
     */
    public ChooseProducerStrategy createStrategy(final EnergyChoiceStrategyType strategyType,
                                                 final int energyNeededKW,
                                                 final ProducerDatabase producerDatabase) {
        return switch (strategyType) {
            case GREEN -> new GreenStrategy(energyNeededKW, producerDatabase);
            case PRICE -> new PriceStrategy(energyNeededKW, producerDatabase);
            case QUANTITY -> new QuantityStrategy(energyNeededKW, producerDatabase);
        };
    }

    /**
     * @return an instance of this StrategyFactory
     */
    public static StrategyFactory getStrategyFactoryInstance() {
        if (instance == null) {
            instance = new StrategyFactory();
        }

        return instance;
    }
}

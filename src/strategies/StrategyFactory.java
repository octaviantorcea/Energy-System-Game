package strategies;

import database.ProducerDatabase;

public final class StrategyFactory {
    private static StrategyFactory instance = null;

    private StrategyFactory() { }

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

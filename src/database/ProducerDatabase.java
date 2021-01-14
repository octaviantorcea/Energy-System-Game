package database;

import entities.Producer;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * Class that contains a list with all producers and two lists with green and
 * non-green producers<br>
 * Also contains methods that can be applied to all producers <br>
 */
public final class ProducerDatabase {
    /**
     * List with all producers
     */
    private final List<Producer> producers = new ArrayList<>();
    /**
     * List with all green producers
     */
    private final List<Producer> greenProducers = new ArrayList<>();
    /**
     * List with all non-green producers
     */
    private final List<Producer> nonGreenProducers = new ArrayList<>();

    /**
     * sorts the lists with green and non-green producers first by price, then
     * by energy per distributor and then finally by id
     */
    public void sortAuxiliaryLists() {
        Comparator<Producer> comparePriceQuantity = Comparator.comparing(Producer::getPriceKW)
                .thenComparing(Producer::getEnergyPerDistributor, Comparator.reverseOrder())
                .thenComparing(Producer::getId);

        greenProducers.sort(comparePriceQuantity);
        nonGreenProducers.sort(comparePriceQuantity);
    }

    /**
     * Applies saveMonthlyStats method for all producers
     * @param month the number of the month
     * @see Producer#saveMonthlyStats
     */
    public void saveMonthlyStats(int month) {
        producers.forEach(producer -> producer.saveMonthlyStats(month));
    }

    public List<Producer> getProducers() {
        return producers;
    }

    public List<Producer> getGreenProducers() {
        return greenProducers;
    }

    public List<Producer> getNonGreenProducers() {
        return nonGreenProducers;
    }
}

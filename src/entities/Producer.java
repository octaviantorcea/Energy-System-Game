package entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import fileio.ProducerInput;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;

/**
 * Class that contains data about a producer and specific methods
 */
public final class Producer extends Observable {
    private int id;
    private int maxDistributors;
    private double priceKW;
    private EnergyType energyType;
    private int energyPerDistributor;
    private List<MonthlyStats> monthlyStats = new ArrayList<>();
    @JsonIgnore
    private int subscribedDistributors;

    public Producer(ProducerInput producerInput) {
        this.id = producerInput.getId();
        this.maxDistributors = producerInput.getMaxDistributors();
        this.priceKW = producerInput.getPriceKW();
        this.energyType = producerInput.getEnergyType();
        this.energyPerDistributor = producerInput.getEnergyPerDistributor();
        this.subscribedDistributors = 0;
    }

    public boolean canHaveMoreDistributors() {
        return subscribedDistributors != maxDistributors;
    }

    public int getId() {
        return id;
    }

    public double getPriceKW() {
        return priceKW;
    }

    public int getEnergyPerDistributor() {
        return energyPerDistributor;
    }

    //for debugging
    @Override
    public String toString() {
        return "\nid=" + id +
                "\nmaxDistributors=" + maxDistributors +
                "\npriceKW=" + priceKW +
                "\nenergyType=" + energyType +
                "\nenergyPerDistributor=" + energyPerDistributor +
                "\nmonthlyStats=" + monthlyStats +
                "\nsubscribedDistributors=" + subscribedDistributors +
                "}\n";
    }
}

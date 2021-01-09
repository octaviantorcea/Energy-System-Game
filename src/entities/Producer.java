package entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import fileio.ProducerInput;

import java.util.ArrayList;
import java.util.List;

/**
 * Class that contains data about a producer and specific methods
 */
public final class Producer {
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

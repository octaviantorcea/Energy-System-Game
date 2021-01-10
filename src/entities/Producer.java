package entities;

import fileio.ProducerInput;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;

/**
 * Class that contains data about a producer and specific methods
 */
@SuppressWarnings("deprecation")
public final class Producer extends Observable {
    private int id;
    private int maxDistributors;
    private double priceKW;
    private EnergyType energyType;
    private int energyPerDistributor;
    private List<MonthlyStats> monthlyStats = new ArrayList<>();
    private int nrOfSubbedDistributors;
    private List<Distributor> subscribedDistributors = new ArrayList<>();

    public Producer(ProducerInput producerInput) {
        this.id = producerInput.getId();
        this.maxDistributors = producerInput.getMaxDistributors();
        this.priceKW = producerInput.getPriceKW();
        this.energyType = producerInput.getEnergyType();
        this.energyPerDistributor = producerInput.getEnergyPerDistributor();
        this.nrOfSubbedDistributors = 0;
    }

    public boolean canHaveMoreDistributors() {
        return nrOfSubbedDistributors != maxDistributors;
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

    public int getNrOfSubbedDistributors() {
        return nrOfSubbedDistributors;
    }

    public void setNrOfSubbedDistributors(int nrOfSubbedDistributors) {
        this.nrOfSubbedDistributors = nrOfSubbedDistributors;
    }

    public List<Distributor> getSubscribedDistributors() {
        return subscribedDistributors;
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
                "\nnrOfSubbedDistributors=" + nrOfSubbedDistributors +
                "}\n";
    }
}

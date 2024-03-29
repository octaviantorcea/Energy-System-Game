package entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import database.DistributorDatabase;
import fileio.ProducerChanges;
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
    @JsonIgnore
    private int nrOfSubbedDistributors;
    /**
     * a list with all distributors a producer has
     */
    @JsonIgnore
    private final List<Distributor> subscribedDistributors = new ArrayList<>();

    public Producer(ProducerInput producerInput) {
        this.id = producerInput.getId();
        this.maxDistributors = producerInput.getMaxDistributors();
        this.priceKW = producerInput.getPriceKW();
        this.energyType = producerInput.getEnergyType();
        this.energyPerDistributor = producerInput.getEnergyPerDistributor();
        this.nrOfSubbedDistributors = 0;
    }

    /**
     * verifies if this producer can accept another distributor
     * @return true if it can accept another distributor, false otherwise
     */
    public boolean canHaveMoreDistributors() {
        return nrOfSubbedDistributors != maxDistributors;
    }

    /**
     * saves the monthly stats (ids of subscribed distributors) for the month
     * given as argument
     * @param month the number of the month for which the stats are saved
     */
    public void saveMonthlyStats(int month) {
        List<Integer> subbedDistIds = new ArrayList<>();
        subscribedDistributors.forEach(distributor -> subbedDistIds.add(distributor.getId()));
        monthlyStats.add(new MonthlyStats(month, subbedDistIds));
    }

    /**
     * modifies this producer based on the input it gets from "producerChanges"
     * @param producerChanges data changes
     * @param distributorDB the database that has a list with all the distributors
     * that need to be notified
     */
    public void modifyProducer(ProducerChanges producerChanges, DistributorDatabase distributorDB) {
        setChanged();
        energyPerDistributor = producerChanges.getEnergyPerDistributor();
        notifyObservers(distributorDB);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getMaxDistributors() {
        return maxDistributors;
    }

    public void setMaxDistributors(int maxDistributors) {
        this.maxDistributors = maxDistributors;
    }

    public double getPriceKW() {
        return priceKW;
    }

    public void setPriceKW(double priceKW) {
        this.priceKW = priceKW;
    }

    public EnergyType getEnergyType() {
        return energyType;
    }

    public void setEnergyType(EnergyType energyType) {
        this.energyType = energyType;
    }

    public int getEnergyPerDistributor() {
        return energyPerDistributor;
    }

    public void setEnergyPerDistributor(int energyPerDistributor) {
        this.energyPerDistributor = energyPerDistributor;
    }

    public List<MonthlyStats> getMonthlyStats() {
        return monthlyStats;
    }

    public void setMonthlyStats(List<MonthlyStats> monthlyStats) {
        this.monthlyStats = monthlyStats;
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
}

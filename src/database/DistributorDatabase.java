package database;

import entities.Distributor;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * Class that contains all distributors <br>
 * Also contains methods that can be applied to all distributors <br>
 */
public final class DistributorDatabase {
    /**
     * List with all distributors
     */
    private final List<Distributor> distributors = new ArrayList<>();

    private final List<Distributor> needNewProducers = new ArrayList<>();

    public List<Distributor> getDistributors() {
        return distributors;
    }

    public List<Distributor> getNeedNewProducers() {
        return needNewProducers;
    }

    public void chooseProducers(ProducerDatabase producerDatabase) {
        distributors.forEach(distributor -> distributor.chooseProducers(producerDatabase));
    }

    public void chooseNewProducers(ProducerDatabase producerDatabase) {
        Comparator<Distributor> idComparator = Comparator.comparing(Distributor::getId);
        needNewProducers.sort(idComparator);
        needNewProducers.forEach(distributor -> distributor.chooseNewProducers(producerDatabase));
    }

    public void computeProductionCosts() {
        distributors.forEach(Distributor::computeProductionCost);
    }

    public void computeContractPrices() {
        distributors.forEach(Distributor::computeContractCost);
    }

    public Distributor findBestContract() {
        Distributor bestDistributor = null;

        for (Distributor toCompare : distributors) {
            if (!toCompare.isBankrupt()) {
                if (bestDistributor == null) {
                    bestDistributor = toCompare;
                } else {
                    if (toCompare.getContractCost() < bestDistributor.getContractCost()) {
                        bestDistributor = toCompare;
                    }
                }
            }
        }

        return bestDistributor;
    }

    /**
     * Applies payFees method for all distributors
     * @see Distributor#payFees()
     */
    public void payAllFees() {
        distributors.forEach(Distributor::payFees);
    }

    /**
     * Applies declareBankruptcy method for all distributors
     * @see Distributor#declareBankruptcy()
     */
    public void declareAllBankruptcies() {
        distributors.forEach(Distributor::declareBankruptcy);
    }


    //for debugging
    @Override
    public String toString() {
        return "DistributorDatabase{" +
                "distributors=" + distributors +
                '}';
    }
}

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
    /**
     * List with distributors whose producers have changed during a month
     */
    private final List<Distributor> needNewProducers = new ArrayList<>();

    /**
     * Applies chooseProducers method for all distributors
     * @see Distributor#chooseProducers()
     */
    public void chooseInitialProducers() {
        distributors.forEach(Distributor::chooseProducers);
    }

    /**
     * Applies chooseNewProducers method for distributors (that are first
     * ordered by their ids) which need to change their producers
     * @see Distributor#chooseNewProducers()
     */
    public void chooseProducers() {
        Comparator<Distributor> idComparator = Comparator.comparing(Distributor::getId);
        needNewProducers.sort(idComparator);
        needNewProducers.forEach(Distributor::chooseNewProducers);
    }

    /**
     * Applies computePrice method for all distributors
     * @see Distributor#computeProductionCost()
     */
    public void computeProductionCosts() {
        distributors.forEach(Distributor::computeProductionCost);
    }

    /**
     * Applies computePrice method for all distributors
     * @see Distributor#computeContractCost()
     */
    public void computeContractPrices() {
        distributors.forEach(Distributor::computeContractCost);
    }

    /**
     * Finds the distributor with the best (lowest price) contract
     * @return the distributor with the best contract
     */
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

    /**
     * verifies if all distributors are bankrupt
     * @return true if all distributors are bankrupt, false otherwise
     */
    public boolean allBankrupt() {
        for (Distributor distributor : distributors) {
            if (!distributor.isBankrupt()) {
                return false;
            }
        }

        return true;
    }

    public List<Distributor> getDistributors() {
        return distributors;
    }

    public List<Distributor> getNeedNewProducers() {
        return needNewProducers;
    }
}

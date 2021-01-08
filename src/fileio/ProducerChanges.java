package fileio;

/**
 * Class that contains producer changes
 */
public final class ProducerChanges {
    private int id;
    private int energyPerDistributor;

    public int getId() {
        return id;
    }

    public void setId(final int id) {
        this.id = id;
    }

    public int getEnergyPerDistributor() {
        return energyPerDistributor;
    }

    public void setEnergyPerDistributor(final int energyPerDistributor) {
        this.energyPerDistributor = energyPerDistributor;
    }

    // for debugging
    @Override
    public String toString() {
        return "ProducerChanges{" +
                "\nid=" + id +
                "\nenergyPerDistributor=" + energyPerDistributor +
                "}\n";
    }
}

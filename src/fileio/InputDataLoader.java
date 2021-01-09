package fileio;

import java.util.List;

/**
 * Class that contains all data parsed from input file
 */
public final class InputDataLoader {
    private int numberOfTurns;
    /**
     * Class with all the initial data
     */
    private InitialData initialData;
    /**
     * List with all the monthly updates
     */
    private List<MonthlyUpdates> monthlyUpdates;

    public int getNumberOfTurns() {
        return numberOfTurns;
    }

    public void setNumberOfTurns(final int numberOfTurns) {
        this.numberOfTurns = numberOfTurns;
    }

    public InitialData getInitialData() {
        return initialData;
    }

    public void setInitialData(final InitialData initialData) {
        this.initialData = initialData;
    }

    public List<MonthlyUpdates> getMonthlyUpdates() {
        return monthlyUpdates;
    }

    public void setMonthlyUpdates(final List<MonthlyUpdates> monthlyUpdates) {
        this.monthlyUpdates = monthlyUpdates;
    }
}

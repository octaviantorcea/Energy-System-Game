package entities;

import java.util.List;

/**
 * class that keeps track of subscribed distributors in a month for a producer
 */
public final class MonthlyStats {
    /**
     * number of month
     */
    private int month;
    /**
     * list with ids of subscribed distributors
     */
    private List<Integer> distributorsIds;

    public MonthlyStats(int month, List<Integer> distributorsIds) {
        this.month = month;
        this.distributorsIds = distributorsIds;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public List<Integer> getDistributorsIds() {
        return distributorsIds;
    }

    public void setDistributorsIds(List<Integer> distributorsIds) {
        this.distributorsIds = distributorsIds;
    }
}

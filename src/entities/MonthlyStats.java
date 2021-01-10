package entities;

import java.util.ArrayList;
import java.util.List;

public final class MonthlyStats {
    private int month;
    private List<Integer> distributorsIds;

    //for debugging
    public MonthlyStats(int month, List<Integer> distributorsIds) {
        this.month = month;
        this.distributorsIds = distributorsIds;
    }

    @Override
    public String toString() {
        return "\nmonth=" + month +
                "\ndistributorsIds=" + distributorsIds +
                "}\n";
    }
}

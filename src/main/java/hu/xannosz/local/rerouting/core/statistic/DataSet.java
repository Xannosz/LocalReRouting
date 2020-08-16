package hu.xannosz.local.rerouting.core.statistic;

import lombok.Getter;
import org.jfree.data.category.DefaultCategoryDataset;

import java.util.HashMap;
import java.util.Map;

public class DataSet extends DefaultCategoryDataset {

    @Getter
    private final String title;
    Map<String, Integer> times = new HashMap<>();

    public DataSet(String title) {
        this.title = title;
    }

    public void addData(String key, float data) {
        setValue(data, key, "" + getTime(key));
    }

    private int getTime(String key) {
        if (!times.containsKey(key)) {
            if (times.isEmpty()) {
                times.put(key, 0);
            } else {
                times.put(key, times.entrySet().stream().iterator().next().getValue()-1);
            }

        }
        int time = times.get(key);
        times.put(key, ++time);
        return time;
    }
}

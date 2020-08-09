package hu.xannosz.local.rerouting.core.statistic;

import lombok.Getter;
import org.jfree.data.category.DefaultCategoryDataset;

public class DataSet extends DefaultCategoryDataset {

    @Getter
    private String title;
    private int time = 0;

    public DataSet(String title) {
        this.title = title;
    }

    public void addData(int data) {
        setValue(data, title, "" + time);
        time++;
    }
}

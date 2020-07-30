package hu.xannosz.local.rerouting.statistic;

import org.jfree.data.category.DefaultCategoryDataset;

public class DataSet extends DefaultCategoryDataset {

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

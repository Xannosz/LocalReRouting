package hu.xannosz.local.rerouting.statistic;

import hu.xannosz.microtools.Sleep;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;

import javax.swing.*;

public class GraphicWindow extends JFrame {

    public static void main(String[] args) {
        dataSet = createDataset();
        GraphicWindow win = new GraphicWindow();
        for (; ; ) {
            Sleep.sleepSeconds(1);
            dataSet.setValue(60, "line1", "time1");
            win.update(dataSet);
            Sleep.sleepSeconds(1);
            dataSet.setValue(10, "line1", "time1");
            win.update(dataSet);
        }
    }

    private BaseChart demo;
    private static DefaultCategoryDataset dataSet;

    public GraphicWindow() {
        demo = new BaseChart("Test", dataSet);
        add(demo);
        pack();
        setVisible(true);
    }

    public void update(CategoryDataset dataSet) {
        demo.updateData(dataSet);
    }

    private static DefaultCategoryDataset createDataset() {
        DefaultCategoryDataset result = new DefaultCategoryDataset();
        result.setValue(29, "line1", "time1");
        result.setValue(40, "line1", "time2");
        result.setValue(20, "line2", "time1");
        result.setValue(51, "line2", "time2");
        return result;
    }
}

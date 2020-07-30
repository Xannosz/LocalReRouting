package hu.xannosz.local.rerouting.statistic;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.CategoryDataset;

public class BaseChart extends ChartPanel {
    private String chartTitle;

    public BaseChart(String chartTitle, CategoryDataset dataSet) {
        super(null);
        this.chartTitle = chartTitle;
        updateData(dataSet);
        setPreferredSize(new java.awt.Dimension(500, 270));
    }

    public void updateData(CategoryDataset dataSet) {
        setChart(ChartFactory.createLineChart(
                chartTitle,
                "time", "value",
                dataSet,
                PlotOrientation.VERTICAL,
                true,
                true,
                false
        ));
    }
}

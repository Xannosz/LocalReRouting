package hu.xannosz.local.rerouting.core;

import hu.xannosz.local.rerouting.core.statistic.BaseChart;
import hu.xannosz.local.rerouting.core.statistic.ChartStatistic;
import hu.xannosz.local.rerouting.core.util.Constants;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;
import java.util.Map;

public class ChartWindow extends JFrame {

    Map<ChartStatistic, BaseChart> dates = new HashMap<>();

    public ChartWindow() {
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setSize(300, Constants.CHART_STATISTICS.size() * 100);
        setLocationRelativeTo(null);
        setLayout(new GridLayout(Constants.CHART_STATISTICS.size(), 1));
        for (ChartStatistic chart : Constants.CHART_STATISTICS) {
            dates.put(chart, new BaseChart(chart.getDataSet().getTitle(), chart.getDataSet()));
        }
        for (Map.Entry<ChartStatistic, BaseChart> entry : dates.entrySet()) {
            add(entry.getValue());
        }
        pack();
        setVisible(true);
    }

    public void update() {
        for (Map.Entry<ChartStatistic, BaseChart> entry : dates.entrySet()) {
            entry.getValue().updateData(entry.getKey().getDataSet());
        }
    }
}

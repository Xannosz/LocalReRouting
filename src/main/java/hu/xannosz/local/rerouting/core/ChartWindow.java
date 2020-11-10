package hu.xannosz.local.rerouting.core;

import hu.xannosz.local.rerouting.core.interfaces.Statistic;
import hu.xannosz.local.rerouting.core.statistic.BaseChart;
import hu.xannosz.local.rerouting.core.thread.ChartThread;
import hu.xannosz.local.rerouting.core.util.Constants;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.HashMap;
import java.util.Map;

public class ChartWindow extends JFrame {

    private final Map<Statistic, BaseChart> dates = new HashMap<>();

    public ChartWindow() {
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setSize(300, Constants.STATISTICS.size() * 100);
        setLocationRelativeTo(null);
        setLayout(new GridLayout(Constants.STATISTICS.size(), 1));
        for (Statistic chart : Constants.STATISTICS) {
            dates.put(chart, new BaseChart(chart.getDataSet().getTitle(), chart.getDataSet()));
        }
        for (Map.Entry<Statistic, BaseChart> entry : dates.entrySet()) {
            add(entry.getValue());
        }
        pack();

        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                ChartThread.reset();
            }
        });

        setVisible(true);
    }

    public void update() {
        for (Map.Entry<Statistic, BaseChart> entry : dates.entrySet()) {
            entry.getValue().updateData(entry.getKey().getDataSet());
        }
    }
}

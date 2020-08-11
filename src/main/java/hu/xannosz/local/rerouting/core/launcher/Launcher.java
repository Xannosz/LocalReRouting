package hu.xannosz.local.rerouting.core.launcher;

import hu.xannosz.local.rerouting.core.App;
import hu.xannosz.local.rerouting.core.StatisticRunner;
import hu.xannosz.local.rerouting.core.algorithm.Algorithm;
import hu.xannosz.local.rerouting.core.graph.GraphType;
import hu.xannosz.local.rerouting.core.thread.StatisticRunnerThread;
import hu.xannosz.local.rerouting.core.util.Constants;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashSet;
import java.util.Set;

public class Launcher extends JFrame implements ActionListener {
    public static void main(String[] args) {
        new Launcher();
    }

    private GraphType<?> graphType;
    private SettingsPanel<?> settingsPanel;
    private final JPanel settingsPanelContainer = new JPanel();
    private Algorithm<?> algorithm;

    public Launcher() {
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(300, 200);
        init();
        setLayout(new GridLayout(2, 2));
        settingsPanelContainer.add(settingsPanel);
        settingsPanelContainer.setLayout(new GridLayout(1, 1));
    }

    private JComboBox<String> createGraphList() {
        Set<String> graphNames = new HashSet<>();
        for (GraphType<?> type : Constants.GRAPHS) {
            graphNames.add(type.getName());
        }
        JComboBox<String> graphs = new JComboBox<>(graphNames.toArray(new String[Constants.GRAPHS.size()]));
        graphs.addActionListener(this);
        graphType = Constants.GRAPHS.iterator().next();
        settingsPanel = graphType.getPanel();
        graphs.setSelectedItem(graphType.getName());
        return graphs;
    }

    private JComboBox<String> createAlgorithmList() {
        Set<String> algorithmNames = new HashSet<>();
        for (Algorithm<?> algorithm : Constants.ALGORITHMS) {
            algorithmNames.add(algorithm.getName());
        }
        JComboBox<String> algorithms = new JComboBox<>(algorithmNames.toArray(new String[Constants.ALGORITHMS.size()]));
        algorithms.addActionListener(this);
        algorithm = Constants.ALGORITHMS.iterator().next();
        return algorithms;
    }

    private void init() {
        add(createGraphList());
        add(createAlgorithmList());
        add(settingsPanelContainer);
        JButton button = new JButton("Start");
        button.addActionListener(this);
        add(button);
        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() instanceof JComboBox) {
            JComboBox<?> cb = (JComboBox<?>) e.getSource();
            String item = cb.getSelectedItem() != null ? (String) cb.getSelectedItem() : "";
            for (GraphType<?> graphType : Constants.GRAPHS) {
                if (graphType.getName().equals(item)) {
                    this.graphType = graphType;
                    settingsPanel = this.graphType.getPanel();
                    settingsPanelContainer.removeAll();
                    settingsPanelContainer.add(settingsPanel);
                }
            }
            for (Algorithm<?> algorithm : Constants.ALGORITHMS) {
                if (algorithm.getName().equals(item)) {
                    this.algorithm = algorithm;
                }
            }
            revalidate();
            repaint();
        }
        if (e.getSource() instanceof JButton) {
            JButton button = (JButton) e.getSource();
            if (button.getText().equals("Start")) {
                new App(graphType, settingsPanel.getSettings(), algorithm);
                StatisticRunnerThread statisticRunnerThread = new StatisticRunnerThread(new StatisticRunner(graphType, algorithm, 100, settingsPanel.getSettings()));
                statisticRunnerThread.start();
            }
        }
    }
}

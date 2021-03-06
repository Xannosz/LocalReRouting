package hu.xannosz.local.rerouting.core.launcher;

import hu.xannosz.local.rerouting.core.App;
import hu.xannosz.local.rerouting.core.StatisticRunner;
import hu.xannosz.local.rerouting.core.interfaces.Algorithm;
import hu.xannosz.local.rerouting.core.interfaces.FailureGenerator;
import hu.xannosz.local.rerouting.core.interfaces.GraphType;
import hu.xannosz.local.rerouting.core.interfaces.MessageGenerator;
import hu.xannosz.local.rerouting.core.thread.ChartThread;
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
    private GraphSettingsPanel<?> graphSettingsPanel;
    private final JPanel graphSettingsPanelContainer = new JPanel();
    private AlgorithmSettingsPanel algorithmSettingsPanel;
    private final JPanel algorithmSettingsPanelContainer = new JPanel();
    private MessageGeneratorSettingsPanel<?> messageGeneratorSettingsPanel;
    private FailureGeneratorSettingsPanel<?> failureGeneratorSettingsPanel;
    private final JPanel messageGeneratorSettingsPanelContainer = new JPanel();
    private final JPanel failureGeneratorSettingsPanelContainer = new JPanel();
    private Algorithm algorithm;
    private MessageGenerator<?> messageGenerator;
    private FailureGenerator<?> failureGenerator;
    private final JSpinner graphCountSpinner = new JSpinner();
    private final JCheckBox runStatistic = new JCheckBox();
    private final JCheckBox runVisualiser = new JCheckBox();

    public Launcher() {
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(6 * 180, 150);
        init();
        setLayout(new GridLayout(2, 6));
        graphSettingsPanelContainer.add(graphSettingsPanel);
        graphSettingsPanelContainer.setLayout(new GridLayout(1, 1));
        algorithmSettingsPanelContainer.add(algorithmSettingsPanel);
        algorithmSettingsPanelContainer.setLayout(new GridLayout(1, 1));
        messageGeneratorSettingsPanelContainer.add(messageGeneratorSettingsPanel);
        messageGeneratorSettingsPanelContainer.setLayout(new GridLayout(1, 1));
        failureGeneratorSettingsPanelContainer.add(failureGeneratorSettingsPanel);
        failureGeneratorSettingsPanelContainer.setLayout(new GridLayout(1, 1));
    }

    private JComboBox<String> createGraphList() {
        Set<String> graphNames = new HashSet<>();
        for (GraphType<?> type : Constants.GRAPHS) {
            graphNames.add(type.getName());
        }
        JComboBox<String> graphs = new JComboBox<>(graphNames.toArray(new String[Constants.GRAPHS.size()]));
        graphs.addActionListener(this);
        graphType = Constants.GRAPHS.iterator().next();
        graphSettingsPanel = graphType.getPanel();
        graphs.setSelectedItem(graphType.getName());
        return graphs;
    }

    private JComboBox<String> createAlgorithmList() {
        Set<String> algorithmNames = new HashSet<>();
        for (Algorithm algorithm : Constants.ALGORITHMS) {
            algorithmNames.add(algorithm.getName());
        }
        JComboBox<String> algorithms = new JComboBox<>(algorithmNames.toArray(new String[Constants.ALGORITHMS.size()]));
        algorithms.addActionListener(this);
        algorithm = Constants.ALGORITHMS.iterator().next();
        algorithmSettingsPanel = algorithm.getPanel();
        algorithms.setSelectedItem(algorithm.getName());
        return algorithms;
    }

    private JComboBox<String> createMessageGeneratorList() {
        Set<String> generatorNames = new HashSet<>();
        for (MessageGenerator<?> generator : Constants.MESSAGE_GENERATORS) {
            generatorNames.add(generator.getName());
        }
        JComboBox<String> generators = new JComboBox<>(generatorNames.toArray(new String[Constants.MESSAGE_GENERATORS.size()]));
        generators.addActionListener(this);
        messageGenerator = Constants.MESSAGE_GENERATORS.iterator().next();
        messageGeneratorSettingsPanel = messageGenerator.getPanel();
        generators.setSelectedItem(messageGenerator.getName());
        return generators;
    }

    private JComboBox<String> createFailureGeneratorList() {
        Set<String> generatorNames = new HashSet<>();
        for (FailureGenerator<?> generator : Constants.FAILURE_GENERATORS) {
            generatorNames.add(generator.getName());
        }
        JComboBox<String> generators = new JComboBox<>(generatorNames.toArray(new String[Constants.FAILURE_GENERATORS.size()]));
        generators.addActionListener(this);
        failureGenerator = Constants.FAILURE_GENERATORS.iterator().next();
        failureGeneratorSettingsPanel = failureGenerator.getPanel();
        generators.setSelectedItem(failureGenerator.getName());
        return generators;
    }

    private void init() {
        add(createGraphList());

        JPanel countPanel = new JPanel();
        countPanel.add(new JLabel("Count of graphs: "));
        graphCountSpinner.setValue(100);
        countPanel.add(graphCountSpinner);
        countPanel.setLayout(new GridLayout(2, 1));
        add(countPanel);

        add(createMessageGeneratorList());
        add(createFailureGeneratorList());
        add(createAlgorithmList());
        add(new JPanel());

        add(graphSettingsPanelContainer);

        JPanel boolPanel = new JPanel();
        boolPanel.add(new JLabel("Run statistics: "));
        boolPanel.add(runStatistic);
        runStatistic.setSelected(true);
        boolPanel.add(new JLabel("Run visualiser: "));
        boolPanel.add(runVisualiser);
        runVisualiser.setSelected(true);
        add(boolPanel);

        add(messageGeneratorSettingsPanelContainer);
        add(failureGeneratorSettingsPanelContainer);
        add(algorithmSettingsPanelContainer);

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
                    graphSettingsPanel = this.graphType.getPanel();
                    graphSettingsPanelContainer.removeAll();
                    graphSettingsPanelContainer.add(graphSettingsPanel);
                }
            }
            for (Algorithm algorithm : Constants.ALGORITHMS) {
                if (algorithm.getName().equals(item)) {
                    this.algorithm = algorithm;
                    algorithmSettingsPanel = this.algorithm.getPanel();
                    algorithmSettingsPanelContainer.removeAll();
                    algorithmSettingsPanelContainer.add(algorithmSettingsPanel);
                }
            }
            for (MessageGenerator<?> messageGenerator : Constants.MESSAGE_GENERATORS) {
                if (messageGenerator.getName().equals(item)) {
                    this.messageGenerator = messageGenerator;
                    messageGeneratorSettingsPanel = this.messageGenerator.getPanel();
                    messageGeneratorSettingsPanelContainer.removeAll();
                    messageGeneratorSettingsPanelContainer.add(messageGeneratorSettingsPanel);
                }
            }
            for (FailureGenerator<?> failureGenerator : Constants.FAILURE_GENERATORS) {
                if (failureGenerator.getName().equals(item)) {
                    this.failureGenerator = failureGenerator;
                    failureGeneratorSettingsPanel = this.failureGenerator.getPanel();
                    failureGeneratorSettingsPanelContainer.removeAll();
                    failureGeneratorSettingsPanelContainer.add(failureGeneratorSettingsPanel);
                }
            }
            revalidate();
            repaint();
        }
        if (e.getSource() instanceof JButton) {
            JButton button = (JButton) e.getSource();
            if (button.getText().equals("Start")) {
                if (runVisualiser.isSelected()) {
                    new App(graphType, graphSettingsPanel.getSettings(), algorithm, algorithmSettingsPanel.getSettings(), messageGenerator, messageGeneratorSettingsPanel.getSettings(), failureGenerator, failureGeneratorSettingsPanel.getSettings());
                }
                if (runStatistic.isSelected()) {
                    StatisticRunnerThread statisticRunnerThread = new StatisticRunnerThread(
                            new StatisticRunner(graphType, algorithm, algorithmSettingsPanel.getSettings(), (int) graphCountSpinner.getValue(), graphSettingsPanel.getSettings(),
                                    messageGenerator, messageGeneratorSettingsPanel.getSettings(),
                                    failureGenerator, failureGeneratorSettingsPanel.getSettings()));
                    statisticRunnerThread.start();
                    ChartThread.startChart();
                }
            }
        }
    }
}

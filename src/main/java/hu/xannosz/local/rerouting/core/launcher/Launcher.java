package hu.xannosz.local.rerouting.core.launcher;

import hu.xannosz.local.rerouting.core.App;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Launcher extends JFrame implements ActionListener {
    public static void main(String[] args) {
        new Launcher();
    }

    private JPanel complete = new JPanel();
    private JPanel pair = new JPanel();
    private JPanel threeComplete = new JPanel();
    private JPanel erdosRenyi = new JPanel();
    private JPanel settings = new JPanel();
    private String graphType = "Complete";
    private JSpinner CompleteSA = new JSpinner();
    private JSpinner PairSA = new JSpinner();
    private JSpinner PairSB = new JSpinner();
    private JSpinner ThreeCompleteSA = new JSpinner();
    private JSpinner ThreeCompleteSB = new JSpinner();
    private JSpinner ThreeCompleteSC = new JSpinner();
    private JSpinner ErdosRenyiSA = new JSpinner();
    private JSpinner ErdosRenyiSB = new JSpinner();

    private String algorithm = "Random";

    public Launcher() {
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(300, 400);
        setUpConstants();
        init();
        setLayout(new GridLayout(4, 1));
        settings.add(complete);
        settings.setLayout(new GridLayout(1, 1));
    }

    private JComboBox<String> createGraphList() {
        JComboBox<String> graphs = new JComboBox<>(new String[]{"Complete", "Pair", "ThreeComplete", "Petersen", "Erdős–Rényí"});
        graphs.addActionListener(this);
        return graphs;
    }

    private JComboBox<String> createAlgorithmList() {
        JComboBox<String> algorithms = new JComboBox<>(new String[]{"Random", "AllToOne", "Permutation", "BBID_3", "BBID_5"});
        algorithms.addActionListener(this);
        return algorithms;
    }

    private void createCompleteSettings() {
        complete.add(new JLabel("Nodes: "));
        CompleteSA.setValue(10);
        complete.add(CompleteSA);
        complete.setLayout(new GridLayout(1, 2));
    }

    private void createPairSettings() {
        JPanel a = new JPanel();
        a.add(new JLabel("A Nodes: "));
        PairSA.setValue(10);
        a.add(PairSA);
        a.setLayout(new GridLayout(1, 2));
        JPanel b = new JPanel();
        b.add(new JLabel("B Nodes: "));
        PairSB.setValue(10);
        b.add(PairSB);
        b.setLayout(new GridLayout(1, 2));
        pair.add(a);
        pair.add(b);
        pair.setLayout(new GridLayout(2, 1));
    }

    private void createThreeCompleteSettings() {
        JPanel a = new JPanel();
        a.add(new JLabel("A Nodes: "));
        ThreeCompleteSA.setValue(10);
        a.add(ThreeCompleteSA);
        a.setLayout(new GridLayout(1, 2));
        JPanel b = new JPanel();
        b.add(new JLabel("B Nodes: "));
        ThreeCompleteSB.setValue(10);
        b.add(ThreeCompleteSB);
        b.setLayout(new GridLayout(1, 2));
        JPanel c = new JPanel();
        c.add(new JLabel("C Nodes: "));
        ThreeCompleteSC.setValue(10);
        c.add(ThreeCompleteSC);
        c.setLayout(new GridLayout(1, 2));
        threeComplete.add(a);
        threeComplete.add(b);
        threeComplete.add(c);
        threeComplete.setLayout(new GridLayout(3, 1));
    }

    private void createErdosRenyiSettings() {
        JPanel a = new JPanel();
        a.add(new JLabel("Nodes: "));
        ErdosRenyiSA.setValue(15);
        a.add(ErdosRenyiSA);
        a.setLayout(new GridLayout(1, 2));
        erdosRenyi.add(a);
        JPanel b = new JPanel();
        b.add(new JLabel("p (%): "));
        ErdosRenyiSB.setValue(30);
        b.add(ErdosRenyiSB);
        b.setLayout(new GridLayout(1, 2));
        erdosRenyi.add(b);
        erdosRenyi.setLayout(new GridLayout(2, 1));
    }

    private void setUpConstants() {
        createCompleteSettings();
        createPairSettings();
        createThreeCompleteSettings();
        createErdosRenyiSettings();
    }

    private void init() {
        add(createGraphList());
        add(createAlgorithmList());
        add(settings);
        JButton button = new JButton("Start");
        button.addActionListener(this);
        add(button);
        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() instanceof JComboBox) {
            JComboBox cb = (JComboBox) e.getSource();
            String item = (String) cb.getSelectedItem();
            if (item.equals("Complete")) {
                graphType = item;
                settings.removeAll();
                settings.add(complete);
            }
            if (item.equals("Pair")) {
                graphType = item;
                settings.removeAll();
                settings.add(pair);
            }
            if (item.equals("ThreeComplete")) {
                graphType = item;
                settings.removeAll();
                settings.add(threeComplete);
            }
            if (item.equals("Petersen")) {
                graphType = item;
                settings.removeAll();
            }
            if (item.equals("Erdős–Rényí")) {
                graphType = item;
                settings.removeAll();
                settings.add(erdosRenyi);
            }
            if (item.equals("Random") || item.equals("Permutation") || item.equals("AllToOne") || item.equals("BBID_3") || item.equals("BBID_5")) {
                algorithm = item;
            }
            revalidate();
            repaint();
        }
        if (e.getSource() instanceof JButton) {
            JButton button = (JButton) e.getSource();
            if (button.getText().equals("Start")) {
                if (graphType.equals("Complete")) {
                    new App(graphType, (int) CompleteSA.getValue(), 0, 0, algorithm);
                }
                if (graphType.equals("Pair")) {
                    new App(graphType, (int) PairSA.getValue(), (int) PairSB.getValue(), 0, algorithm);
                }
                if (graphType.equals("ThreeComplete")) {
                    new App(graphType, (int) ThreeCompleteSA.getValue(), (int) ThreeCompleteSB.getValue(), (int) ThreeCompleteSC.getValue(), algorithm);
                }
                if (graphType.equals("Petersen")) {
                    new App(graphType, 0, 0, 0, algorithm);
                }
                if (graphType.equals("Erdős–Rényí")) {
                    new App(graphType, (int) ErdosRenyiSA.getValue(), (int) ErdosRenyiSB.getValue(), 0, algorithm);
                }
            }
        }
    }
}

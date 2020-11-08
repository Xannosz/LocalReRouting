package hu.xannosz.local.rerouting.graph;

import hu.xannosz.local.rerouting.core.Network;
import hu.xannosz.local.rerouting.core.interfaces.GraphType;
import hu.xannosz.local.rerouting.core.launcher.GraphSettingsPanel;
import hu.xannosz.local.rerouting.core.util.GraphHelper;
import lombok.Data;

import javax.swing.*;
import java.awt.*;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

@hu.xannosz.local.rerouting.core.annotation.GraphType
public class Pair implements GraphType<Pair.Settings> {
    @Override
    public Network createGraph(Settings settings) {
        Network result = new Network(getName(), settings.aNodes + settings.bNodes);
        GraphHelper.createPairGraph(result, result.getNodesFromInterval(0, settings.aNodes), result.getNodesFromInterval(settings.aNodes, settings.aNodes + settings.bNodes), 0, 0, 3);
        return result;
    }

    @Override
    public String getName() {
        return "Pair";
    }

    @Override
    public PanelGraph getPanel() {
        return new PanelGraph();
    }

    @Data
    public static class Settings {
        private int aNodes;
        private int bNodes;
    }

    public static class PanelGraph extends GraphSettingsPanel<Settings> {

        private final JSpinner spinnerA = new JSpinner();
        private final JSpinner spinnerB = new JSpinner();

        public PanelGraph() {
            JPanel a = new JPanel();
            a.add(new JLabel("A Nodes: "));
            spinnerA.setValue(10);
            a.add(spinnerA);
            a.setLayout(new GridLayout(1, 2));
            JPanel b = new JPanel();
            b.add(new JLabel("B Nodes: "));
            spinnerB.setValue(10);
            b.add(spinnerB);
            b.setLayout(new GridLayout(1, 2));
            add(a);
            add(b);
            setLayout(new GridLayout(2, 1));
        }

        @Override
        public Settings getSettings() {
            Settings result = new Settings();
            result.setANodes((int) spinnerA.getValue());
            result.setBNodes((int) spinnerB.getValue());
            return result;
        }
    }

    @Override
    public Collection<Settings> getSettings() {
        Set<Settings> settingses = new HashSet<>();
        for (int i = 10; i < 500; i += 20) {
            for (int e = 10; e < 500; e += 20) {
                Settings settings = new Settings();
                settings.setANodes(i);
                settings.setBNodes(e);
                settingses.add(settings);
            }
        }
        return settingses;
    }
}

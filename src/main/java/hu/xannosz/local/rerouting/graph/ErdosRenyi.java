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
public class ErdosRenyi implements GraphType<ErdosRenyi.Settings> {
    @Override
    public Network createGraph(Settings settings) {
        Network result = new Network(getName(), settings.nodes);
        GraphHelper.createErdosRenyiGraph(result, result.getNodesFromInterval(0, settings.nodes), settings.p, 0, 0);
        return result;
    }

    @Override
    public String getName() {
        return "Erdős–Rényí";
    }

    @Override
    public PanelGraph getPanel() {
        return new PanelGraph();
    }

    @Data
    public static class Settings {
        private int nodes;
        private int p;

        @Override
        public String toString() {
            return "Node" + nodes + "P" + p;
        }
    }

    public static class PanelGraph extends GraphSettingsPanel<Settings> {

        private final JSpinner spinnerA = new JSpinner();
        private final JSpinner spinnerB = new JSpinner();

        public PanelGraph() {
            JPanel a = new JPanel();
            a.add(new JLabel("Nodes: "));
            spinnerA.setValue(15);
            a.add(spinnerA);
            a.setLayout(new GridLayout(1, 2));
            JPanel b = new JPanel();
            b.add(new JLabel("p (%): "));
            spinnerB.setValue(30);
            b.add(spinnerB);
            b.setLayout(new GridLayout(1, 2));
            add(a);
            add(b);
            setLayout(new GridLayout(2, 1));
        }

        @Override
        public Settings getSettings() {
            Settings result = new Settings();
            result.setNodes((int) spinnerA.getValue());
            result.setP((int) spinnerB.getValue());
            return result;
        }
    }

    @Override
    public Collection<Settings> getSettings() {
        Set<Settings> settingses = new HashSet<>();

        for (int e = 20; e < 70; e += 20) {
            Settings settings = new Settings();
            settings.setNodes(97);
            settings.setP(e);
            settingses.add(settings);
        }
        for (int e = 20; e < 70; e += 20) {
            Settings settings = new Settings();
            settings.setNodes(199);
            settings.setP(e);
            settingses.add(settings);
        }
/*        for (int e = 20; e < 70; e += 20) {
            Settings settings = new Settings();
            settings.setNodes(499);
            settings.setP(e);
            settingses.add(settings);
        }*/

        return settingses;
    }
}

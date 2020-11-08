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
public class ThreeComplete implements GraphType<ThreeComplete.Settings> {
    @Override
    public Network createGraph(Settings settings) {
        Network result = new Network(getName(), settings.aNodes + settings.bNodes + settings.cNodes);
        GraphHelper.createCompleteGraph(result, result.getNodesFromInterval(0, settings.aNodes), 0, 0);
        GraphHelper.createCompleteGraph(result, result.getNodesFromInterval(settings.aNodes, settings.aNodes + settings.bNodes), 3, 3);
        GraphHelper.createCompleteGraph(result, result.getNodesFromInterval(settings.aNodes + settings.bNodes, settings.aNodes + settings.bNodes + settings.cNodes), 6, 0);

        GraphHelper.createPairGraph(result,
                result.getNodesFromInterval(0, settings.aNodes),
                result.getNodesFromInterval(settings.aNodes, settings.aNodes + settings.bNodes));
        GraphHelper.createPairGraph(result,
                result.getNodesFromInterval(settings.aNodes + settings.bNodes, settings.aNodes + settings.bNodes + settings.cNodes),
                result.getNodesFromInterval(settings.aNodes, settings.aNodes + settings.bNodes));
        GraphHelper.createPairGraph(result,
                result.getNodesFromInterval(settings.aNodes + settings.bNodes, settings.aNodes + settings.bNodes + settings.cNodes),
                result.getNodesFromInterval(0, settings.aNodes));
        return result;
    }

    @Override
    public String getName() {
        return "Three Complete";
    }

    @Override
    public PanelGraph getPanel() {
        return new PanelGraph();
    }

    @Data
    public static class Settings {
        private int aNodes;
        private int bNodes;
        private int cNodes;
    }

    public static class PanelGraph extends GraphSettingsPanel<Settings> {

        private final JSpinner spinnerA = new JSpinner();
        private final JSpinner spinnerB = new JSpinner();
        private final JSpinner spinnerC = new JSpinner();

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
            JPanel c = new JPanel();
            c.add(new JLabel("C Nodes: "));
            spinnerC.setValue(10);
            c.add(spinnerC);
            c.setLayout(new GridLayout(1, 2));
            add(a);
            add(b);
            add(c);
            setLayout(new GridLayout(3, 1));
        }

        @Override
        public Settings getSettings() {
            Settings result = new Settings();
            result.setANodes((int) spinnerA.getValue());
            result.setBNodes((int) spinnerB.getValue());
            result.setCNodes((int) spinnerC.getValue());
            return result;
        }
    }

    @Override
    public Collection<Settings> getSettings() {
        Set<Settings> settingses = new HashSet<>();
        for (int i = 10; i < 350; i += 20) {
            for (int e = 10; e < 350; e += 20) {
                for (int f = 10; f < 350; f += 20) {
                    Settings settings = new Settings();
                    settings.setANodes(i);
                    settings.setBNodes(e);
                    settings.setCNodes(f);
                    settingses.add(settings);
                }
            }
        }
        return settingses;
    }
}

package hu.xannosz.local.rerouting.graph;

import hu.xannosz.local.rerouting.core.Network;
import hu.xannosz.local.rerouting.core.interfaces.GraphType;
import hu.xannosz.local.rerouting.core.launcher.GraphSettingsPanel;
import lombok.Data;
import org.graphstream.graph.Node;

import javax.swing.*;
import java.awt.*;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@hu.xannosz.local.rerouting.core.annotation.GraphType
public class Squared implements GraphType<Squared.Settings> {
    @Override
    public Network createGraph(Settings settings) {
        Network graph = new Network(getName(), settings.height * settings.width);
        List<Node> node = graph.getNodesFromInterval(0, settings.height * settings.width);
        for (int i = 0; i < settings.width; i++) {
            for (int e = 0; e < settings.height; e++) {
                node.get(i * settings.height + e).addAttribute("layout.frozen");
                node.get(i * settings.height + e).addAttribute("xy", i, e);
                if ((i + 1) * settings.height + e < settings.height * settings.width) {
                    graph.addEdge("E: " + node.get(i * settings.height + e) + " -> " + node.get((i + 1) * settings.height + e), node.get(i * settings.height + e), node.get((i + 1) * settings.height + e));
                }
                if (i * settings.height + e + 1 < settings.height * settings.width && e + 1 < settings.height) {
                    graph.addEdge("E: " + node.get(i * settings.height + e) + " -> " + node.get(i * settings.height + e + 1), node.get(i * settings.height + e), node.get(i * settings.height + e + 1));
                }
            }
        }
        return graph;
    }

    @Override
    public String getName() {
        return "Squared";
    }

    @Override
    public PanelGraph getPanel() {
        return new PanelGraph();
    }

    @Data
    public static class Settings {
        private int width;
        private int height;
    }

    public static class PanelGraph extends GraphSettingsPanel<Settings> {
        private final JSpinner spinnerWidth = new JSpinner();
        private final JSpinner spinnerHeight = new JSpinner();

        public PanelGraph() {
            JPanel a = new JPanel();
            a.add(new JLabel("Width: "));
            spinnerWidth.setValue(10);
            a.add(spinnerWidth);
            a.setLayout(new GridLayout(1, 2));
            JPanel b = new JPanel();
            b.add(new JLabel("Height: "));
            spinnerHeight.setValue(10);
            b.add(spinnerHeight);
            b.setLayout(new GridLayout(1, 2));
            add(a);
            add(b);
            setLayout(new GridLayout(2, 1));
        }

        @Override
        public Settings getSettings() {
            Settings result = new Settings();
            result.setWidth((int) spinnerWidth.getValue());
            result.setHeight((int) spinnerHeight.getValue());
            return result;
        }
    }

    @Override
    public Collection<Settings> getSettings() {
        Set<Settings> settingses = new HashSet<>();
        for (int i = 10; i < 100; i += 20) {
            for (int e = 10; e < 100; e += 20) {
                Settings settings = new Settings();
                settings.setWidth(i);
                settings.setHeight(e);
                settingses.add(settings);
            }
        }
        return settingses;
    }
}

package hu.xannosz.local.rerouting.graph;

import hu.xannosz.local.rerouting.core.util.GraphHelper;
import hu.xannosz.local.rerouting.core.interfaces.GraphType;
import hu.xannosz.local.rerouting.core.launcher.SettingsPanel;
import lombok.Data;
import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;

import javax.swing.*;
import java.awt.*;
import java.util.List;

@hu.xannosz.local.rerouting.core.annotation.GraphType
public class Squared implements GraphType<Squared.Settings> {
    @Override
    public Graph createGraph(Settings settings) {
        Graph graph = GraphHelper.createGraph(getName(), settings.height * settings.width);
        List<Node> node = GraphHelper.getNodesFromInterval(graph, 0, settings.height * settings.width);
        for (int i = 0; i < settings.width; i++) {
            for (int e = 0; e < settings.height; e++) {
                node.get(i * settings.height + e).addAttribute("layout.frozen");
                node.get(i * settings.height + e).addAttribute("xy", i, e);
                if ((i + 1) * settings.height + e < settings.height * settings.width) {
                    graph.addEdge("E: " + node.get(i * settings.height + e) + " -> " + node.get((i + 1) * settings.height + e), node.get(i * settings.height + e), node.get((i + 1) * settings.height + e));
                }
                if (i * settings.height + e + 1 < settings.height * settings.width&&e+1<settings.height) {
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
    public Panel getPanel() {
        return new Panel();
    }

    @Data
    public static class Settings {
        private int width;
        private int height;
    }

    public static class Panel extends SettingsPanel<Settings> {
        private final JSpinner spinnerWidth = new JSpinner();
        private final JSpinner spinnerHeight = new JSpinner();

        public Panel() {
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
}

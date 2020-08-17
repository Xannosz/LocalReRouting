package hu.xannosz.local.rerouting.graph;

import hu.xannosz.local.rerouting.core.util.GraphHelper;
import hu.xannosz.local.rerouting.core.interfaces.GraphType;
import hu.xannosz.local.rerouting.core.launcher.SettingsPanel;
import lombok.Data;
import org.graphstream.graph.Graph;

import javax.swing.*;
import java.awt.*;

@hu.xannosz.local.rerouting.core.annotation.GraphType
public class ErdosRenyi implements GraphType<ErdosRenyi.Settings> {
    @Override
    public Graph createGraph(Settings settings) {
        Graph result = GraphHelper.createGraph(getName(), settings.nodes);
        GraphHelper.createErdosRenyiGraph(result, GraphHelper.getNodesFromInterval(result, 0, settings.nodes), settings.p, 0, 0);
        return result;
    }

    @Override
    public String getName() {
        return "Erdős–Rényí";
    }

    @Override
    public Panel getPanel() {
        return new Panel();
    }

    @Data
    public static class Settings {
        private int nodes;
        private int p;
    }

    public static class Panel extends SettingsPanel<Settings> {

        private final JSpinner spinnerA = new JSpinner();
        private final JSpinner spinnerB = new JSpinner();

        public Panel() {
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
}

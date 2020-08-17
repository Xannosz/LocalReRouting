package hu.xannosz.local.rerouting.graph;

import hu.xannosz.local.rerouting.core.util.GraphHelper;
import hu.xannosz.local.rerouting.core.interfaces.GraphType;
import hu.xannosz.local.rerouting.core.launcher.GraphSettingsPanel;
import lombok.Data;
import org.graphstream.graph.Graph;

import javax.swing.*;
import java.awt.*;

@hu.xannosz.local.rerouting.core.annotation.GraphType
public class Complete implements GraphType<Complete.Settings> {
    @Override
    public Graph createGraph(Settings settings) {
        Graph result = GraphHelper.createGraph(getName(), settings.nodes);
        GraphHelper.createCompleteGraph(result, GraphHelper.getNodesFromInterval(result, 0, settings.nodes), 0, 0);
        return result;
    }

    @Override
    public String getName() {
        return "Complete";
    }

    @Override
    public PanelGraph getPanel() {
        return new PanelGraph();
    }

    @Data
    public static class Settings {
        private int nodes;
    }

    public static class PanelGraph extends GraphSettingsPanel<Settings> {

        private final JSpinner spinner = new JSpinner();

        public PanelGraph() {
            add(new JLabel("Nodes: "));
            spinner.setValue(10);
            add(spinner);
            setLayout(new GridLayout(1, 2));
        }

        @Override
        public Settings getSettings() {
            Settings result = new Settings();
            result.setNodes((int) spinner.getValue());
            return result;
        }
    }
}

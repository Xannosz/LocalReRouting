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
public class Complete implements GraphType<Complete.Settings> {
    @Override
    public Network createGraph(Settings settings) {
        Network result = new Network(getName(), settings.nodes);
        GraphHelper.createCompleteGraph(result, result.getNodesFromInterval(0, settings.nodes), 0, 0);
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

    @Override
    public Collection<Settings> getSettings() {
        Set<Settings> settingses = new HashSet<>();
        for (int i = 10; i < 2000; i += 100) {
            Settings settings = new Settings();
            settings.setNodes(i);
            settingses.add(settings);
        }
        return settingses;
    }
}

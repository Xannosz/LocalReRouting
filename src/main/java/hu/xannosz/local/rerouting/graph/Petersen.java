package hu.xannosz.local.rerouting.graph;

import hu.xannosz.local.rerouting.core.graph.GraphHelper;
import hu.xannosz.local.rerouting.core.graph.GraphType;
import hu.xannosz.local.rerouting.core.launcher.SettingsPanel;
import org.graphstream.graph.Graph;

public class Petersen implements GraphType<Petersen.Settings> {
    @Override
    public Graph createGraph(Settings settings) {
        return GraphHelper.createPetersenGraph(getName());
    }

    @Override
    public String getName() {
        return "Petersen";
    }

    @Override
    public Panel getPanel() {
        return new Panel();
    }

    public static class Settings {

    }

    public static class Panel extends SettingsPanel<Settings> {
        @Override
        public Settings getSettings() {
            return new Settings();
        }
    }
}

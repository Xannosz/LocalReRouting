package hu.xannosz.local.rerouting.graph;

import hu.xannosz.local.rerouting.core.Network;
import hu.xannosz.local.rerouting.core.interfaces.GraphType;
import hu.xannosz.local.rerouting.core.launcher.GraphSettingsPanel;
import hu.xannosz.local.rerouting.core.util.GraphHelper;

import java.util.Collection;
import java.util.Collections;

@hu.xannosz.local.rerouting.core.annotation.GraphType
public class Petersen implements GraphType<Petersen.Settings> {
    @Override
    public Network createGraph(Settings settings) {
        return GraphHelper.createPetersenGraph(getName());
    }

    @Override
    public String getName() {
        return "Petersen";
    }

    @Override
    public PanelGraph getPanel() {
        return new PanelGraph();
    }

    public static class Settings {

    }

    @Override
    public Collection<Settings> getSettings() {
        return Collections.singleton(new Settings());
    }

    public static class PanelGraph extends GraphSettingsPanel<Settings> {
        @Override
        public Settings getSettings() {
            return new Settings();
        }
    }
}

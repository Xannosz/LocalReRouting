package hu.xannosz.local.rerouting.core.graph;

import hu.xannosz.local.rerouting.core.launcher.SettingsPanel;
import org.graphstream.graph.Graph;

public interface GraphType<T> {

    @SuppressWarnings("unchecked")
    default Graph convertAndCreateGraph(Object settings) {
        return createGraph((T) settings);
    }

    Graph createGraph(T settings);

    String getName();

    SettingsPanel<T> getPanel();
}

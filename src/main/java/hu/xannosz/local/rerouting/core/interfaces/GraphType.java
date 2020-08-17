package hu.xannosz.local.rerouting.core.interfaces;

import hu.xannosz.local.rerouting.core.launcher.GraphSettingsPanel;
import org.graphstream.graph.Graph;

public interface GraphType<T> {

    @SuppressWarnings("unchecked")
    default Graph convertAndCreateGraph(Object settings) {
        return createGraph((T) settings);
    }

    Graph createGraph(T settings);

    String getName();

    GraphSettingsPanel<T> getPanel();
}

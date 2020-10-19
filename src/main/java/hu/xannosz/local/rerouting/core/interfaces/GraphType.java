package hu.xannosz.local.rerouting.core.interfaces;

import hu.xannosz.local.rerouting.core.Network;
import hu.xannosz.local.rerouting.core.launcher.GraphSettingsPanel;

public interface GraphType<T> {

    @SuppressWarnings("unchecked")
    default Network convertAndCreateGraph(Object settings) {
        return createGraph((T) settings);
    }

    Network createGraph(T settings);

    String getName();

    GraphSettingsPanel<T> getPanel();
}

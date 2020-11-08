package hu.xannosz.local.rerouting.core.interfaces;

import hu.xannosz.local.rerouting.core.Network;
import hu.xannosz.local.rerouting.core.launcher.FailureGeneratorSettingsPanel;

import java.util.Collection;

public interface FailureGenerator<T> {

    @SuppressWarnings("unchecked")
    default void convertAndCreateFailures(Network graph, Object settings) {
        createFailures(graph, (T) settings);
    }

    void createFailures(Network graph, T settings);

    String getName();

    FailureGeneratorSettingsPanel<T> getPanel();

    Collection<T> getSettings();
}

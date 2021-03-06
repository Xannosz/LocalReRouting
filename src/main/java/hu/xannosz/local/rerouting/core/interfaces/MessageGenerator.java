package hu.xannosz.local.rerouting.core.interfaces;

import hu.xannosz.local.rerouting.core.Network;
import hu.xannosz.local.rerouting.core.algorithm.Message;
import hu.xannosz.local.rerouting.core.launcher.MessageGeneratorSettingsPanel;

import java.util.Collection;
import java.util.Map;
import java.util.Set;

public interface MessageGenerator<T> {

    @SuppressWarnings("unchecked")
    default Map<Integer, Set<Message>> convertAndGetMessages(Network graph, Object settings) {
        return getMessages(graph, (T) settings);
    }

    Map<Integer, Set<Message>> getMessages(Network graph, T settings);

    String getName();

    MessageGeneratorSettingsPanel<T> getPanel();

    Collection<T> getSettings();
}

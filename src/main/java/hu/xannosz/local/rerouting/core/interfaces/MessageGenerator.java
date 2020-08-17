package hu.xannosz.local.rerouting.core.interfaces;

import hu.xannosz.local.rerouting.core.algorithm.Message;
import hu.xannosz.local.rerouting.core.launcher.GeneratorSettingsPanel;
import org.graphstream.graph.Graph;

import java.util.Map;
import java.util.Set;

public interface MessageGenerator<T> {

    @SuppressWarnings("unchecked")
    default Map<Integer, Set<Message>> convertAndGetMessages(Graph graph, Object settings) {
        return getMessages(graph, (T) settings);
    }

    Map<Integer, Set<Message>> getMessages(Graph graph, T settings);

    String getName();

    GeneratorSettingsPanel<T> getPanel();
}

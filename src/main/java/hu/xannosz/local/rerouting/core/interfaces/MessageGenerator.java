package hu.xannosz.local.rerouting.core.interfaces;

import hu.xannosz.local.rerouting.core.algorithm.Message;
import org.graphstream.graph.Graph;

import java.util.Map;
import java.util.Set;

public interface MessageGenerator {
    String getName();

    Map<Integer, Set<Message>> getMessages(Graph graph);
}

package hu.xannosz.local.rerouting.core.statistic;

import hu.xannosz.local.rerouting.core.algorithm.Message;
import org.graphstream.graph.Graph;

import java.util.Map;
import java.util.Set;

public class Visualiser {

    private final Graph graph;

    public Visualiser(Graph graph) {
        this.graph = graph;
    }

    public void update(Map<Integer, Set<Message>> oldMessages, Map<Integer, Set<Message>> newMessages) {
        Map<String, Integer> congestions = StatisticHelper.getConnectionCongestion(oldMessages, newMessages);
        for (Map.Entry<String, Integer> congestion : congestions.entrySet()) {
            if (graph.getEdge(congestion.getKey()) != null) {
                graph.getEdge(congestion.getKey()).addAttribute("ui.style", "size: " + congestion.getValue() + "px; fill-color: red;");
            }
        }
    }
}

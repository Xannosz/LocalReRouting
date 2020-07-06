package hu.xannosz.local.rerouting.core.statistic;

import hu.xannosz.local.rerouting.core.algorithm.Message;
import org.graphstream.graph.Graph;

import java.util.Map;
import java.util.Set;

public class Visualiser implements Statistic {

    private final Graph graph;

    public Visualiser(Graph graph) {
        this.graph = graph;
    }

    @Override
    public void update(Map<Integer, Set<Message>> messages, Map<Integer, Set<Message>> newMessages) {
        Map<String, Integer> congestions = StatisticHelper.getConnectionCongestion(messages, newMessages);
        for (Map.Entry<String, Integer> congestion : congestions.entrySet()) {
            graph.getEdge(congestion.getKey()).addAttribute("ui.style", "size: " + congestion.getValue() + "px; fill-color: red;");
        }
    }
}

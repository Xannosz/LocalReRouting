package hu.xannosz.local.rerouting.core.statistic;

import hu.xannosz.local.rerouting.core.Network;
import hu.xannosz.local.rerouting.core.algorithm.Message;
import hu.xannosz.local.rerouting.core.util.Util;

import java.util.Map;
import java.util.Set;

public class Visualiser {

    private final Network graph;

    public Visualiser(Network graph) {
        this.graph = graph;
    }

    public void update(Map<Integer, Set<Message>> oldMessages, Map<Integer, Set<Message>> newMessages) {
        Map<String, Integer> congestions = Util.getConnectionCongestion(oldMessages, newMessages);
        for (Map.Entry<String, Integer> congestion : congestions.entrySet()) {
            graph.setEdgeSizeAndColor(congestion.getKey(), congestion.getValue(), "red");
        }
    }
}

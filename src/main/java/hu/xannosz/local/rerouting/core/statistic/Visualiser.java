package hu.xannosz.local.rerouting.core.statistic;

import hu.xannosz.local.rerouting.core.Network;
import hu.xannosz.microtools.pack.Douplet;
import org.graphstream.graph.Edge;

import java.util.HashMap;
import java.util.Map;

public class Visualiser {

    private final Network graph;
    private final Map<Integer, String> colors = new HashMap<>();

    public Visualiser(Network graph) {
        this.graph = graph;
        int step = Math.max(255 / graph.getTrees().size(), 1);
        int c = 0;
        for (int tree : graph.getTrees()) {
            colors.put(tree, "rgb(" + c + ",66," + (255 - c) + ")");
            c += step;
        }
    }

    public void update(int tree) {
        for (Edge edge : graph.getEdgeSet()) {
            Douplet<Integer, Integer> nodes = Network.edgeIdToIntInt(edge.getId());
            graph.setEdgeSizeAndColor(edge.getId(), graph.getTreeLabel(nodes.getFirst(), nodes.getSecond(), tree), getColor(tree));
        }
    }

    private String getColor(int i) {
        String color = colors.get(i);
        return color == null ? "black" : color;
    }
}

package hu.xannosz.local.rerouting.core.util;

import hu.xannosz.local.rerouting.core.Network;
import org.graphstream.graph.Node;

import java.util.List;
import java.util.Random;

public class GraphHelper {
    public static Network createCompleteGraph(Network graph, List<Node> nodes, int x, int y) {
        for (int i = 0; i < nodes.size(); i++) {
            graph.addCoordinateToNode(nodes.get(i), x + Math.sin((double) i / nodes.size() * Math.PI * 2), y + Math.cos((double) i / nodes.size() * Math.PI * 2));
        }
        for (int i = 0; i < nodes.size(); i++) {
            for (int e = i; e < nodes.size(); e++) {
                graph.addEdge(nodes.get(i), nodes.get(e));
            }
        }
        return graph;
    }

    public static Network createPairGraph(Network graph, List<Node> aNodes, List<Node> bNodes) {
        return createPairGraph(graph, aNodes, bNodes, 0, 0, 0, false);
    }

    public static Network createPairGraph(Network graph, List<Node> aNodes, List<Node> bNodes, int x, int y, int distance) {
        return createPairGraph(graph, aNodes, bNodes, x, y, distance, true);
    }

    public static Network createPairGraph(Network graph, List<Node> aNodes, List<Node> bNodes, int x, int y, int distance, boolean setLayout) {
        if (setLayout) {
            for (int i = 0; i < aNodes.size(); i++) {
                graph.addCoordinateToNode(aNodes.get(i), x + i, y + distance);
            }
            for (int i = 0; i < bNodes.size(); i++) {
                graph.addCoordinateToNode(bNodes.get(i), x + i, y - distance);
            }
        }
        for (Node aNode : aNodes) {
            for (Node bNode : bNodes) {
                graph.addEdge(aNode, bNode);
            }
        }
        return graph;
    }

    public static Network createPetersenGraph(String id) {
        Network graph = new Network(id, 10);

        for (int i = 1; i < 6; i++) {
            graph.addCoordinateToNode(i, 0 + Math.sin((double) i / 5 * Math.PI * 2) * 3, 0 + Math.cos((double) i / 5 * Math.PI * 2) * 3);
        }
        for (int i = 6; i < 10; i++) {
            graph.addCoordinateToNode(i, 0 + Math.sin((double) i / 5 * Math.PI * 2), 0 + Math.cos((double) i / 5 * Math.PI * 2));
        }
        graph.addCoordinateToNode(0, 0 + Math.sin((double) 0 / 5 * Math.PI * 2), 0 + Math.cos((double) 0 / 5 * Math.PI * 2));

        return graph.addEdge(1, 6).
                addEdge(2, 7).
                addEdge(3, 8).
                addEdge(4, 9).
                addEdge(5, 0).

                addEdge(1, 2).
                addEdge(2, 3).
                addEdge(3, 4).
                addEdge(4, 5).
                addEdge(5, 1).

                addEdge(0, 7).
                addEdge(7, 9).
                addEdge(9, 6).
                addEdge(6, 8).
                addEdge(8, 0);
    }

    public static Network createErdosRenyiGraph(Network graph, List<Node> nodes, int p, int x, int y) {
        Random random = new Random();
        for (int i = 0; i < nodes.size(); i++) {
            graph.addCoordinateToNode(nodes.get(i), x + Math.sin((double) i / nodes.size() * Math.PI * 2), y + Math.cos((double) i / nodes.size() * Math.PI * 2));
        }
        for (int i = 0; i < nodes.size(); i++) {
            for (int e = i; e < nodes.size(); e++) {
                if (random.nextInt(100) < p) {
                    graph.addEdge(nodes.get(i), nodes.get(e));
                }
            }
        }
        return graph;
    }
}

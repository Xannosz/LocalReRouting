package hu.xannosz.local.rerouting.core.util;

import org.graphstream.graph.Edge;
import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;
import org.graphstream.graph.implementations.SingleGraph;

import java.util.*;

public class GraphHelper {
    public static Graph createCompleteGraph(String id, int nodesNumber, int x, int y) {
        Graph graph = new SingleGraph(id);
        List<Node> nodes = new ArrayList<>();
        for (int i = 0; i < nodesNumber; i++) {
            nodes.add(graph.addNode("N: " + i));
        }
        return createCompleteGraph(graph, nodes, x, y);
    }

    public static Graph createCompleteGraph(Graph graph, List<Node> nodes, int x, int y) {
        for (int i = 0; i < nodes.size(); i++) {
            nodes.get(i).addAttribute("layout.frozen");
            nodes.get(i).addAttribute("xy", x + Math.sin((double) i / nodes.size() * Math.PI * 2), y + Math.cos((double) i / nodes.size() * Math.PI * 2));
        }
        for (int i = 0; i < nodes.size(); i++) {
            for (int e = i; e < nodes.size(); e++) {
                graph.addEdge("E: " + nodes.get(i) + " -> " + nodes.get(e), nodes.get(i), nodes.get(e));
            }
        }
        return graph;
    }

    public static Graph createPairGraph(Graph graph, List<Node> aNodes, List<Node> bNodes) {
        return createPairGraph(graph, aNodes, bNodes, 0, 0, 0, false);
    }

    public static Graph createPairGraph(Graph graph, List<Node> aNodes, List<Node> bNodes, int x, int y, int distance) {
        return createPairGraph(graph, aNodes, bNodes, x, y, distance, true);
    }

    public static Graph createPairGraph(Graph graph, List<Node> aNodes, List<Node> bNodes, int x, int y, int distance, boolean setLayout) {
        if (setLayout) {
            for (int i = 0; i < aNodes.size(); i++) {
                aNodes.get(i).addAttribute("layout.frozen");
                aNodes.get(i).addAttribute("xy", x + i, y + distance);
            }
            for (int i = 0; i < bNodes.size(); i++) {
                bNodes.get(i).addAttribute("layout.frozen");
                bNodes.get(i).addAttribute("xy", x + i, y - distance);
            }
        }
        for (int i = 0; i < aNodes.size(); i++) {
            for (int e = 0; e < bNodes.size(); e++) {
                graph.addEdge("E: " + aNodes.get(i) + " -> " + bNodes.get(e), aNodes.get(i), bNodes.get(e));
            }
        }
        return graph;
    }

    public static Graph createPetersenGraph(String id) {
        Graph graph = new SingleGraph(id);
        Node A[] = new Node[10];
        for (int i = 0; i < 10; i++) {
            A[i] = graph.addNode("N: " + i);
            A[i].addAttribute("layout.frozen");
        }

        for (int i = 1; i < 6; i++) {
            A[i].addAttribute("xy", 0 + Math.sin((double) i / 5 * Math.PI * 2) * 3, 0 + Math.cos((double) i / 5 * Math.PI * 2) * 3);
        }
        for (int i = 6; i < 10; i++) {
            A[i].addAttribute("xy", 0 + Math.sin((double) i / 5 * Math.PI * 2), 0 + Math.cos((double) i / 5 * Math.PI * 2));
        }
        A[0].addAttribute("xy", 0 + Math.sin((double) 0 / 5 * Math.PI * 2), 0 + Math.cos((double) 0 / 5 * Math.PI * 2));

        addEdge(graph, A, 1, 6);
        addEdge(graph, A, 2, 7);
        addEdge(graph, A, 3, 8);
        addEdge(graph, A, 4, 9);
        addEdge(graph, A, 5, 0);

        addEdge(graph, A, 1, 2);
        addEdge(graph, A, 2, 3);
        addEdge(graph, A, 3, 4);
        addEdge(graph, A, 4, 5);
        addEdge(graph, A, 5, 1);

        addEdge(graph, A, 0, 7);
        addEdge(graph, A, 7, 9);
        addEdge(graph, A, 9, 6);
        addEdge(graph, A, 6, 8);
        addEdge(graph, A, 8, 0);
        return graph;
    }

    public static List<Node> getNodesFromInterval(Graph graph, int start, int end) {
        List<Node> result = new ArrayList<>();
        for (int i = start; i < end; i++) {
            result.add(graph.getNode("N: " + i));
        }
        return result;
    }

    public static List<Node> getNodesFromIds(Graph graph, int... ids) {
        List<Node> result = new ArrayList<>();
        for (int i : ids) {
            result.add(graph.getNode("N: " + i));
        }
        return result;
    }

    public static List<Node> concatLists(List<Node>... lists) {
        Set<Node> result = new HashSet<>();
        for (List<Node> list : lists) {
            result.addAll(list);
        }
        return new ArrayList<>(result);
    }

    public static Graph createGraph(String id, int nodes) {
        Graph graph = new SingleGraph(id);
        for (int i = 0; i < nodes; i++) {
            graph.addNode("N: " + i);
        }
        return graph;
    }

    private static void addEdge(Graph graph, Node[] nodes, int i, int e) {
        graph.addEdge("E: N: " + i + " -> N: " + e, nodes[i], nodes[e]);
    }

    public static Graph createErdosRenyiGraph(Graph graph, List<Node> nodes, int p, int x, int y) {
        Random random = new Random();
        for (int i = 0; i < nodes.size(); i++) {
            nodes.get(i).addAttribute("layout.frozen");
            nodes.get(i).addAttribute("xy", x + Math.sin((double) i / nodes.size() * Math.PI * 2), y + Math.cos((double) i / nodes.size() * Math.PI * 2));
        }
        for (int i = 0; i < nodes.size(); i++) {
            for (int e = i; e < nodes.size(); e++) {
                if (random.nextInt(100) < p) {
                    graph.addEdge("E: " + nodes.get(i) + " -> " + nodes.get(e), nodes.get(i), nodes.get(e));
                }
            }
        }
        return graph;
    }

    public static Set<Integer> getConnects(int node, Graph graph) {
        Node from = getNodesFromIds(graph, node).get(0);
        Set<Integer> result = new HashSet<>();
        for (Edge edge : from.getEdgeSet()) {
            result.add(Integer.parseInt(edge.getOpposite(from).toString().substring(3)));
        }
        return result;
    }
}

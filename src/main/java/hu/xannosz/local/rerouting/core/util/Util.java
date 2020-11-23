package hu.xannosz.local.rerouting.core.util;

import hu.xannosz.local.rerouting.core.Network;
import hu.xannosz.local.rerouting.core.algorithm.Message;
import hu.xannosz.microtools.pack.Douplet;
import org.graphstream.algorithm.Kruskal;
import org.graphstream.graph.Edge;
import org.graphstream.graph.Node;
import org.graphstream.graph.implementations.Graphs;

import java.util.*;

public class Util {
    public static List<List<Integer>> getPermutations(List<Integer> list, int maxResults) {
        List<List<Integer>> result = new ArrayList<>();

        Integer[] indexes = new Integer[list.size()];
        Integer[] elements = list.toArray(new Integer[0]);
        for (int i = 0; i < list.size(); i++) {
            indexes[i] = 0;
        }

        result.add(new ArrayList<>(Arrays.asList(elements)));

        int i = 0;
        while (i < list.size()) {
            if (indexes[i] < i) {
                swap(elements, i % 2 == 0 ? 0 : indexes[i], i);
                result.add(new ArrayList<>(Arrays.asList(elements)));
                indexes[i]++;
                i = 0;
            } else {
                indexes[i] = 0;
                i++;
            }
            if (result.size() >= maxResults) {
                break;
            }
        }

        return result;
    }

    private static <T> void swap(T[] input, int a, int b) {
        T tmp = input[a];
        input[a] = input[b];
        input[b] = tmp;
    }

    public static List<Integer> removeItemFromList(List<Integer> list, int remove) {
        List<Integer> result = new ArrayList<>();
        for (int i : list) {
            if (i != remove) {
                result.add(i);
            }
        }
        return result;
    }

    public static Map<Integer, Set<Integer>> getReachableNodes(int node, Network graph) {
        Map<Integer, Set<Integer>> result = new HashMap<>();

        Iterator<Node> iterator = graph.getNodeFromInt(node).getNeighborNodeIterator();
        while (iterator.hasNext()) {
            result.put(Network.getNodeNumber(iterator.next()), new HashSet<>());
        }
        for (Node i : graph.getNodeSet()) {
            if (Network.getNodeNumber(i) != node) {
                iterator = graph.getNodeFromInt(node).getNeighborNodeIterator();
                while (iterator.hasNext()) {
                    int neighborNode = Network.getNodeNumber(iterator.next());
                    Set<Integer> forbidden = new HashSet<>();
                    forbidden.add(node);
                    if (reachable(forbidden, neighborNode, Network.getNodeNumber(i), graph)) {
                        result.get(neighborNode).add(Network.getNodeNumber(i));
                    }
                }
            }
        }
        return result;
    }

    private static boolean reachable(Set<Integer> forbidden, int start, int target, Network graph) {
        for (int node : graph.getConnects(start)) {
            if (node == target) {
                return true;
            }
            if (!forbidden.contains(node)) {
                forbidden.add(node);
                if (reachable(forbidden, node, target, graph)) {
                    return true;
                }
            }
        }
        return false;
    }

    public static void bfs(Network graph) {
        Node start = new ArrayList<Node>(graph.getNodeSet()).get((new Random()).nextInt(graph.getNodeSet().size()));
        start.setAttribute("reached", "reached");
        bfs(start, graph);
    }

    private static void bfs(Node node, Network graph) {
        Iterator<Node> iterator = node.getNeighborNodeIterator();
        Set<Node> nextNodes = new HashSet<>();
        while (iterator.hasNext()) {
            Node next = iterator.next();
            if (next.getAttribute("reached") == null) {
                next.setAttribute("reached", "reached");
                nextNodes.add(next);
                graph.getEdge(Network.getNodeNumber(node), Network.getNodeNumber(next)).setAttribute("treeFlag", "inTree");
            } else {
                if (!graph.getEdge(Network.getNodeNumber(node), Network.getNodeNumber(next)).hasAttribute("treeFlag")) {
                    graph.getEdge(Network.getNodeNumber(node), Network.getNodeNumber(next)).setAttribute("treeFlag", "notInTree");
                }
            }
        }
        for (Node next : nextNodes) {
            bfs(next, graph);
        }
    }

    public static void dfs(Network graph) {
        Node start = new ArrayList<Node>(graph.getNodeSet()).get((new Random()).nextInt(graph.getNodeSet().size()));
        start.setAttribute("reached", "reached");
        dfs(start, graph);
    }

    private static void dfs(Node node, Network graph) {
        Iterator<Node> iterator = node.getNeighborNodeIterator();
        while (iterator.hasNext()) {
            Node next = iterator.next();
            if (next.getAttribute("reached") == null) {
                next.setAttribute("reached", "reached");
                graph.getEdge(Network.getNodeNumber(node), Network.getNodeNumber(next)).setAttribute("treeFlag", "inTree");
                dfs(next, graph);
            } else {
                if (!graph.getEdge(Network.getNodeNumber(node), Network.getNodeNumber(next)).hasAttribute("treeFlag")) {
                    graph.getEdge(Network.getNodeNumber(node), Network.getNodeNumber(next)).setAttribute("treeFlag", "notInTree");
                }
            }
        }
    }

    public static LatinSquare createLatinSquare(int n) {
        LatinSquare latinSquare = new LatinSquare(n);
        for (int i = 0; i < n; i++) {
            latinSquare.permuteRows();
            latinSquare.permuteColumns();
        }
        return latinSquare;
    }

    public static Set<LatinSquare> createLatinSquares(int n) {
        Set<LatinSquare> latinSquares = new HashSet<>();
        LatinSquare latinSquare = createLatinSquare(n);
        while (latinSquares.size() < n) {
            for (int i = 0; i < n; i++) {
                latinSquare.swapSymbols(0, i); //shift all symbol
            }
            latinSquares.add(new LatinSquare(latinSquare));
        }
        return latinSquares;
    }

    public static int getCirclesNumber(Message message) {
        Map<Integer, Integer> numbers = new HashMap<>();
        for (Douplet<Integer, Integer> nodes : message.visitedNodesMap) {
            if (!numbers.containsKey(nodes.getSecond())) {
                numbers.put(nodes.getSecond(), 0);
            } else {
                numbers.put(nodes.getSecond(), numbers.get(nodes.getSecond()) + 1);
            }
        }
        int result = 0;
        for (Map.Entry<Integer, Integer> entry : numbers.entrySet()) {
            result += entry.getValue();
        }
        return result;
    }

    public static void getCriticalWeights(Network graph) {
        for (Edge edge : graph.getEdgeSet()) {
            edge.setAttribute("weight", 1);
        }
        for (int num = 0; num < graph.getNodeCount(); num++) {
            Network labelled = (Network) Graphs.clone(graph);

            Kruskal kruskal = new Kruskal("weight", "treeFlag", "inTree", "notInTree");

            kruskal.init(labelled);
            kruskal.compute();

            Network trunked = (Network) Graphs.clone(labelled);

            Set<Edge> edges = new HashSet<>(trunked.getEdgeSet());
            for (Edge edge : edges) {
                if (!edge.hasAttribute("treeFlag") || edge.getAttribute("treeFlag").equals("notInTree")) {
                    trunked.removeEdge(edge);
                } else {
                    graph.getEdge(edge.getId()).setAttribute("weight", (int) graph.getEdge(edge.getId()).getAttribute("weight") + 1);
                }
            }
        }
    }

    public static Map<Integer, Set<Edge>> getCriticalEdges(Network network) {
        Map<Integer, Set<Edge>> edges = new HashMap<>();
        for (Edge edge : network.getEdgeSet()) {
            int weight = edge.getAttribute("weight");
            edges.computeIfAbsent(weight, k -> new HashSet<>());
            edges.get(weight).add(edge);
        }
        return edges;
    }
}

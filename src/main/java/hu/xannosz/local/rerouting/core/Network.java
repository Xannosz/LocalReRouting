package hu.xannosz.local.rerouting.core;

import hu.xannosz.microtools.pack.Douplet;
import org.graphstream.graph.Edge;
import org.graphstream.graph.Node;
import org.graphstream.graph.implementations.DefaultGraph;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Network extends DefaultGraph {
    public Network(String id) {
        super(id);
    }

    public Network(String id, int nodeNumber) {
        super(id);
        IntStream.range(0, nodeNumber).forEach(this::addNode);
    }

    public void clearAllLabel() {

    }

    public void accommodatePath(int s, int t) {

    }

    public Node getNodeFromInt(int id) {
        return getNode(intToId(id));
    }

    public Network addNode(int id) {
        addNode(intToId(id));
        return this;
    }

    public Edge getEdge(int a, int b) {
        return getEdge(intIntToEdgeId(a, b));
    }

    public Network addEdge(int a, int b) {
        addEdge(intIntToEdgeId(a, b), getNodeFromInt(a), getNodeFromInt(b));
        return this;
    }

    public Edge getEdge(Node a, Node b) {
        return getEdge(intIntToEdgeId(getNodeNumber(a), getNodeNumber(b)));
    }

    public Network addEdge(Node a, Node b) {
        addEdge(intIntToEdgeId(getNodeNumber(a), getNodeNumber(b)), a, b);
        return this;
    }

    public Network addCoordinateToNode(int node, double x, double y) {
        return addCoordinateToNode(getNodeFromInt(node), x, y);
    }

    public Network addCoordinateToNode(Node node, double x, double y) {
        node.addAttribute("layout.frozen");
        node.addAttribute("xy", x, y);
        return this;
    }

    public Set<Integer> getIntegerNodeSet() {
        return getNodeSet().stream().map(Network::getNodeNumber).collect(Collectors.toSet());
    }

    public void setEdgeSizeAndColor(String key, int size, String color) {
        if (getEdge(key) != null) {
            getEdge(key).addAttribute("ui.style", "size: " + size + "px; fill-color: " + color + ";");
        }
    }

    public Set<Integer> getConnects(int node) {
        Set<Integer> result = new HashSet<>();
        getNodeFromInt(node).getNeighborNodeIterator().forEachRemaining(n -> result.add(idToInt(n.getId())));
        return result;
    }

    public List<Node> getNodesFromInterval(int start, int end) {
        List<Node> result = new ArrayList<>();
        IntStream.range(start, end).forEach(n -> result.add(this.getNodeFromInt(n)));
        return result;
    }

    public static int getNodeNumber(Node node) {
        return idToInt(node.getId());
    }

    public static int idToInt(String id) {
        return Integer.parseInt(id.substring(3));
    }

    public static String intToId(int id) {
        return "N: " + id;
    }

    public static String intIntToEdgeId(int a, int b) {
        return "E: " + intToId(a) + " -> " + intToId(b);
    }

    public static Douplet<Integer, Integer> edgeIdToIntInt(String edgeId) {
        String[] split = edgeId.split("->");
        return new Douplet<>(idToInt(split[0].substring(3).trim()), idToInt(split[1].trim()));
    }
}

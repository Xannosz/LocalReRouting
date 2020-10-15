package hu.xannosz.local.rerouting.core.util;

import org.graphstream.algorithm.Dijkstra;
import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;

import java.util.*;

public class TreeUtil {
    public static Map<Integer, Set<Integer>> getReachableNodes(int node, Graph graph) {
        Map<Integer, Set<Integer>> result = new HashMap<>();
        Dijkstra dijkstra = new Dijkstra(Dijkstra.Element.EDGE_AND_NODE, "result", "length");
        dijkstra.init(graph);
        dijkstra.setSource(graph.getNode("N: " + node));
        dijkstra.compute();
        Iterator<Node> iterator = graph.getNode("N: " + node).getNeighborNodeIterator();
        while (iterator.hasNext()) {
            result.put(getNodeNumber(iterator.next()), new HashSet<>());
        }
        for (Node i : graph.getNodeSet()) {
            if (getNodeNumber(i) != node) {
               System.out.println("###:" + dijkstra);
                System.out.println("###:" + dijkstra.getPath(i));
                System.out.println("###:" + dijkstra.getPath(i).getNodePath());
                List<Node> list = dijkstra.getPath(i).getNodePath();
                if (list.size() > 0) {
                    result.get(getNodeNumber(list.get(0))).add(getNodeNumber(i));
                }
            }
        }
        return result;
    }

    public static int getNodeNumber(Node node) {
        return Integer.parseInt(node.getId().substring(3));
    }
}

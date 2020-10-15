package hu.xannosz.local.rerouting.algorithm;

import hu.xannosz.local.rerouting.core.algorithm.ListRoutingTable;
import hu.xannosz.local.rerouting.core.algorithm.ListRoutingTableUser;
import hu.xannosz.local.rerouting.core.interfaces.Algorithm;
import hu.xannosz.local.rerouting.core.interfaces.MatrixCreator;
import hu.xannosz.local.rerouting.core.interfaces.ReRouter;
import hu.xannosz.local.rerouting.core.util.TreeUtil;
import org.graphstream.algorithm.Kruskal;
import org.graphstream.graph.Edge;
import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;
import org.graphstream.graph.implementations.Graphs;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import static hu.xannosz.local.rerouting.core.util.TreeUtil.getNodeNumber;

@hu.xannosz.local.rerouting.core.annotation.Algorithm
public class KruskalAlgorithm implements Algorithm<ListRoutingTable.RoutingTable> {
    @Override
    public String getName() {
        return "Kruskal";
    }

    @Override
    public MatrixCreator<ListRoutingTable.RoutingTable> getCreator() {
        return new Creator();
    }

    @Override
    public ReRouter<ListRoutingTable.RoutingTable> getReRouter() {
        return new ListRoutingTableUser();
    }

    public static class Creator implements MatrixCreator<ListRoutingTable.RoutingTable> {
        @Override
        public Map<Integer, ListRoutingTable.RoutingTable> createMatrices(Graph graph) {
            ListRoutingTable routingTable = new ListRoutingTable();
            for (int num = 0; num < 10; num++) {
                Kruskal kruskal = new Kruskal("ui.class", "inTree", "notInTree");

                kruskal.init(graph);
                kruskal.compute();

                Graph trunked = Graphs.clone(graph);

                Set<Edge> edges = new HashSet<>(trunked.getEdgeSet());
                for (Edge edge : edges) {
                    if (edge.getAttribute("ui.class").equals("notInTree") || !edge.hasAttribute("ui.class")) {
                        trunked.removeEdge(edge);
                    }
                }
                for (Node i : graph.getNodeSet()) {
                    Map<Integer, Set<Integer>> nodes = TreeUtil.getReachableNodes(getNodeNumber(i), trunked);
                    for (Map.Entry<Integer, Set<Integer>> list : nodes.entrySet()) {
                        for (int target : list.getValue()) {
                            routingTable.addRouting(getNodeNumber(i), target, list.getKey());
                        }
                    }
                }
            }
            return routingTable.getRouting();
        }
    }
}

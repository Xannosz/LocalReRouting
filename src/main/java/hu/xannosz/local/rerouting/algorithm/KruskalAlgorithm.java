package hu.xannosz.local.rerouting.algorithm;

import hu.xannosz.local.rerouting.core.Network;
import hu.xannosz.local.rerouting.core.algorithm.ListRoutingTable;
import hu.xannosz.local.rerouting.core.algorithm.ListRoutingTableUser;
import hu.xannosz.local.rerouting.core.interfaces.Algorithm;
import hu.xannosz.local.rerouting.core.interfaces.MatrixCreator;
import hu.xannosz.local.rerouting.core.interfaces.ReRouter;
import hu.xannosz.local.rerouting.core.util.Util;
import org.graphstream.algorithm.Kruskal;
import org.graphstream.graph.Edge;
import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;
import org.graphstream.graph.implementations.Graphs;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@hu.xannosz.local.rerouting.core.annotation.Algorithm
public class KruskalAlgorithm implements Algorithm<ListRoutingTable.RoutingTable> {
    @Override
    public String getName() {
        return "Kruskal";
    }

    @Override
    public MatrixCreator getCreator() {
        return new Creator();
    }

    @Override
    public ReRouter getReRouter() {
        return new ListRoutingTableUser();
    }

    public static class Creator implements MatrixCreator {
        @Override
        public ListRoutingTable createMatrices(Network graph) {
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
                    Map<Integer, Set<Integer>> nodes = Util.getReachableNodes(Network.getNodeNumber(i), trunked);
                    for (Map.Entry<Integer, Set<Integer>> list : nodes.entrySet()) {
                        for (int target : list.getValue()) {
                            routingTable.addRouting(Network.getNodeNumber(i), target, list.getKey());
                        }
                    }
                }
            }
            return routingTable;
        }
    }
}

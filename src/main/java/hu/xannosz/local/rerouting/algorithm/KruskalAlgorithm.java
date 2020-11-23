package hu.xannosz.local.rerouting.algorithm;

import hu.xannosz.local.rerouting.core.Network;
import hu.xannosz.local.rerouting.core.algorithm.ReroutingMatrixList;
import hu.xannosz.local.rerouting.core.interfaces.Algorithm;
import hu.xannosz.local.rerouting.core.interfaces.MatrixCreator;
import hu.xannosz.local.rerouting.core.util.Util;
import org.graphstream.algorithm.Kruskal;
import org.graphstream.graph.Edge;
import org.graphstream.graph.Node;
import org.graphstream.graph.implementations.Graphs;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@hu.xannosz.local.rerouting.core.annotation.Algorithm
public class KruskalAlgorithm implements Algorithm {
    @Override
    public String getName() {
        return "Kruskal";
    }

    @Override
    public MatrixCreator getCreator() {
        return new Creator();
    }

    public static class Creator implements MatrixCreator {
        @Override
        public ReroutingMatrixList createMatrices(Network graph) {
            ReroutingMatrixList routingTable = new ReroutingMatrixList();
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
                        graph.getEdge(edge.getId()).setAttribute("weight", (int) graph.getEdge(edge.getId()).getAttribute("weight") + 100);
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

package hu.xannosz.local.rerouting.algorithm;

import hu.xannosz.local.rerouting.core.Network;
import hu.xannosz.local.rerouting.core.algorithm.ReroutingMatrixList;
import hu.xannosz.local.rerouting.core.interfaces.Algorithm;
import hu.xannosz.local.rerouting.core.interfaces.MatrixCreator;
import hu.xannosz.local.rerouting.core.launcher.AlgorithmSettingsPanel;
import hu.xannosz.local.rerouting.core.util.Util;
import org.graphstream.graph.Edge;
import org.graphstream.graph.Node;
import org.graphstream.graph.implementations.Graphs;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@hu.xannosz.local.rerouting.core.annotation.Algorithm
public class DFS implements Algorithm {
    @Override
    public String getName() {
        return "DFS";
    }

    @Override
    public MatrixCreator getCreator(AlgorithmSettingsPanel.Settings settings) {
        return new Creator(settings);
    }

    public static class Creator implements MatrixCreator {

        private final AlgorithmSettingsPanel.Settings settings;

        public Creator(AlgorithmSettingsPanel.Settings settings) {
            this.settings = settings;
        }

        @Override
        public ReroutingMatrixList createMatrices(Network graph) {
            ReroutingMatrixList routingTable = new ReroutingMatrixList();
            for (int num = 0; num < graph.getNodeCount(); num++) {
                Network labelled = (Network) Graphs.clone(graph);

                Util.dfs(labelled);

                Network trunked = (Network) Graphs.clone(labelled);

                Set<Edge> edges = new HashSet<>(trunked.getEdgeSet());
                for (Edge edge : edges) {
                    if (!edge.hasAttribute("treeFlag") || edge.getAttribute("treeFlag").equals("notInTree")) {
                        trunked.removeEdge(edge);
                    }
                }
                for (Node i : trunked.getNodeSet()) {
                    Map<Integer, Set<Integer>> nodes = Util.getReachableNodes(Network.getNodeNumber(i), trunked);
                    for (Map.Entry<Integer, Set<Integer>> list : nodes.entrySet()) {
                        for (int target : list.getValue()) {
                            routingTable.addRouting(Network.getNodeNumber(i), target, list.getKey());
                        }
                    }
                }
            }
            routingTable.setGenre(ReroutingMatrixList.Genre.TREE_MODEL);

            routingTable.setMultiTrees(settings.isUseMultiTree());
            routingTable.setUseCongestionBorder(settings.isUseCongestionBorder());
            return routingTable;
        }
    }
}

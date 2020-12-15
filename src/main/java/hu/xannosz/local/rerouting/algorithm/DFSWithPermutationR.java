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
public class DFSWithPermutationR implements Algorithm {
    @Override
    public String getName() {
        return "DFS With Permutation R";
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
            ReroutingMatrixList result = new ReroutingMatrixList();

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
                            for (int r = 0; r < 5; r++) {
                                result.addRouting(Network.getNodeNumber(i) * 5 + r, target, list.getKey());
                            }
                        }
                    }
                }
            }

            Util.createBIBDk(graph, result, graph.getNodeCount(), 5);
            result.setGenre(ReroutingMatrixList.Genre.HYBRID);

            result.setUseRandomization(true);
            result.setMultiTrees(settings.isUseMultiTree());
            result.setUseCongestionBorder(settings.isUseCongestionBorder());
            return result;
        }
    }
}

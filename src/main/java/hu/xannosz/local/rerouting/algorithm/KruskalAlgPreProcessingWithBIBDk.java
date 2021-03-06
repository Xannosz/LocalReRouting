package hu.xannosz.local.rerouting.algorithm;

import hu.xannosz.local.rerouting.core.Network;
import hu.xannosz.local.rerouting.core.algorithm.ReroutingMatrixList;
import hu.xannosz.local.rerouting.core.interfaces.Algorithm;
import hu.xannosz.local.rerouting.core.interfaces.MatrixCreator;
import hu.xannosz.local.rerouting.core.launcher.AlgorithmSettingsPanel;
import hu.xannosz.local.rerouting.core.util.Util;
import org.graphstream.graph.implementations.Graphs;

@hu.xannosz.local.rerouting.core.annotation.Algorithm
public class KruskalAlgPreProcessingWithBIBDk implements Algorithm {
    @Override
    public String getName() {
        return "Kruskal With PP & BIBD k";
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
            Network innerGraph = (Network) Graphs.clone(graph);
            Util.getCriticalWeights(innerGraph);
            ReroutingMatrixList kruskalReroutingMatrixList = Util.createKruskalReroutingMatrixList(innerGraph);

            ReroutingMatrixList result = new ReroutingMatrixList();

            for (int n = 0; n < graph.getNodeCount() * 5; n += 5) {
                for (int next = 0; next < graph.getNodeCount() * 5; next++) {
                    for (int r = 0; r < 5; r++) {
                        result.addRouting(n + r, next, kruskalReroutingMatrixList.getRouting(n, next));
                    }
                }
            }


            Util.createBIBDk(graph, result, 5, 5);
            result.setGenre(ReroutingMatrixList.Genre.HYBRID);

            result.setUseRandomization(true);
            result.setMultiTrees(settings.isUseMultiTree());
            result.setUseCongestionBorder(settings.isUseCongestionBorder());
            return result;
        }
    }
}

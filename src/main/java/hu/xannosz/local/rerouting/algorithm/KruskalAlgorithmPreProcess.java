package hu.xannosz.local.rerouting.algorithm;

import hu.xannosz.local.rerouting.core.Network;
import hu.xannosz.local.rerouting.core.algorithm.ReroutingMatrixList;
import hu.xannosz.local.rerouting.core.interfaces.Algorithm;
import hu.xannosz.local.rerouting.core.interfaces.MatrixCreator;
import hu.xannosz.local.rerouting.core.launcher.AlgorithmSettingsPanel;
import hu.xannosz.local.rerouting.core.util.Util;
import org.graphstream.graph.implementations.Graphs;

@hu.xannosz.local.rerouting.core.annotation.Algorithm
public class KruskalAlgorithmPreProcess implements Algorithm {
    @Override
    public String getName() {
        return "Kruskal With Preprocess";
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

            ReroutingMatrixList result = Util.createKruskalReroutingMatrixList(innerGraph);
            result.setGenre(ReroutingMatrixList.Genre.TREE_MODEL);

            result.setMultiTrees(settings.isUseMultiTree());
            result.setUseCongestionBorder(settings.isUseCongestionBorder());
            return result;
        }
    }
}

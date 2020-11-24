package hu.xannosz.local.rerouting.algorithm;

import hu.xannosz.local.rerouting.core.Network;
import hu.xannosz.local.rerouting.core.algorithm.ReroutingMatrixList;
import hu.xannosz.local.rerouting.core.interfaces.Algorithm;
import hu.xannosz.local.rerouting.core.interfaces.MatrixCreator;
import hu.xannosz.local.rerouting.core.launcher.AlgorithmSettingsPanel;
import hu.xannosz.local.rerouting.core.util.LatinSquare;
import hu.xannosz.local.rerouting.core.util.Util;

@hu.xannosz.local.rerouting.core.annotation.Algorithm
public class AllToOne implements Algorithm {
    @Override
    public String getName() {
        return "All To One";
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
            int n = graph.getNodeCount();
            LatinSquare square = Util.createLatinSquare(n);

            for (int i = 0; i < n; i++) {
                for (int x = 0; x < n; x++) {
                    for (int y = 0; y < n; y++) {
                        result.addRouting(i, x, square.getItem(x, y));
                    }
                }
            }

            result.setMultiTrees(settings.isUseMultiTree());
            result.setUseCongestionBorder(settings.isUseCongestionBorder());
            return result;
        }
    }
}

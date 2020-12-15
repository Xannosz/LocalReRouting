package hu.xannosz.local.rerouting.algorithm;

import hu.xannosz.local.rerouting.core.Network;
import hu.xannosz.local.rerouting.core.algorithm.ReroutingMatrixList;
import hu.xannosz.local.rerouting.core.interfaces.Algorithm;
import hu.xannosz.local.rerouting.core.interfaces.MatrixCreator;
import hu.xannosz.local.rerouting.core.launcher.AlgorithmSettingsPanel;
import hu.xannosz.local.rerouting.core.util.Util;

import java.util.Set;

@hu.xannosz.local.rerouting.core.annotation.Algorithm
public class RandomWithRandomization implements Algorithm {
    @Override
    public String getName() {
        return "Random With Randomization";
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

            Set<Integer> nodes = graph.getIntegerNodeSet();

            for (int from : nodes) {
                for (int next : nodes) {
                    for (int i = 0; i < 5; i++) {
                        result.setRoutingList(from, next, Util.getRandomList(nodes));
                    }
                }
            }

            result.setUseRandomization(true);
            result.setMultiTrees(settings.isUseMultiTree());
            result.setUseCongestionBorder(settings.isUseCongestionBorder());
            return result;
        }
    }
}

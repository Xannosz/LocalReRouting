package hu.xannosz.local.rerouting.algorithm;

import hu.xannosz.local.rerouting.core.Network;
import hu.xannosz.local.rerouting.core.algorithm.ReroutingMatrixList;
import hu.xannosz.local.rerouting.core.interfaces.Algorithm;
import hu.xannosz.local.rerouting.core.interfaces.MatrixCreator;
import hu.xannosz.local.rerouting.core.launcher.AlgorithmSettingsPanel;
import hu.xannosz.local.rerouting.core.util.Util;

public abstract class BIBDk implements Algorithm {

    private final int k;

    public BIBDk(int k) {
        this.k = k;
    }

    @Override
    public String getName() {
        return "BIBD " + k;
    }

    @Override
    public MatrixCreator getCreator(AlgorithmSettingsPanel.Settings settings) {
        return new Creator(k, settings);
    }

    public static class Creator implements MatrixCreator {

        private final int k;
        private final AlgorithmSettingsPanel.Settings settings;

        public Creator(int k, AlgorithmSettingsPanel.Settings settings) {
            this.k = k;
            this.settings = settings;
        }

        @Override
        public ReroutingMatrixList createMatrices(Network graph) {
            ReroutingMatrixList result = new ReroutingMatrixList();
            Util.createBIBDk(graph, result, k);

            result.setMultiTrees(settings.isUseMultiTree());
            result.setUseCongestionBorder(settings.isUseCongestionBorder());
            return result;
        }
    }
}
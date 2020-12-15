package hu.xannosz.local.rerouting.algorithm;

import hu.xannosz.local.rerouting.core.Network;
import hu.xannosz.local.rerouting.core.algorithm.ReroutingMatrixList;
import hu.xannosz.local.rerouting.core.interfaces.Algorithm;
import hu.xannosz.local.rerouting.core.interfaces.MatrixCreator;
import hu.xannosz.local.rerouting.core.launcher.AlgorithmSettingsPanel;
import hu.xannosz.local.rerouting.core.util.Util;

public abstract class BIBDk implements Algorithm {

    private final int k;
    private final int r;

    public BIBDk(int k) {
        this(k, 1);
    }

    public BIBDk(int k, int r) {
        this.k = k;
        this.r = r;
    }

    @Override
    public String getName() {
        return "BIBD " + k + (r > 1 ? " With " + r + " line Randomization" : "");
    }

    @Override
    public MatrixCreator getCreator(AlgorithmSettingsPanel.Settings settings) {
        return new Creator(k, r, settings);
    }

    public static class Creator implements MatrixCreator {

        private final int k;
        private final int r;
        private final AlgorithmSettingsPanel.Settings settings;

        public Creator(int k, int r, AlgorithmSettingsPanel.Settings settings) {
            this.k = k;
            this.r = r;
            this.settings = settings;
        }

        @Override
        public ReroutingMatrixList createMatrices(Network graph) {
            ReroutingMatrixList result = new ReroutingMatrixList();
            Util.createBIBDk(graph, result, k, r);

            result.setUseRandomization(r > 1);
            result.setRandomizationsNumber(r);
            result.setMultiTrees(settings.isUseMultiTree());
            result.setUseCongestionBorder(settings.isUseCongestionBorder());
            return result;
        }
    }
}
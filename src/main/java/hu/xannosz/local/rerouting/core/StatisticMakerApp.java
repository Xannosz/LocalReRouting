package hu.xannosz.local.rerouting.core;

import hu.xannosz.local.rerouting.algorithm.AllToOne;
import hu.xannosz.local.rerouting.algorithm.Permutation;
import hu.xannosz.local.rerouting.algorithm.Random;
import hu.xannosz.local.rerouting.core.interfaces.FailureGenerator;
import hu.xannosz.local.rerouting.core.launcher.FailureGeneratorSettingsPanel;
import hu.xannosz.local.rerouting.generator.BasicMessageGenerator;
import hu.xannosz.local.rerouting.graph.ErdosRenyi;
import hu.xannosz.local.rerouting.graph.Pair;

public class StatisticMakerApp {
    public static void main(String[] args) {
        run(1000);
    }

    public static void run(int count) {
        Pair.Settings settings = new Pair.Settings();
        settings.setANodes(10);
        settings.setBNodes(15);
        PathRunner runner = new PathRunner(new Permutation(), new Pair(), settings,
                new BasicMessageGenerator(), new BasicMessageGenerator.Settings(),
                new FailureGenerator<Object>() {
                    @Override
                    public void createFailures(Network graph, Object settings) {

                    }

                    @Override
                    public String getName() {
                        return "test";
                    }

                    @Override
                    public FailureGeneratorSettingsPanel<Object> getPanel() {
                        return null;
                    }
                }, null, 0, 0);
        for (int i = 0; i < count; i++) {
            try {
                System.out.println("++" + runner.createPaths().getGraph().getNodeSet().size());
            } catch (Exception e) {
                //System.out.println("++" + e);
            }
        }
    }
}

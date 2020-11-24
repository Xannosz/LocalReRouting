package hu.xannosz.local.rerouting.core;

import hu.xannosz.local.rerouting.core.interfaces.*;
import hu.xannosz.local.rerouting.core.launcher.AlgorithmSettingsPanel;
import hu.xannosz.local.rerouting.core.util.Constants;

import java.util.HashSet;
import java.util.Set;

public class StatisticRunner {

    private static int NUMBER = 0;

    private final PathRunner pathRunner;
    private final Set<PathRunner.PathResponse> responses = new HashSet<>();
    private int number = 0;
    private final int count;
    private final String name;

    public StatisticRunner(final GraphType<?> graphType, final Algorithm algorithm, final AlgorithmSettingsPanel.Settings algorithmSettings, final int count, final Object graphSettings,
                           final MessageGenerator<?> messageGenerator, final Object messageGeneratorSettings,
                           final FailureGenerator<?> failureGenerator, final Object failureGeneratorSettings) {
        number = ++NUMBER;
        this.count = count;
        name = graphType.getName();
        pathRunner = new PathRunner(algorithm, algorithmSettings, graphType, graphSettings,
                messageGenerator, messageGeneratorSettings,
                failureGenerator, failureGeneratorSettings);
    }

    public void run() {
        for (int i = 0; i < count; i++) {
            for (Statistic statistic : Constants.STATISTICS) {
                statistic.update(name + " (" + number + ")", pathRunner.createPaths());
            }
        }
    }
}

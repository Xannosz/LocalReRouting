package hu.xannosz.local.rerouting.core;

import hu.xannosz.local.rerouting.core.interfaces.*;
import hu.xannosz.local.rerouting.core.util.Constants;

import java.util.HashSet;
import java.util.Set;

public class StatisticRunner {

    private static int NUMBER = 0;

    private final PathRunner pathRunner;
    private final Set<PathRunner.PathResponse> responses = new HashSet<>();
    private int number = 0;
    private int count = 0;
    private final String name;

    public StatisticRunner(final GraphType<?> graphType, final Algorithm algorithm, int count, final Object graphSettings,
                           MessageGenerator<?> messageGenerator, Object messageGeneratorSettings,
                           FailureGenerator<?> failureGenerator, Object failureGeneratorSettings) {
        number = ++NUMBER;
        this.count = count;
        name = graphType.getName();
        pathRunner = new PathRunner(algorithm, graphType, graphSettings,
                messageGenerator, messageGeneratorSettings,
                failureGenerator, failureGeneratorSettings, 0, 0);
    }

    public void run() {
        for (int i = 0; i < count; i++) {
            responses.add(pathRunner.createPaths());
        }
        for (PathRunner.PathResponse response : responses) {
            for (Statistic statistic : Constants.STATISTICS) {
                statistic.update(name + " (" + number + ")", response);
            }
        }
    }
}

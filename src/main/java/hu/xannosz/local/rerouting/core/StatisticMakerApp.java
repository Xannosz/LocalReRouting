package hu.xannosz.local.rerouting.core;

import hu.xannosz.local.rerouting.core.interfaces.*;

import static hu.xannosz.local.rerouting.core.util.Constants.*;

public class StatisticMakerApp {
    public static void main(String[] args) {
        run(1000);
    }

    public static void run(int count) {
        for (Algorithm algorithm : ALGORITHMS) {
            for (GraphType<?> graphType : GRAPHS) {
                for (MessageGenerator<?> messageGenerator : MESSAGE_GENERATORS) {
                    for (FailureGenerator<?> failureGenerator : FAILURE_GENERATORS) {
                        for (Object graphSettings : graphType.getSettings()) {
                            for (Object messageGeneratorSettings : messageGenerator.getSettings()) {
                                for (Object failureGeneratorSettings : failureGenerator.getSettings()) {
                                    PathRunner pathRunner = new PathRunner(algorithm,
                                            graphType, graphSettings,
                                            messageGenerator, messageGeneratorSettings,
                                            failureGenerator, failureGeneratorSettings, 0, 0
                                    );
                                    for (int i = 0; i < count; i++) {

                                    }
                                    for (Statistic statistic : STATISTICS) {
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

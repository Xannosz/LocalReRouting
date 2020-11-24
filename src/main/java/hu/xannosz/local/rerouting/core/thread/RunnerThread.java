package hu.xannosz.local.rerouting.core.thread;

import hu.xannosz.local.rerouting.core.PathRunner;
import hu.xannosz.local.rerouting.core.statistic.Visualiser;
import hu.xannosz.microtools.Sleep;

public class RunnerThread extends Thread {
    private final Visualiser visualiser;
    private final PathRunner.PathResponse pathResponse;

    public RunnerThread(PathRunner.PathResponse pathResponse) {
        this.pathResponse = pathResponse;
        this.visualiser = new Visualiser(pathResponse.getGraph());
    }

    @Override
    public void run() {
        for (; ; ) {
            for (int tree : pathResponse.getGraph().getTrees()) {
                Sleep.sleepSeconds(1);
                visualiser.update(tree);
            }
        }
    }
}

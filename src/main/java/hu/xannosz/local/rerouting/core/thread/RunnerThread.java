package hu.xannosz.local.rerouting.core.thread;

import hu.xannosz.local.rerouting.core.Network;
import hu.xannosz.local.rerouting.core.statistic.Visualiser;
import hu.xannosz.microtools.Sleep;

public class RunnerThread extends Thread {
    private final Visualiser visualiser;
    private final Network network;

    public RunnerThread(Network network) {
        this.network = network;
        this.visualiser = new Visualiser(network);
    }

    @Override
    public void run() {
        for (; ; ) {
            for (int tree : network.getTrees()) {
                Sleep.sleepSeconds(1);
                visualiser.update(tree);
            }
        }
    }
}

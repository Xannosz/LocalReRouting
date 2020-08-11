package hu.xannosz.local.rerouting.core.thread;

import hu.xannosz.local.rerouting.core.StatisticRunner;
import hu.xannosz.microtools.Sleep;

public class StatisticRunnerThread extends Thread {
    private final StatisticRunner runner;

    public StatisticRunnerThread(StatisticRunner runner) {
        this.runner = runner;
    }

    @Override
    public void run() {
        for (; ; ) {
            Sleep.sleepSeconds(2);
            runner.step();
        }
    }
}

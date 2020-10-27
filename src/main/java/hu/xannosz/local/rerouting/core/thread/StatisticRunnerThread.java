package hu.xannosz.local.rerouting.core.thread;

import hu.xannosz.local.rerouting.core.StatisticRunner;

public class StatisticRunnerThread extends Thread {
    private final StatisticRunner runner;

    public StatisticRunnerThread(StatisticRunner runner) {
        this.runner = runner;
    }

    @Override
    public void run() {
        runner.run();
    }
}

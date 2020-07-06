package hu.xannosz.local.rerouting.core;

import hu.xannosz.microtools.Sleep;

public class RunnerThread extends Thread {
    private final Runner<?> runner;

    public RunnerThread(Runner<?> runner) {
        this.runner = runner;
    }

    @Override
    public void run() {
        for (; ; ) {
            Sleep.sleepSeconds(1);
            runner.step();
        }
    }
}

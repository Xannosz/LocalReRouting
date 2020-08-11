package hu.xannosz.local.rerouting.core.thread;

import hu.xannosz.local.rerouting.core.Runner;
import hu.xannosz.microtools.Sleep;

public class RunnerThread extends Thread {
    private final Runner runner;

    public RunnerThread(Runner runner) {
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

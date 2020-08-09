package hu.xannosz.local.rerouting.core;

import hu.xannosz.microtools.Sleep;

public class ChartThread extends Thread {
    private final ChartWindow window = new ChartWindow();

    @Override
    public void run() {
        for (; ; ) {
            Sleep.sleepSeconds(2);
            window.update();
        }
    }
}

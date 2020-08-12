package hu.xannosz.local.rerouting.core.thread;

import hu.xannosz.local.rerouting.core.ChartWindow;
import hu.xannosz.microtools.Sleep;

public class ChartThread extends Thread {

    private static ChartThread INSTANCE;

    private final ChartWindow window = new ChartWindow();

    private ChartThread() {
        // Singleton class
    }

    public static void startChart() {
        if (INSTANCE == null) {
            INSTANCE = new ChartThread();
            INSTANCE.start();
        }
    }

    @Override
    public void run() {
        for (; ; ) {
            Sleep.sleepSeconds(2);
            window.update();
        }
    }
}

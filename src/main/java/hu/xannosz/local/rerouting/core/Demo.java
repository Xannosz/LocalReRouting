package hu.xannosz.local.rerouting.core;

import hu.xannosz.local.rerouting.core.launcher.Launcher;

public class Demo {
    public static void main(String[] args) {
        if (args.length == 0) {
            System.out.println("Running in graphical mode.");
            new Launcher();
            return;
        }
        if (args.length == 1 || args.length == 2) {
            if (args[0].equals("rewriter")) {
                StatisticRewriter.main(new String[0]);
                return;
            }

            int count;
            try {
                count = Integer.parseInt(args[0]);
            } catch (NumberFormatException e) {
                System.err.println("First parameter must be an Integer or 'rewriter'.");
                return;
            }
            System.out.println("Running in statistic mode.");
            StatisticMakerApp.run(count, args.length == 1 ? "" : args[1]);
            return;
        }

        System.err.println("Only zero, one or two parameter acceptable.");
    }
}

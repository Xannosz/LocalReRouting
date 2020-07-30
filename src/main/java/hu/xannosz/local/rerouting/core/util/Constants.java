package hu.xannosz.local.rerouting.core.util;

import hu.xannosz.local.rerouting.core.statistic.ChartStatistic;
import hu.xannosz.local.rerouting.core.statistic.MaxNodeLoad;
import hu.xannosz.local.rerouting.core.statistic.Statistic;
import hu.xannosz.local.rerouting.core.statistic.Visualiser;

import java.util.HashSet;
import java.util.Set;

public class Constants {
    public static final Set<ChartStatistic> CHART_STATISTICS = new HashSet<>();
    public static final Set<Statistic> STATISTICS = new HashSet<>();

    //Algorithms
    public static final String ALL_TO_ONE = "All to One";
    public static final String RANDOM = "Random";
    public static final String PERMUTATION = "Permutation";
    public static final String BBID_3 = "BBID_3";
    public static final String BBID_5 = "BBID_5";
    public static final Set<String> ALGORITHMS = new HashSet<>();

    //Graphs
    public static final String COMPLETE = "Complete";
    public static final String PAIR = "Pair";
    public static final String THREE_COMPLETE = "Three Complete";
    public static final String PETERSEN = "Petersen";
    public static final String ERDOS_RENYI = "Erdős–Rényí";
    public static final Set<String> GRAPHS = new HashSet<>();

    static {
        ALGORITHMS.add(ALL_TO_ONE);
        ALGORITHMS.add(RANDOM);
        ALGORITHMS.add(PERMUTATION);
        ALGORITHMS.add(BBID_3);
        ALGORITHMS.add(BBID_5);

        GRAPHS.add(COMPLETE);
        GRAPHS.add(PAIR);
        GRAPHS.add(THREE_COMPLETE);
        GRAPHS.add(PETERSEN);
        GRAPHS.add(ERDOS_RENYI);

        //STATISTICS.add(new Visualiser());

        CHART_STATISTICS.add(new MaxNodeLoad());

        STATISTICS.addAll(CHART_STATISTICS);
    }
}

package hu.xannosz.local.rerouting.core.util;

import hu.xannosz.local.rerouting.algorithm.*;
import hu.xannosz.local.rerouting.core.algorithm.Algorithm;
import hu.xannosz.local.rerouting.core.algorithm.ListRoutingTable;
import hu.xannosz.local.rerouting.core.graph.GraphType;
import hu.xannosz.local.rerouting.core.statistic.ChartStatistic;
import hu.xannosz.local.rerouting.graph.*;
import hu.xannosz.local.rerouting.statistic.AverageNodeLoad;
import hu.xannosz.local.rerouting.statistic.MaxNodeLoad;

import java.util.HashSet;
import java.util.Set;

public class Constants {
    public static final Set<ChartStatistic> CHART_STATISTICS = new HashSet<>();

    //Algorithms
    public static final Algorithm<ListRoutingTable> ALL_TO_ONE = new AllToOne();
    public static final Algorithm<ListRoutingTable> RANDOM = new Random();
    public static final Algorithm<ListRoutingTable> PERMUTATION = new Permutation();
    public static final Algorithm<ListRoutingTable> BBID_3 = new BBID3();
    public static final Algorithm<ListRoutingTable> BBID_5 = new BBID5();
    public static final Set<Algorithm<?>> ALGORITHMS = new HashSet<>();

    //Graphs
    public static final GraphType<Complete.Settings> COMPLETE = new Complete();
    public static final GraphType<Pair.Settings> PAIR = new Pair();
    public static final GraphType<ThreeComplete.Settings> THREE_COMPLETE = new ThreeComplete();
    public static final GraphType<Petersen.Settings> PETERSEN = new Petersen();
    public static final GraphType<ErdosRenyi.Settings> ERDOS_RENYI = new ErdosRenyi();
    public static final Set<GraphType<?>> GRAPHS = new HashSet<>();

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

        CHART_STATISTICS.add(new MaxNodeLoad());
        CHART_STATISTICS.add(new AverageNodeLoad());
    }
}

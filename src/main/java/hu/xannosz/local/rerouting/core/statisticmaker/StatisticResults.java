package hu.xannosz.local.rerouting.core.statisticmaker;

import hu.xannosz.local.rerouting.core.PathRunner;
import hu.xannosz.local.rerouting.core.interfaces.Statistic;
import hu.xannosz.microtools.pack.Quatlet;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

public class StatisticResults {
    @Getter
    private final List<Quatlet<PathRunner.PathResponse, Statistic, StatisticAggregateType, Double>> data = new ArrayList<>();

    public void add(PathRunner.PathResponse response, Statistic statistic, StatisticAggregateType type, double n) {
        data.add(new Quatlet<>(response, statistic, type, n));
    }

    public void add(StatisticResults statisticResults){
        data.addAll(statisticResults.getData());
    }
}

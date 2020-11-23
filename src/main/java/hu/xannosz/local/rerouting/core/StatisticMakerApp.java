package hu.xannosz.local.rerouting.core;

import com.google.gson.Gson;
import hu.xannosz.local.rerouting.core.interfaces.*;
import hu.xannosz.local.rerouting.core.statisticmaker.StatisticAggregateType;
import hu.xannosz.local.rerouting.core.statisticmaker.StatisticResults;
import hu.xannosz.local.rerouting.graph.ErdosRenyi;
import hu.xannosz.local.rerouting.messagegenerator.AllToOneMessageGenerator;
import org.apache.commons.io.FileUtils;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import static hu.xannosz.local.rerouting.core.util.Constants.*;

public class StatisticMakerApp {

    private final static Path DATA_PATH = Paths.get("results/data.json");

    public static void main(String[] args) {
        run(10);
    }

    public static void run(int count) {
        StatisticResults results = new StatisticResults();
        GraphType<?> graphType = new ErdosRenyi();
        MessageGenerator<?> messageGenerator = new AllToOneMessageGenerator();
        for (Algorithm algorithm : ALGORITHMS) {
            for (FailureGenerator<?> failureGenerator : FAILURE_GENERATORS) {
                for (Object graphSettings : graphType.getSettings()) {
                    for (Object messageGeneratorSettings : messageGenerator.getSettings()) {
                        for (Object failureGeneratorSettings : failureGenerator.getSettings()) {
                            PathRunner pathRunner = new PathRunner(algorithm,
                                    graphType, graphSettings,
                                    messageGenerator, messageGeneratorSettings,
                                    failureGenerator, failureGeneratorSettings
                            );
                            for (int i = 0; i < count; i++) {
                                PathRunner.PathResponse response = pathRunner.createPaths();
                                for (Statistic statistic : STATISTICS) {
                                    statistic.update("key", response);
                                }
                            }
                            PathRunner.PathResponse response = new PathRunner.PathResponse(null
                                    , graphType.getName()
                                    , failureGenerator.getName()
                                    , messageGenerator.getName()
                                    , algorithm.getName()
                                    , null
                                    , graphSettings
                                    , messageGeneratorSettings
                                    , failureGeneratorSettings
                                    , null);
                            for (Statistic statistic : STATISTICS) {
                                List<Double> datas = getDatas(statistic);
                                results.add(response, statistic, StatisticAggregateType.MAX, getMax(datas));
                                results.add(response, statistic, StatisticAggregateType.MIN, getMin(datas));
                                results.add(response, statistic, StatisticAggregateType.AVERAGE, getAverage(datas));
                                results.add(response, statistic, StatisticAggregateType.MEDIAN, getMedian(datas));
                            }
                        }
                    }
                }
            }
        }
        writeData(results);
    }

    private static void writeData(StatisticResults results) {
        try {
            DATA_PATH.toFile().getParentFile().mkdirs();
            DATA_PATH.toFile().createNewFile();
            FileUtils.writeStringToFile(DATA_PATH.toFile(), new Gson().toJson(results));
        } catch (Exception e) {
            // Empty
        }
    }

    private static double getMax(List<Double> datas) {
        if (datas == null || datas.size() == 0) {
            return Double.MIN_VALUE;
        }
        List<Double> sortedlist = new ArrayList<>(datas);
        Collections.sort(sortedlist);
        return sortedlist.get(sortedlist.size() - 1);
    }

    private static double getMin(List<Double> datas) {
        if (datas == null || datas.size() == 0) {
            return Double.MAX_VALUE;
        }
        List<Double> sortedlist = new ArrayList<>(datas);
        Collections.sort(sortedlist);
        return sortedlist.get(0);
    }

    private static double getAverage(List<Double> datas) {
        return datas.stream().mapToDouble(val -> val).average().orElse(0.0);
    }

    private static double getMedian(List<Double> datas) {
        if (datas == null || datas.size() == 0) {
            return 0;
        }
        List<Double> sortedlist = new ArrayList<>(datas);
        Collections.sort(sortedlist);

        if (sortedlist.size() % 2 == 0) {
            return (sortedlist.get(sortedlist.size() / 2) + sortedlist.get(sortedlist.size() / 2 - 1)) / 2;
        } else {
            return sortedlist.get(sortedlist.size() / 2);
        }
    }

    public static List<Double> getDatas(Statistic statistic) {
        List<Double> datas = new ArrayList<>();
        for (Map.Entry<String, Integer> entry : statistic.getDataSet().getTimes().entrySet()) {
            for (int i = 0; i <= entry.getValue(); i++) {
                datas.add(statistic.getDataSet().getValue(entry.getKey(), "" + i).doubleValue());
            }
        }
        statistic.getDataSet().clear();
        return datas;
    }
}

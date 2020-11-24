package hu.xannosz.local.rerouting.core;

import com.google.gson.Gson;
import hu.xannosz.local.rerouting.core.interfaces.*;
import hu.xannosz.local.rerouting.core.launcher.AlgorithmSettingsPanel;
import hu.xannosz.local.rerouting.core.statisticmaker.StatisticAggregateType;
import hu.xannosz.local.rerouting.core.statisticmaker.StatisticResults;
import hu.xannosz.local.rerouting.graph.ErdosRenyi;
import hu.xannosz.local.rerouting.messagegenerator.AllToOneMessageGenerator;
import org.apache.commons.io.FileUtils;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

import static hu.xannosz.local.rerouting.core.util.Constants.*;

public class StatisticMakerApp {

    private final static String DATA_PATH = "results/data/";

    public static void main(String[] args) {
        Date start = new Date();
        run(100);
        System.out.println("Time: " + (new Date().getTime() - start.getTime()));
    }

    public static void run(int count) {
        GraphType<ErdosRenyi.Settings> graphType = new ErdosRenyi();
        MessageGenerator<?> messageGenerator = new AllToOneMessageGenerator();
        for (ErdosRenyi.Settings graphSettings : graphType.getSettings()) {
            for (Algorithm algorithm : ALGORITHMS) {
                for (FailureGenerator<?> failureGenerator : FAILURE_GENERATORS) {
                    StatisticResults results = new StatisticResults();
                    for (AlgorithmSettingsPanel.Settings algorithmSettings : algorithm.getSettings()) {
                        for (Object messageGeneratorSettings : messageGenerator.getSettings()) {
                            for (Object failureGeneratorSettings : failureGenerator.getSettings()) {
                                PathRunner pathRunner = new PathRunner(
                                        algorithm, algorithmSettings,
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
                                System.out.println("End of: " + response);
                            }
                        }
                    }
                    writeData(Paths.get(DATA_PATH, algorithm.getName(), failureGenerator.getName(), graphSettings + ".json"), results);
                }
            }
        }
    }

    @SuppressWarnings("ResultOfMethodCallIgnored")
    private static void writeData(Path path, StatisticResults results) {
        try {
            path.toFile().getParentFile().mkdirs();
            path.toFile().createNewFile();
            FileUtils.writeStringToFile(path.toFile(), new Gson().toJson(results), "UTF-8");
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
                try {
                    datas.add(statistic.getDataSet().getValue(entry.getKey(), "" + i).doubleValue());
                } catch (Exception e) {
                    //Empty
                }
            }
        }
        statistic.getDataSet().clear();
        return datas;
    }
}

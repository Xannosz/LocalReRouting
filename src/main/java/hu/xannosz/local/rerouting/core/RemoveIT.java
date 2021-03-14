package hu.xannosz.local.rerouting.core;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import hu.xannosz.local.rerouting.core.interfaces.Statistic;
import hu.xannosz.local.rerouting.core.statisticmaker.StatisticAggregateType;
import hu.xannosz.local.rerouting.core.statisticmaker.StatisticResults;
import hu.xannosz.microtools.pack.Quatlet;
import hu.xannosz.microtools.pack.Triplet;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.filefilter.DirectoryFileFilter;
import org.apache.commons.io.filefilter.RegexFileFilter;

import java.io.File;
import java.io.IOException;
import java.util.*;

public class RemoveIT {
    private final static String DATA_PATH = "BIBDk/data/";

    private final static String OUT_PATH = "matlabResult/";

    public static void main(String[] args) {
        StatisticResults results = new StatisticResults();

        if (!new File(DATA_PATH).exists() || !new File(DATA_PATH).isDirectory()) {
            System.err.println(DATA_PATH + " not exists or not a directory.");
            return;
        }

        Collection<File> files = FileUtils.listFilesAndDirs(new File(DATA_PATH), new RegexFileFilter("^(.*?)"),
                DirectoryFileFilter.DIRECTORY);
        for (File file : files) {
            System.out.println("Read: " + file);
            if (file.isDirectory()) {
                continue;
            }
            try {
                JsonElement dataObject = JsonParser.parseString(FileUtils.readFileToString(file, "UTF-8"));
                results.add(new Gson().fromJson(dataObject, StatisticResults.class));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        System.out.println("Data size: " + results.getData().size());

        Map<String, StringBuilder> outputs = new HashMap<>();

        Set<Triplet<Integer, Integer, Double>> triResults = new HashSet<>();

        for (Quatlet<PathRunner.PathResponse, Statistic, StatisticAggregateType, Double> data : results.getData()) {
            if (data.getThird().equals(StatisticAggregateType.MEDIAN)) {
                int nodes = 0;
                int chance = 0;
                String algorithm = data.getFirst().getAlgorithmName();
                String genName = data.getFirst().getFailureGeneratorName();
                String title = data.getSecond().getDataSet().getTitle();

                try {
                    nodes = Integer.parseInt(("" + data.getFirst().getGraphSettings())
                            .split(",")[0].split("=")[1].split("\\.")[0]);
                    chance = Integer.parseInt(("" + data.getFirst().getFailureGeneratorSettings())
                            .split(",")[0].split("=")[1].split("\\.")[0]);
                } catch (Exception e) {
                    e.printStackTrace();
                }

                if (!title.contains("Max") || !title.contains("Cong") || !genName.contains("Basic") || chance != 10) {
                    continue;
                }


                triResults.add(
                        new Triplet<>(nodes, Integer.parseInt(algorithm.split(" ")[1]), data.getFourth()));

            }
        }

        String pathNodes = "result";

        outputs.put(pathNodes, new StringBuilder(";"));
        for (int k = 3; k < 25; k++) {
            outputs.get(pathNodes).append(k).append(";");
        }
        outputs.get(pathNodes).append("\n");
        for (int n = 10; n < 150; n++) {
            outputs.get(pathNodes).append(n).append(";");
            for (int k = 3; k < 25; k++) {
                for (Triplet<Integer, Integer, Double> dat : triResults) {
                    if (dat.getFirst() == n && dat.getSecond() == k) {
                        outputs.get(pathNodes).append(dat.getThird()).append(";");

                    }
                }
            }
            outputs.get(pathNodes).append("\n");
        }

        for (Map.Entry<String, StringBuilder> entry : outputs.entrySet()) {
            try {
                FileUtils.write(new File(OUT_PATH + entry.getKey() + ".csv"), entry.getValue().toString(), "UTF-8");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}

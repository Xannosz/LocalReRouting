package hu.xannosz.local.rerouting.core;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import hu.xannosz.local.rerouting.core.interfaces.Statistic;
import hu.xannosz.local.rerouting.core.statisticmaker.StatisticAggregateType;
import hu.xannosz.local.rerouting.core.statisticmaker.StatisticResults;
import hu.xannosz.microtools.pack.Quatlet;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.filefilter.DirectoryFileFilter;
import org.apache.commons.io.filefilter.RegexFileFilter;

import java.io.File;
import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class Test {

    private final static String DATA_PATH = "results/data/";
    //private final static String DATA_PATH = "BIBDk";

    private final static String OUT_PATH = "matlabResult/";

    public static void main(String[] args) {
        StatisticResults results = new StatisticResults();

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

        for (Quatlet<PathRunner.PathResponse, Statistic, StatisticAggregateType, Double> data : results.getData()) {
            if (data.getThird().equals(StatisticAggregateType.MEDIAN)) {
                int nodes = 0;
                int p = 0;
                int chance = 0;
                String algorithm = data.getFirst().getAlgorithmName();
                boolean mT = data.getFirst().getAlgorithmSettings().isUseMultiTree();
                boolean cB = data.getFirst().getAlgorithmSettings().isUseCongestionBorder();
                String genName = data.getFirst().getFailureGeneratorName();
                String title=  data.getSecond().getDataSet().getTitle();

                try {
                    nodes = Integer.parseInt(("" + data.getFirst().getGraphSettings())
                            .split(",")[0].split("=")[1].split("\\.")[0]);
                    p = Integer.parseInt(("" + data.getFirst().getGraphSettings())
                            .split(",")[1].split("=")[1].split("\\.")[0]);
                    chance = Integer.parseInt(("" + data.getFirst().getFailureGeneratorSettings())
                            .split(",")[0].split("=")[1].split("\\.")[0]);
                } catch (Exception e) {
                    e.printStackTrace();
                }

                System.out.println("Chance: " + chance +
                        " Nodes: " + nodes +
                       " Algorithm: " + algorithm +
                        " Value: " + data.getFourth());

                String path = ( title+"/"+algorithm + "P" + p + "C" + chance +
                       // "MT" + (mT ? 1 : 0) +
                       // "CB" + (cB ? 1 : 0) +
                        genName
                ).replace(" ", "");

/*                if( (chance == 20 && p == 40 && !mT && !cB && genName.contains("Bas")) ||
                        (chance == 20 && p == 40 && !mT && !cB && genName.contains("Crit"))) {
                    if(title.contains("Lost") && algorithm.contains("Prep")){
                        System.out.println("#"+nodes + " " + data.getFourth() + ";\n");
                    }
                }*/

                if( (chance == 10 && p == 60 && !mT && !cB && genName.contains("Bas")) ||
                 (chance == 20 && p == 40 && !mT && !cB && genName.contains("Bas")) ||
                (chance == 20 && p == 40 && !mT && !cB && genName.contains("Crit")) ||
                (chance == 30 && p == 20 && !mT && !cB && genName.contains("Crit"))) {
                    if (!outputs.containsKey(path)) {
                        outputs.put(path, new StringBuilder("function r = " + path.split("/")[1] + "()\nr=["));
                    }
                    outputs.get(path).append(nodes + " " + data.getFourth() + ";\n");
                }
            }
        }

        for (Map.Entry<String, StringBuilder> entry : outputs.entrySet()) {
            entry.getValue().append("];\nend");
            try {
                FileUtils.write(new File(OUT_PATH + entry.getKey() + ".m"), entry.getValue().toString(), "UTF-8");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}

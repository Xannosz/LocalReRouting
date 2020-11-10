package hu.xannosz.local.rerouting.statistic;

import hu.xannosz.local.rerouting.core.PathRunner;
import hu.xannosz.local.rerouting.core.algorithm.Message;
import hu.xannosz.local.rerouting.core.interfaces.Statistic;
import hu.xannosz.local.rerouting.core.statistic.DataSet;
import lombok.Getter;

import java.util.Map;
import java.util.Set;

@hu.xannosz.local.rerouting.core.annotation.Statistic
public class AveragePathLong implements Statistic {

    @Getter
    private final DataSet dataSet = new DataSet("Average Path Long");

    @Override
    public void update(String key, PathRunner.PathResponse response) {
        float count = 0;
        float avg = 0;
        for (Map.Entry<Integer, Set<Message>> entry : response.getMessages().entrySet()) {
            for (Message message : entry.getValue()) {
                avg += message.visitedNodesMap.size();
                count++;
            }
        }
        dataSet.addData(key, avg / count);
    }
}

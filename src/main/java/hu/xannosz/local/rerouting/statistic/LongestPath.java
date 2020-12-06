package hu.xannosz.local.rerouting.statistic;

import hu.xannosz.local.rerouting.core.PathRunner;
import hu.xannosz.local.rerouting.core.algorithm.Message;
import hu.xannosz.local.rerouting.core.interfaces.Statistic;
import hu.xannosz.local.rerouting.core.statistic.DataSet;

import java.util.Map;
import java.util.Set;

@hu.xannosz.local.rerouting.core.annotation.Statistic
public class LongestPath extends Statistic {

    public LongestPath() {
        dataSet = new DataSet("Longest Path");
    }

    @Override
    public void update(String key, PathRunner.PathResponse response) {
        int max = 0;
        for (Map.Entry<Integer, Set<Message>> entry : response.getMessages().entrySet()) {
            for (Message message : entry.getValue()) {
                if (message.visitedNodesMap.size() > max) {
                    max = message.visitedNodesMap.size();
                }
            }
        }
        dataSet.addData(key, max);
    }
}

package hu.xannosz.local.rerouting.statistic;

import hu.xannosz.local.rerouting.core.interfaces.Statistic;
import hu.xannosz.local.rerouting.core.statistic.DataSet;
import hu.xannosz.local.rerouting.core.util.Util;

import java.util.Map;
import java.util.Set;

@hu.xannosz.local.rerouting.core.annotation.Statistic
public class MaxCongestion implements Statistic {

    private final DataSet dataSet = new DataSet("Max Congestion");

    @Override
    public void update(String key, Set<MessageContainer> containers) {
        int messagesAvg = 0;
        int messagesCount = 0;
        for (MessageContainer container : containers) {
            int max = 0;
            for (Map.Entry<String, Integer> congestion : Util.getConnectionCongestion(container.getOldMessages(), container.getNewMessages()).entrySet()) {
                if (congestion.getValue() > max) {
                    max = congestion.getValue();
                }
            }
            messagesAvg += max;
            messagesCount++;
        }
        if (messagesCount > 0) {
            dataSet.addData(key, messagesAvg / (0.0f + messagesCount));
        }
    }

    @Override
    public DataSet getDataSet() {
        return dataSet;
    }
}

package hu.xannosz.local.rerouting.statistic;

import hu.xannosz.local.rerouting.core.interfaces.Statistic;
import hu.xannosz.local.rerouting.core.statistic.DataSet;
import hu.xannosz.local.rerouting.core.util.Util;

import java.util.Map;
import java.util.Set;

@hu.xannosz.local.rerouting.core.annotation.Statistic
public class AverageCongestion implements Statistic {

    private final DataSet dataSet = new DataSet("Average Congestion");

    @Override
    public void update(String key, Set<MessageContainer> containers) {
        int messagesAvg = 0;
        int messagesCount = 0;
        for (MessageContainer container : containers) {
            float avg = 0;
            float count = 0;
            for (Map.Entry<String, Integer> congestion : Util.getConnectionCongestion(container.getOldMessages(), container.getNewMessages()).entrySet()) {
                avg += congestion.getValue();
                count++;
            }
            if (count > 0) {
                messagesAvg += avg / count;
                messagesCount++;
            }
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

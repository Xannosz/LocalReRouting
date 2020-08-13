package hu.xannosz.local.rerouting.statistic;

import hu.xannosz.local.rerouting.core.algorithm.Message;
import hu.xannosz.local.rerouting.core.statistic.Statistic;
import hu.xannosz.local.rerouting.core.statistic.DataSet;

import java.util.Map;
import java.util.Set;

@hu.xannosz.local.rerouting.core.annotation.Statistic
public class MaxNodeLoad implements Statistic {

    private final DataSet dataSet = new DataSet("Max Node Load");

    @Override
    public void update(String key, Set<MessageContainer> containers) {
        int messagesAvg = 0;
        int messagesCount = 0;
        for (MessageContainer container : containers) {
            int max = 0;
            for (Map.Entry<Integer, Set<Message>> message : container.getNewMessages().entrySet()) {
                int i = message.getValue().size();
                if (i > max) {
                    max = i;
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

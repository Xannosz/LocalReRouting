package hu.xannosz.local.rerouting.statistic;

import hu.xannosz.local.rerouting.core.algorithm.Message;
import hu.xannosz.local.rerouting.core.statistic.Statistic;
import hu.xannosz.local.rerouting.core.statistic.DataSet;

import java.util.Map;
import java.util.Set;

@hu.xannosz.local.rerouting.core.annotation.Statistic
public class AverageNodeLoad implements Statistic {

    private final DataSet dataSet = new DataSet("Average Node Load");

    @Override
    public void update(String key, Set<MessageContainer> containers) {
        float messagesAvg = 0;
        int messagesCount = 0;
        for (MessageContainer container : containers) {
            int avg = 0;
            int count = 0;
            for (Map.Entry<Integer, Set<Message>> message : container.getNewMessages().entrySet()) {
                avg += message.getValue().size();
                count++;
            }
            if (count > 0) {
                messagesAvg += avg / (0.0f + count);
            }
            messagesCount++;
        }
        if (messagesCount > 0) {
            dataSet.addData(key, messagesAvg / (0.0f +messagesCount));
        }
    }

    @Override
    public DataSet getDataSet() {
        return dataSet;
    }
}

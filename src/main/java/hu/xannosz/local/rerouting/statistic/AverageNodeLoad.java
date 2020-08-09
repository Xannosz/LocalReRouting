package hu.xannosz.local.rerouting.statistic;

import hu.xannosz.local.rerouting.core.algorithm.Message;
import hu.xannosz.local.rerouting.core.statistic.ChartStatistic;
import hu.xannosz.local.rerouting.core.statistic.DataSet;

import java.util.Map;
import java.util.Set;

public class AverageNodeLoad implements ChartStatistic {

    private final DataSet dataSet = new DataSet("AverageNodeLoad");

    @Override
    public void update(Map<Integer, Set<Message>> oldMessages, Map<Integer, Set<Message>> newMessages) {
        int avg = 0;
        int count = 0;
        for (Map.Entry<Integer, Set<Message>> message : newMessages.entrySet()) {
            avg += message.getValue().size();
            count++;
        }
        if (count > 0) {
            dataSet.addData(avg / count);
        }
    }

    @Override
    public DataSet getDataSet() {
        return dataSet;
    }
}

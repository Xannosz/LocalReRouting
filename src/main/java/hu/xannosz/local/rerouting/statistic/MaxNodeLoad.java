package hu.xannosz.local.rerouting.statistic;

import hu.xannosz.local.rerouting.core.algorithm.Message;
import hu.xannosz.local.rerouting.core.statistic.ChartStatistic;
import hu.xannosz.local.rerouting.core.statistic.DataSet;

import java.util.Map;
import java.util.Set;

public class MaxNodeLoad implements ChartStatistic {

    private final DataSet dataSet = new DataSet("MaxNodeLoad");

    @Override
    public void update(Map<Integer, Set<Message>> oldMessages, Map<Integer, Set<Message>> newMessages) {
        int max = 0;
        for (Map.Entry<Integer, Set<Message>> message : newMessages.entrySet()) {
            int i = message.getValue().size();
            if (i > max) {
                max = i;
            }
        }
        dataSet.addData(max);
    }

    @Override
    public DataSet getDataSet() {
        return dataSet;
    }
}

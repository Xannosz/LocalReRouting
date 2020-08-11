package hu.xannosz.local.rerouting.core.statistic;

import hu.xannosz.local.rerouting.core.algorithm.Message;
import lombok.Data;

import java.util.Map;
import java.util.Set;

public interface ChartStatistic {
    
    DataSet getDataSet();

    void update(String key, Set<MessageContainer> containers);

    @Data
    class MessageContainer {
        private Map<Integer, Set<Message>> oldMessages;
        private Map<Integer, Set<Message>> newMessages;
    }
}

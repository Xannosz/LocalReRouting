package hu.xannosz.local.rerouting.core.statistic;

import hu.xannosz.local.rerouting.core.algorithm.Message;

import java.util.Map;
import java.util.Set;

public interface Statistic {
    void update(Map<Integer, Set<Message>> messages, Map<Integer, Set<Message>> newMessages);
}

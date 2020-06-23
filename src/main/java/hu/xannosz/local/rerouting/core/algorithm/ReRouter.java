package hu.xannosz.local.rerouting.core.algorithm;

import java.util.Map;
import java.util.Set;

public interface ReRouter<T> {
    public Map<Integer, Set<Message>> route(int node, T routingTable, Set<Message> messages, Set<Integer> connects);
}

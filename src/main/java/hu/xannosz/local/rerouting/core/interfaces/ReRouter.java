package hu.xannosz.local.rerouting.core.interfaces;

import hu.xannosz.local.rerouting.core.algorithm.ListRoutingTable;
import hu.xannosz.local.rerouting.core.algorithm.Message;

import java.util.Map;
import java.util.Set;

public interface ReRouter {
    Map<Integer, Set<Message>> route(int node, ListRoutingTable routingTable, Set<Message> messages, Set<Integer> connects);
}

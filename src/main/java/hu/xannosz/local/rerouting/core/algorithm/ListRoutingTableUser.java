package hu.xannosz.local.rerouting.core.algorithm;

import hu.xannosz.local.rerouting.core.interfaces.ReRouter;

import java.util.*;

public class ListRoutingTableUser implements ReRouter<ListRoutingTable.RoutingTable> {
    @Override
    public Map<Integer, Set<Message>> route(int node, ListRoutingTable.RoutingTable routingTable, Set<Message> messages, Set<Integer> connects) {
        Map<Integer, Set<Message>> result = new HashMap<>();
        if (messages == null) {
            return result;
        }
        for (Message message : messages) {
            List<Integer> routing = routingTable.getRouting(message.to);
            message.visitedNodes.add(node);
            boolean success = false;

            for (int next : routing) {
                if (connects.contains(next)) {
                    if (!result.containsKey(next)) {
                        result.put(next, new HashSet<>());
                    }
                    result.get(next).add(message);
                    success = true;
                    break;
                }
            }

            if (!success) {
                if (!result.containsKey(node)) {
                    result.put(node, new HashSet<>());
                }
                result.get(node).add(message);
            }
        }
        return result;
    }
}

package hu.xannosz.local.rerouting.core.algorithm;

import java.util.Map;
import java.util.Set;

public interface ReRouter<T> {

    @SuppressWarnings("unchecked")
    default Map<Integer, Set<Message>> convertAndRoute(int node, Object routingTable, Set<Message> messages, Set<Integer> connects){
        return route( node, (T) routingTable,  messages, connects);
    }

    Map<Integer, Set<Message>> route(int node, T routingTable, Set<Message> messages, Set<Integer> connects);
}

package hu.xannosz.local.rerouting.algorithm;

import hu.xannosz.local.rerouting.core.algorithm.MatrixCreator;
import hu.xannosz.local.rerouting.core.algorithm.Message;
import hu.xannosz.local.rerouting.core.algorithm.ReRouter;
import org.graphstream.graph.Graph;

import java.util.*;

public class RandomReRouter implements MatrixCreator<ListRoutingTable>, ReRouter<ListRoutingTable> {
    @Override
    public Map<Integer, ListRoutingTable> createMatrices(Graph graph) {
        Map<Integer, ListRoutingTable> result = new HashMap<>();
        List<Integer> nodes = new ArrayList<>();
        for (int i = 0; i < graph.getNodeCount(); i++) {
            nodes.add(i);
        }
        Collections.shuffle(nodes);
        for (int i = 0; i < graph.getNodeCount(); i++) {
            result.put(i, new ListRoutingTable(nodes));
        }
        return result;
    }

    @Override
    public Map<Integer, Set<Message>> route(int node, ListRoutingTable routingTable, Set<Message> message, Set<Integer> connects) {
        
        return null;
    }
}

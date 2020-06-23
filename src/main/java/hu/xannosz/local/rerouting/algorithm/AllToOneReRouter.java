package hu.xannosz.local.rerouting.algorithm;

import hu.xannosz.local.rerouting.core.algorithm.MatrixCreator;
import hu.xannosz.local.rerouting.core.algorithm.ReRouter;
import org.graphstream.graph.Graph;

import java.util.*;

public class AllToOneReRouter extends ListRoutingTableUser implements MatrixCreator<ListRoutingTable>, ReRouter<ListRoutingTable> {
    @Override
    public Map<Integer, ListRoutingTable> createMatrices(Graph graph) {
        Map<Integer, ListRoutingTable> result = new HashMap<>();

        Set<Integer> nodes = getNodeSet(graph);

        Map<Integer, List<Integer>> routing = new HashMap<>();
        for (int i = 0; i < graph.getNodeCount(); i++) {
            Set<Integer> table = new HashSet<>(nodes);
            table.remove(i);
            List<Integer> tableList = new ArrayList<>(table);
            Collections.shuffle(tableList);
            routing.put(i, tableList);
        }

        for (int i = 0; i < graph.getNodeCount(); i++) {
            ListRoutingTable table = new ListRoutingTable();
            Map<Integer, List<Integer>> routingTmp = new HashMap<>();
            for (Map.Entry<Integer, List<Integer>> item : routing.entrySet()) {
                routingTmp.put(item.getKey(), removeItemFromList(item.getValue(), i));
            }
            table.routing = routingTmp;
            result.put(i, table);
        }

        return result;
    }

    private Set<Integer> getNodeSet(Graph graph) {
        Set<Integer> nodes = new HashSet<>();
        for (int i = 0; i < graph.getNodeCount(); i++) {
            nodes.add(i);
        }
        return nodes;
    }

    private List<Integer> removeItemFromList(List<Integer> list, int remove) {
        List<Integer> result = new ArrayList<>();
        for (int i : list) {
            if (i != remove) {
                result.add(i);
            }
        }
        return result;
    }
}

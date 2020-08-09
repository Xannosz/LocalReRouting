package hu.xannosz.local.rerouting.algorithm;

import hu.xannosz.local.rerouting.core.algorithm.*;
import org.graphstream.graph.Graph;

import java.util.*;

public class AllToOne implements Algorithm<ListRoutingTable> {
    @Override
    public String getName() {
        return "All To One";
    }

    @Override
    public MatrixCreator<ListRoutingTable> getCreator() {
        return new Creator();
    }

    @Override
    public ReRouter<ListRoutingTable> getReRouter() {
        return new ListRoutingTableUser();
    }

    public static class Creator implements MatrixCreator<ListRoutingTable> {
        @Override
        public Map<Integer, ListRoutingTable> createMatrices(Graph graph) {
            Map<Integer, ListRoutingTable> result = new HashMap<>();

            Set<Integer> nodes = AlgorithmHelper.getNodeSet(graph);

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
                    routingTmp.put(item.getKey(), AlgorithmHelper.removeItemFromList(item.getValue(), i));
                }
                table.routing = routingTmp;
                result.put(i, table);
            }

            return result;
        }
    }
}

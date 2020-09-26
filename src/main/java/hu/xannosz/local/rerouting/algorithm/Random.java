package hu.xannosz.local.rerouting.algorithm;

import hu.xannosz.local.rerouting.core.algorithm.AlgorithmHelper;
import hu.xannosz.local.rerouting.core.algorithm.ListRoutingTable;
import hu.xannosz.local.rerouting.core.algorithm.ListRoutingTableUser;
import hu.xannosz.local.rerouting.core.interfaces.Algorithm;
import hu.xannosz.local.rerouting.core.interfaces.MatrixCreator;
import hu.xannosz.local.rerouting.core.interfaces.ReRouter;
import org.graphstream.graph.Graph;

import java.util.*;

@hu.xannosz.local.rerouting.core.annotation.Algorithm
public class Random implements Algorithm<ListRoutingTable.RoutingTable> {
    @Override
    public String getName() {
        return "Random";
    }

    @Override
    public MatrixCreator<ListRoutingTable.RoutingTable> getCreator() {
        return new Creator();
    }

    @Override
    public ReRouter<ListRoutingTable.RoutingTable> getReRouter() {
        return new ListRoutingTableUser();
    }

    public static class Creator implements MatrixCreator<ListRoutingTable.RoutingTable> {
        @Override
        public Map<Integer, ListRoutingTable.RoutingTable> createMatrices(Graph graph) {
            ListRoutingTable result = new ListRoutingTable();

            Set<Integer> nodes = AlgorithmHelper.getNodeSet(graph);

            for (int from : nodes) {
                for (int next : nodes) {
                    if (from == next) {
                        continue;
                    }
                    Set<Integer> routing = new HashSet<>(nodes);
                    routing.remove(from);
                    routing.remove(next);
                    List<Integer> res = new ArrayList<>(routing);
                    Collections.shuffle(res);
                    result.setRoutingList(from, next, res);
                }
            }

            return result.getRouting();
        }
    }
}

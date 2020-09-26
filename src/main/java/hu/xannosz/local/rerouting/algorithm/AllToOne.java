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
public class AllToOne implements Algorithm<ListRoutingTable.RoutingTable> {
    @Override
    public String getName() {
        return "All To One";
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

            for (int next : nodes) {
                Set<Integer> routing = new HashSet<>(nodes);
                routing.remove(next);
                List<Integer> res = new ArrayList<>(routing);
                Collections.shuffle(res);
                for (int from : nodes) {
                    if (from == next) {
                        continue;
                    }
                    List<Integer> res2 = new ArrayList<>();
                    for (int i : res) {
                        if (i != from) {
                            res2.add(i);
                        }
                    }
                    result.setRoutingList(from, next, res2);
                }
            }

            return result.getRouting();
        }
    }
}

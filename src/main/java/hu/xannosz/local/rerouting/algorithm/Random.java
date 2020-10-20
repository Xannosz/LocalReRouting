package hu.xannosz.local.rerouting.algorithm;

import hu.xannosz.local.rerouting.core.Network;
import hu.xannosz.local.rerouting.core.algorithm.ListRoutingTable;
import hu.xannosz.local.rerouting.core.algorithm.ListRoutingTableUser;
import hu.xannosz.local.rerouting.core.interfaces.Algorithm;
import hu.xannosz.local.rerouting.core.interfaces.MatrixCreator;
import hu.xannosz.local.rerouting.core.interfaces.ReRouter;

import java.util.*;

@hu.xannosz.local.rerouting.core.annotation.Algorithm
public class Random implements Algorithm<ListRoutingTable.RoutingTable> {
    @Override
    public String getName() {
        return "Random";
    }

    @Override
    public MatrixCreator getCreator() {
        return new Creator();
    }

    @Override
    public ReRouter getReRouter() {
        return new ListRoutingTableUser();
    }

    public static class Creator implements MatrixCreator {
        @Override
        public ListRoutingTable createMatrices(Network graph) {
            ListRoutingTable result = new ListRoutingTable();

            Set<Integer> nodes = graph.getIntegerNodeSet();

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

            return result;
        }
    }
}

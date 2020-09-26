package hu.xannosz.local.rerouting.algorithm;

import hu.xannosz.local.rerouting.core.algorithm.AlgorithmHelper;
import hu.xannosz.local.rerouting.core.algorithm.ListRoutingTable;
import hu.xannosz.local.rerouting.core.algorithm.ListRoutingTableUser;
import hu.xannosz.local.rerouting.core.interfaces.Algorithm;
import hu.xannosz.local.rerouting.core.interfaces.MatrixCreator;
import hu.xannosz.local.rerouting.core.interfaces.ReRouter;
import org.graphstream.graph.Graph;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@hu.xannosz.local.rerouting.core.annotation.Algorithm
public class Permutation implements Algorithm<ListRoutingTable.RoutingTable> {
    @Override
    public String getName() {
        return "Permutation";
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

            for (int v : nodes) {
                for (int next : nodes) {
                    Set<Integer> used = new HashSet<>();
                    Set<Integer> possibilities = new HashSet<>(nodes);
                    possibilities.remove(next);
                    for (int from : nodes) {
                        Set<Integer> now = new HashSet<>(possibilities);
                        now.removeAll(used);
                        now.removeAll(result.getRouting(from, next));
                        now.remove(from);

                        int size = now.size();
                        if (size < 1) {
                            continue;
                        }
                        int item = new java.util.Random().nextInt(size);
                        int i = 0;
                        int root = 0;
                        for (int r : now) {
                            if (i == item) {
                                root = r;
                                break;
                            }
                            i++;
                        }

                        used.add(root);
                        result.addRouting(from, next, root);
                    }
                }
            }

            return result.getRouting();
        }
    }
}

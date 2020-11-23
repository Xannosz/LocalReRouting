package hu.xannosz.local.rerouting.algorithm;

import hu.xannosz.local.rerouting.core.Network;
import hu.xannosz.local.rerouting.core.algorithm.ReroutingMatrixList;
import hu.xannosz.local.rerouting.core.interfaces.Algorithm;
import hu.xannosz.local.rerouting.core.interfaces.MatrixCreator;

import java.util.*;

@hu.xannosz.local.rerouting.core.annotation.Algorithm
public class AllToOne implements Algorithm {
    @Override
    public String getName() {
        return "All To One";
    }

    @Override
    public MatrixCreator getCreator() {
        return new Creator();
    }

    public static class Creator implements MatrixCreator {
        @Override
        public ReroutingMatrixList createMatrices(Network graph) {
            ReroutingMatrixList result = new ReroutingMatrixList();

            Set<Integer> nodes = graph.getIntegerNodeSet();

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

            return result;
        }
    }
}

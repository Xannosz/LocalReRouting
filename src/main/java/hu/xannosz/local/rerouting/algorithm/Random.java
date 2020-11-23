package hu.xannosz.local.rerouting.algorithm;

import hu.xannosz.local.rerouting.core.Network;
import hu.xannosz.local.rerouting.core.algorithm.ReroutingMatrixList;
import hu.xannosz.local.rerouting.core.interfaces.Algorithm;
import hu.xannosz.local.rerouting.core.interfaces.MatrixCreator;

import java.util.*;

@hu.xannosz.local.rerouting.core.annotation.Algorithm
public class Random implements Algorithm {
    @Override
    public String getName() {
        return "Random";
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

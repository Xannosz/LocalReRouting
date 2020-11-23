package hu.xannosz.local.rerouting.algorithm;

import hu.xannosz.local.rerouting.core.Network;
import hu.xannosz.local.rerouting.core.algorithm.ReroutingMatrixList;
import hu.xannosz.local.rerouting.core.interfaces.Algorithm;
import hu.xannosz.local.rerouting.core.interfaces.MatrixCreator;

import java.util.HashSet;
import java.util.Set;

@hu.xannosz.local.rerouting.core.annotation.Algorithm
public class Permutation implements Algorithm {
    @Override
    public String getName() {
        return "Permutation";
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

            return result;
        }
    }
}

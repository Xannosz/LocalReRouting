package hu.xannosz.local.rerouting.algorithm;

import hu.xannosz.local.rerouting.core.Network;
import hu.xannosz.local.rerouting.core.algorithm.ReroutingMatrixList;
import hu.xannosz.local.rerouting.core.interfaces.Algorithm;
import hu.xannosz.local.rerouting.core.interfaces.MatrixCreator;

import java.util.*;

@hu.xannosz.local.rerouting.core.annotation.Algorithm
public class BBID5 implements Algorithm {
    @Override
    public String getName() {
        return "BBID 5";
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

            for (int s = 0; s < 5; s++) {
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
                    result.addRouting(from, next, res);
                }
            }

            return result;
        }
    }
}

package hu.xannosz.local.rerouting.core.algorithm;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.*;

@ToString
public class ReroutingMatrixList {

    @Getter
    private final Map<Integer, ReroutingMatrix> routing = new HashMap<>();

    @Getter
    @Setter
    private int multiTrees;

    @Getter
    @Setter
    private boolean usedCongestionBorder;

    public List<Integer> getRouting(int node, int next) {
        if (!routing.containsKey(node)) {
            return Collections.singletonList(next);
        }
        return routing.get(node).getRouting(next);
    }

    public void setRoutingList(int node, int next, List<Integer> rout) {
        if (!routing.containsKey(node)) {
            routing.put(node, new ReroutingMatrix());
        }
        routing.get(node).setRoutingList(next, rout);
    }

    public void addRouting(int node, int next, List<Integer> rout) {
        for (int r : rout) {
            addRouting(node, next, r);
        }
    }

    public void addRouting(int node, int next, int rout) {
        if (!routing.containsKey(node)) {
            routing.put(node, new ReroutingMatrix());
        }
        routing.get(node).addRouting(next, rout);
    }

    @ToString
    public static class ReroutingMatrix {
        private final Map<Integer, List<Integer>> routingTable = new HashMap<>();

        public List<Integer> getRouting(int next) {
            List<Integer> result = new ArrayList<>();
            result.add(next);
            if (routingTable.containsKey(next)) {
                result.addAll(routingTable.get(next));
            }
            return result;
        }

        public void setRoutingList(int next, List<Integer> rout) {
            routingTable.put(next, rout);
        }

        public void addRouting(int next, int rout) {
            if (!routingTable.containsKey(next)) {
                routingTable.put(next, new ArrayList<>());
            }
            routingTable.get(next).add(rout);
        }
    }
}

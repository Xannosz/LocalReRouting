package hu.xannosz.local.rerouting.core.algorithm;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.*;

@ToString
public class ReroutingMatrixList {

    @Getter
    private final Map<Integer, ReroutingMatrix> routing = new HashMap<>();
    private final Map<Integer, Integer> counter = new HashMap<>();

    @Getter
    @Setter
    private boolean multiTrees = false;

    @Getter
    @Setter
    private boolean useCongestionBorder = false;

    @Getter
    @Setter
    private boolean useRandomization = false;

    @Getter
    @Setter
    private int randomizationsNumber = 5;

    @Getter
    @Setter
    private Genre genre = Genre.NORMAL;

    public List<Integer> getRouting(int node, int next) {
        int key = useRandomization ? node + getCounter(node) : node;
        if (!routing.containsKey(key)) {
            return Collections.singletonList(next);
        }
        return routing.get(key).getRouting(next);
    }

    public void setRoutingList(int key, int next, List<Integer> rout) {
        if (!routing.containsKey(key)) {
            routing.put(key, new ReroutingMatrix());
        }
        routing.get(key).setRoutingList(next, rout);
    }

    public void addRouting(int key, int next, List<Integer> rout) {
        for (int r : rout) {
            addRouting(key, next, r);
        }
    }

    public void addRouting(int key, int next, int rout) {
        if (!routing.containsKey(key)) {
            routing.put(key, new ReroutingMatrix());
        }
        routing.get(key).addRouting(next, rout);
    }

    private int getCounter(int node) {
        if (!counter.containsKey(node)) {
            counter.put(node, 0);
        }
        int c = counter.get(node);
        counter.put(node, (c + 1) % randomizationsNumber);
        return c;
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

    public enum Genre {
        NORMAL, TREE_MODEL, HYBRID
    }
}

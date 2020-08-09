package hu.xannosz.local.rerouting.core.algorithm;

import java.util.List;
import java.util.Map;

public class ListRoutingTable {
    public Map<Integer, List<Integer>> routing;

    public List<Integer> getRouting(int node) {
        return routing.get(node);
    }
}

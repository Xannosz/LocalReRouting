package hu.xannosz.local.rerouting.core.algorithm;

import org.graphstream.graph.Graph;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class AlgorithmHelper {
    public static Set<Integer> getNodeSet(Graph graph) {
        Set<Integer> nodes = new HashSet<>();
        for (int i = 0; i < graph.getNodeCount(); i++) {
            nodes.add(i);
        }
        return nodes;
    }

    public static List<Integer> removeItemFromList(List<Integer> list, int remove) {
        List<Integer> result = new ArrayList<>();
        for (int i : list) {
            if (i != remove) {
                result.add(i);
            }
        }
        return result;
    }
}

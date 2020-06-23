package hu.xannosz.local.rerouting.algorithm;

import hu.xannosz.local.rerouting.core.algorithm.MatrixCreator;
import hu.xannosz.local.rerouting.core.algorithm.ReRouter;
import org.graphstream.graph.Graph;

import java.util.*;

public class RandomReRouter extends ListRoutingTableUser implements MatrixCreator<ListRoutingTable>, ReRouter<ListRoutingTable> {
    @Override
    public Map<Integer, ListRoutingTable> createMatrices(Graph graph) {
        Map<Integer, ListRoutingTable> result = new HashMap<>();

        return result;
    }
}

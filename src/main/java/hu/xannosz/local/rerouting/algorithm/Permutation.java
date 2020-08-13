package hu.xannosz.local.rerouting.algorithm;

import hu.xannosz.local.rerouting.core.algorithm.*;
import org.graphstream.graph.Graph;

import java.util.Map;

@hu.xannosz.local.rerouting.core.annotation.Algorithm
public class Permutation implements Algorithm<ListRoutingTable> {
    @Override
    public String getName() {
        return "Permutation";
    }

    @Override
    public MatrixCreator<ListRoutingTable> getCreator() {
        return new Creator();
    }

    @Override
    public ReRouter<ListRoutingTable> getReRouter() {
        return new ListRoutingTableUser();
    }

    public static class Creator implements MatrixCreator<ListRoutingTable> {
        @Override
        public Map<Integer, ListRoutingTable> createMatrices(Graph graph) {
            return null;
        }
    }
}

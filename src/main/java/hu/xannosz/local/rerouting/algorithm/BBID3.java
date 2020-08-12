package hu.xannosz.local.rerouting.algorithm;

import hu.xannosz.local.rerouting.core.algorithm.*;
import org.graphstream.graph.Graph;

import java.util.Map;

public class BBID3 implements Algorithm<ListRoutingTable> {
    @Override
    public String getName() {
        return "BBID 3";
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
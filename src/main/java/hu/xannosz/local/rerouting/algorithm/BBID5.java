package hu.xannosz.local.rerouting.algorithm;

import hu.xannosz.local.rerouting.core.algorithm.*;
import org.graphstream.graph.Graph;

import java.util.Map;

@hu.xannosz.local.rerouting.core.annotation.Algorithm
public class BBID5 implements Algorithm<ListRoutingTable> {
    @Override
    public String getName() {
        return "BBID 5";
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

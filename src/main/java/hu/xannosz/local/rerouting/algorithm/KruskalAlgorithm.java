package hu.xannosz.local.rerouting.algorithm;

import hu.xannosz.local.rerouting.core.algorithm.ListRoutingTable;
import hu.xannosz.local.rerouting.core.algorithm.ListRoutingTableUser;
import hu.xannosz.local.rerouting.core.interfaces.Algorithm;
import hu.xannosz.local.rerouting.core.interfaces.MatrixCreator;
import hu.xannosz.local.rerouting.core.interfaces.ReRouter;
import org.graphstream.algorithm.Kruskal;
import org.graphstream.graph.Graph;

import java.util.Map;

//@hu.xannosz.local.rerouting.core.annotation.Algorithm
public class KruskalAlgorithm implements Algorithm<ListRoutingTable.RoutingTable> {
    @Override
    public String getName() {
        return "Kruskal";
    }

    @Override
    public MatrixCreator<ListRoutingTable.RoutingTable> getCreator() {
        return new Creator();
    }

    @Override
    public ReRouter<ListRoutingTable.RoutingTable> getReRouter() {
        return new ListRoutingTableUser();
    }

    public static class Creator implements MatrixCreator<ListRoutingTable.RoutingTable> {
        @Override
        public Map<Integer, ListRoutingTable.RoutingTable> createMatrices(Graph graph) {
            Kruskal kruskal = new Kruskal("ui.class", "inTree", "notInTree");

            kruskal.init(graph);
            kruskal.compute();

            return null;
        }
    }
}

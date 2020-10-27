package hu.xannosz.local.rerouting.statistic;

import hu.xannosz.local.rerouting.core.Network;
import hu.xannosz.local.rerouting.core.interfaces.Statistic;
import hu.xannosz.local.rerouting.core.statistic.DataSet;
import hu.xannosz.microtools.pack.Douplet;
import org.graphstream.graph.Edge;
import org.graphstream.graph.Node;

@hu.xannosz.local.rerouting.core.annotation.Statistic
public class MaxNodeLoad implements Statistic {

    private final DataSet dataSet = new DataSet("Max Node Load");

    @Override
    public void update(String key, Network network) {
        int max = 0;
        for (Node node : network.getNodeSet()) {
            int doubleMax = 0;
            for (Edge edge : node.getEdgeSet()) {
                Douplet<Integer, Integer> nodes = Network.edgeIdToIntInt(edge.getId());
                doubleMax += network.getTreeAggregateLabel(nodes.getFirst(), nodes.getSecond());
            }
            max = doubleMax / 2;
        }
        dataSet.addData(key, max);
    }

    @Override
    public DataSet getDataSet() {
        return dataSet;
    }
}

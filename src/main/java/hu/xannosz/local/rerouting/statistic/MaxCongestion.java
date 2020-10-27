package hu.xannosz.local.rerouting.statistic;

import hu.xannosz.local.rerouting.core.Network;
import hu.xannosz.local.rerouting.core.interfaces.Statistic;
import hu.xannosz.local.rerouting.core.statistic.DataSet;
import hu.xannosz.microtools.pack.Douplet;
import org.graphstream.graph.Edge;

@hu.xannosz.local.rerouting.core.annotation.Statistic
public class MaxCongestion implements Statistic {

    private final DataSet dataSet = new DataSet("Max Congestion");

    @Override
    public void update(String key, Network network) {
        int max = 0;
        for (Edge edge : network.getEdgeSet()) {
            Douplet<Integer, Integer> nodes = Network.edgeIdToIntInt(edge.getId());
            max = Math.max(max, network.getTreeAggregateLabel(nodes.getFirst(), nodes.getSecond()));
        }
        dataSet.addData(key, max);
    }

    @Override
    public DataSet getDataSet() {
        return dataSet;
    }
}

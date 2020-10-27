package hu.xannosz.local.rerouting.statistic;

import hu.xannosz.local.rerouting.core.Network;
import hu.xannosz.local.rerouting.core.interfaces.Statistic;
import hu.xannosz.local.rerouting.core.statistic.DataSet;
import hu.xannosz.microtools.pack.Douplet;
import org.graphstream.graph.Edge;

@hu.xannosz.local.rerouting.core.annotation.Statistic
public class AverageCongestion implements Statistic {

    private final DataSet dataSet = new DataSet("Average Congestion");

    @Override
    public void update(String key, Network network) {
        float avg = 0;
        float count = 0;
        for (Edge edge : network.getEdgeSet()) {
            Douplet<Integer, Integer> nodes = Network.edgeIdToIntInt(edge.getId());
            avg += network.getTreeAggregateLabel(nodes.getFirst(), nodes.getSecond());
            count++;
        }
        dataSet.addData(key, avg / (0.0f + count));
    }

    @Override
    public DataSet getDataSet() {
        return dataSet;
    }
}

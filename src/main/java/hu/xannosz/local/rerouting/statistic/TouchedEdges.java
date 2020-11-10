package hu.xannosz.local.rerouting.statistic;

import hu.xannosz.local.rerouting.core.Network;
import hu.xannosz.local.rerouting.core.PathRunner;
import hu.xannosz.local.rerouting.core.interfaces.Statistic;
import hu.xannosz.local.rerouting.core.statistic.DataSet;
import hu.xannosz.microtools.pack.Douplet;
import lombok.Getter;
import org.graphstream.graph.Edge;

@hu.xannosz.local.rerouting.core.annotation.Statistic
public class TouchedEdges implements Statistic {

    @Getter
    private final DataSet dataSet = new DataSet("Touched Edges");

    @Override
    public void update(String key, PathRunner.PathResponse response) {
        int count = 0;
        for (Edge edge : response.getGraph().getEdgeSet()) {
            Douplet<Integer, Integer> nodes = Network.edgeIdToIntInt(edge.getId());
            if (response.getGraph().getTreeAggregateLabel(nodes.getFirst(), nodes.getSecond()) > 0) {
                count++;
            }
        }
        dataSet.addData(key, count);
    }
}

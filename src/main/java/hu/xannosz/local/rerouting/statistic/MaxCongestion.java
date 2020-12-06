package hu.xannosz.local.rerouting.statistic;

import hu.xannosz.local.rerouting.core.PathRunner;
import hu.xannosz.local.rerouting.core.interfaces.Statistic;
import hu.xannosz.local.rerouting.core.statistic.DataSet;
import org.graphstream.graph.Edge;

@hu.xannosz.local.rerouting.core.annotation.Statistic
public class MaxCongestion extends Statistic {

    public MaxCongestion() {
        dataSet = new DataSet("Max Congestion");
    }

    @Override
    public void update(String key, PathRunner.PathResponse response) {
        int max = 0;
        for (Edge edge : response.getGraph().getEdgeSet()) {
            max = Math.max(max, response.getGraph().getTreeAggregateLabel(edge.getId()));
        }
        dataSet.addData(key, max);
    }
}

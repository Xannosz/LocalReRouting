package hu.xannosz.local.rerouting.statistic;

import hu.xannosz.local.rerouting.core.PathRunner;
import hu.xannosz.local.rerouting.core.interfaces.Statistic;
import hu.xannosz.local.rerouting.core.statistic.DataSet;
import org.graphstream.graph.Edge;

@hu.xannosz.local.rerouting.core.annotation.Statistic
public class AverageCongestion extends Statistic {

    public AverageCongestion() {
        dataSet = new DataSet("Average Congestion");
    }

    @Override
    public void update(String key, PathRunner.PathResponse response) {
        float avg = 0;
        float count = 0;
        for (Edge edge : response.getGraph().getEdgeSet()) {
            avg += response.getGraph().getTreeAggregateLabel(edge.getId());
            count++;
        }
        dataSet.addData(key, avg / (0.0f + count));
    }
}

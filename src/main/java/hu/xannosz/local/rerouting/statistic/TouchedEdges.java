package hu.xannosz.local.rerouting.statistic;

import hu.xannosz.local.rerouting.core.PathRunner;
import hu.xannosz.local.rerouting.core.interfaces.Statistic;
import hu.xannosz.local.rerouting.core.statistic.DataSet;
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
            if (response.getGraph().getTreeAggregateLabel(edge.getId()) > 0) {
                count++;
            }
        }
        dataSet.addData(key, count);
    }
}

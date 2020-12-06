package hu.xannosz.local.rerouting.statistic;

import hu.xannosz.local.rerouting.core.PathRunner;
import hu.xannosz.local.rerouting.core.interfaces.Statistic;
import hu.xannosz.local.rerouting.core.statistic.DataSet;
import org.graphstream.graph.Edge;
import org.graphstream.graph.Node;

@hu.xannosz.local.rerouting.core.annotation.Statistic
public class AverageNodeLoad extends Statistic {

    public AverageNodeLoad() {
        dataSet = new DataSet("Average Node Load");
    }

    @Override
    public void update(String key, PathRunner.PathResponse response) {
        float avg = 0;
        float count = 0;
        for (Node node : response.getGraph().getNodeSet()) {
            for (Edge edge : node.getEdgeSet()) {
                avg += response.getGraph().getTreeAggregateLabel(edge.getId()) / 2.0d;
            }
            count++;
        }
        dataSet.addData(key, avg / (0.0f + count));
    }
}

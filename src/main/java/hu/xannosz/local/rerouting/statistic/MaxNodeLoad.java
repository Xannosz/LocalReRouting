package hu.xannosz.local.rerouting.statistic;

import hu.xannosz.local.rerouting.core.PathRunner;
import hu.xannosz.local.rerouting.core.interfaces.Statistic;
import hu.xannosz.local.rerouting.core.statistic.DataSet;
import org.graphstream.graph.Edge;
import org.graphstream.graph.Node;

@hu.xannosz.local.rerouting.core.annotation.Statistic
public class MaxNodeLoad extends Statistic {

    public MaxNodeLoad() {
        dataSet = new DataSet("Max Node Load");
    }

    @Override
    public void update(String key, PathRunner.PathResponse response) {
        int max = 0;
        for (Node node : response.getGraph().getNodeSet()) {
            double possibleMax = 0;
            for (Edge edge : node.getEdgeSet()) {
                possibleMax += response.getGraph().getTreeAggregateLabel(edge.getId()) / 2.0d;
            }
            if (possibleMax > max) {
                max = (int) possibleMax;
            }
        }
        dataSet.addData(key, max);
    }
}

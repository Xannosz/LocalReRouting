package hu.xannosz.local.rerouting.statistic;

import hu.xannosz.local.rerouting.core.PathRunner;
import hu.xannosz.local.rerouting.core.interfaces.Statistic;
import hu.xannosz.local.rerouting.core.statistic.DataSet;
import org.graphstream.graph.Edge;
import org.graphstream.graph.Node;

@hu.xannosz.local.rerouting.core.annotation.Statistic
public class TouchedNodes extends Statistic {

    public TouchedNodes() {
        dataSet = new DataSet("Touched Nodes");
    }

    @Override
    public void update(String key, PathRunner.PathResponse response) {
        int count = 0;
        for (Node node : response.getGraph().getNodeSet()) {
            boolean touched = false;
            for (Edge edge : node.getEdgeSet()) {
                if (response.getGraph().getTreeAggregateLabel(edge.getId()) > 0) {
                    touched = true;
                    break;
                }
            }
            if (touched) {
                count++;
            }
        }
        dataSet.addData(key, count);
    }
}

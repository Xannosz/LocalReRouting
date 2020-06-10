package hu.xannosz.local.rerouting.core.graph;

import org.graphstream.graph.Graph;

public class GraphCreator {
    public static Graph createGraph(String graphType, int aNodes, int bNodes, int cNodes) {
        Graph result = GraphHelper.createGraph(graphType, aNodes + bNodes + cNodes);
        if (graphType.equals("Complete")) {
            GraphHelper.createCompleteGraph(result, GraphHelper.getNodesFromInterval(result, 0, aNodes), 0, 0);
        }
        if (graphType.equals("Pair")) {
            GraphHelper.createPairGraph(result, GraphHelper.getNodesFromInterval(result, 0, aNodes), GraphHelper.getNodesFromInterval(result, aNodes, aNodes + bNodes), 0, 0, 3);
        }
        if (graphType.equals("ThreeComplete")) {
            GraphHelper.createCompleteGraph(result, GraphHelper.getNodesFromInterval(result, 0, aNodes), 0, 0);
            GraphHelper.createCompleteGraph(result, GraphHelper.getNodesFromInterval(result, aNodes, aNodes + bNodes), 3, 3);
            GraphHelper.createCompleteGraph(result, GraphHelper.getNodesFromInterval(result, aNodes + bNodes, aNodes + bNodes + cNodes), 6, 0);

            GraphHelper.createPairGraph(result,
                    GraphHelper.getNodesFromInterval(result, 0, aNodes),
                    GraphHelper.getNodesFromInterval(result, aNodes, aNodes + bNodes));
            GraphHelper.createPairGraph(result,
                    GraphHelper.getNodesFromInterval(result, aNodes + bNodes, aNodes + bNodes + cNodes),
                    GraphHelper.getNodesFromInterval(result, aNodes, aNodes + bNodes));
            GraphHelper.createPairGraph(result,
                    GraphHelper.getNodesFromInterval(result, aNodes + bNodes, aNodes + bNodes + cNodes),
                    GraphHelper.getNodesFromInterval(result, 0, aNodes));
        }
        if (graphType.equals("Petersen")) {
            return GraphHelper.createPetersenGraph(graphType);
        }
        if (graphType.equals("Erdős–Rényí")) {
            result = GraphHelper.createGraph(graphType, aNodes );
            GraphHelper.createErdosRenyiGraph(result, GraphHelper.getNodesFromInterval(result, 0, aNodes), bNodes,0, 0);
        }
        return result;
    }
}

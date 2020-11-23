package hu.xannosz.local.rerouting.algorithm;

import hu.xannosz.local.rerouting.core.Network;
import hu.xannosz.local.rerouting.core.algorithm.ReroutingMatrixList;
import hu.xannosz.local.rerouting.core.interfaces.Algorithm;
import hu.xannosz.local.rerouting.core.interfaces.MatrixCreator;
import hu.xannosz.local.rerouting.core.util.Util;
import org.graphstream.graph.Edge;
import org.graphstream.graph.implementations.Graphs;

@hu.xannosz.local.rerouting.core.annotation.Algorithm
public class KruskalAlgorithmPreProcess implements Algorithm {
    @Override
    public String getName() {
        return "Kruskal";
    }

    @Override
    public MatrixCreator getCreator() {
        return new Creator();
    }

    public static class Creator implements MatrixCreator {
        @Override
        public ReroutingMatrixList createMatrices(Network graph) {
            Network innerGraph = (Network) Graphs.clone(graph);
            Util.getCriticalWeights(innerGraph);
            return Util.createKruskalReroutingMatrixList(innerGraph);
        }
    }
}

package hu.xannosz.local.rerouting.core.graph;

import java.util.Set;

public class CreateSpecialGraph {
    public static <T> Graph<T> createCompleteGraph(Set<T> nodes, boolean directed) {
        Graph<T> graph = new Graph<>(directed);
        graph.addNodes(nodes);
        for (T source : nodes) {
            for (T target : nodes) {
                graph.addEdge(source, target);
            }
        }
        return graph;
    }

    public static <T> Graph<T> createPairGraph(Set<T> aNodes, Set<T> bNodes, boolean directed) {
        Graph<T> graph = new Graph<>(directed);
        graph.addNodes(aNodes);
        graph.addNodes(bNodes);
        for (T source : aNodes) {
            for (T target : bNodes) {
                graph.addEdge(source, target);
            }
        }
        if (!directed) {
            for (T source : bNodes) {
                for (T target : aNodes) {
                    graph.addEdge(source, target);
                }
            }
        }
        return graph;
    }
}

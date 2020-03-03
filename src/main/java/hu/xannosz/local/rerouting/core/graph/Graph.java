package hu.xannosz.local.rerouting.core.graph;

import hu.xannosz.microtools.pack.Douplet;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import java.util.HashSet;
import java.util.Set;

@ToString
@EqualsAndHashCode
public class Graph<T> {

    @Getter
    private Set<T> nodes = new HashSet<>();
    @Getter
    private Set<Douplet<T, T>> edges = new HashSet<>();
    @Getter
    private boolean directed;

    public Graph(boolean directed) {
        this.directed = directed;
    }

    public boolean addNodes(Set<T> nodeSet) {
        return nodes.addAll(nodeSet);
    }

    public boolean addNode(T node) {
        return nodes.add(node);
    }

    public boolean addEdges(Set<Douplet<T, T>> edgeSet) {
        boolean result = false;
        for (Douplet<T, T> edge : edgeSet) {
            result |= addEdge(edge);
        }
        return result;
    }

    public boolean addEdge(T source, T target) {
        return addEdge(new Douplet<>(source, target));
    }

    public boolean addEdge(Douplet<T, T> edge) {
        if (!directed && containsEdge(edge.getSecond(), edge.getFirst())) {
            return true;
        }
        return edges.add(edge);
    }

    public boolean containsNode(T node) {
        return nodes.contains(node);
    }

    public boolean containsEdge(T source, T target) {
        return containsEdge(new Douplet<>(source, target));
    }

    public boolean containsEdge(Douplet<T, T> edge) {
        return edges.contains(edge);
    }

    public boolean addSubGraph(Graph<T> graph) {
        return addNodes(graph.getNodes()) | addEdges(graph.getEdges());
    }
}

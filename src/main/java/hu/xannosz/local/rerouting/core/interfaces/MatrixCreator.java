package hu.xannosz.local.rerouting.core.interfaces;

import org.graphstream.graph.Graph;

import java.util.Map;

public interface MatrixCreator<T> {
    Map<Integer, T> createMatrices(Graph graph);
}

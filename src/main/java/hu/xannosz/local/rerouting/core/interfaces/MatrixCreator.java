package hu.xannosz.local.rerouting.core.interfaces;

import hu.xannosz.local.rerouting.core.Network;

import java.util.Map;

public interface MatrixCreator<T> {
    Map<Integer, T> createMatrices(Network graph);
}

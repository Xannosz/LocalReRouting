package hu.xannosz.local.rerouting.core.interfaces;

import hu.xannosz.local.rerouting.core.Network;
import hu.xannosz.local.rerouting.core.algorithm.ReroutingMatrixList;

public interface MatrixCreator {
    ReroutingMatrixList createMatrices(Network graph);
}

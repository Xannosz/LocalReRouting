package hu.xannosz.local.rerouting.core.interfaces;

import hu.xannosz.local.rerouting.core.Network;
import hu.xannosz.local.rerouting.core.algorithm.ListRoutingTable;

public interface MatrixCreator {
    ListRoutingTable createMatrices(Network graph);
}

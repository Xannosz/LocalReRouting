package hu.xannosz.local.rerouting.algorithm;

import hu.xannosz.local.rerouting.core.Network;
import hu.xannosz.local.rerouting.core.algorithm.ListRoutingTable;
import hu.xannosz.local.rerouting.core.algorithm.ListRoutingTableUser;
import hu.xannosz.local.rerouting.core.interfaces.Algorithm;
import hu.xannosz.local.rerouting.core.interfaces.MatrixCreator;
import hu.xannosz.local.rerouting.core.interfaces.ReRouter;

//@hu.xannosz.local.rerouting.core.annotation.Algorithm
public class BBID3 implements Algorithm<ListRoutingTable.RoutingTable> {
    @Override
    public String getName() {
        return "BBID 3";
    }

    @Override
    public MatrixCreator getCreator() {
        return new Creator();
    }

    @Override
    public ReRouter getReRouter() {
        return new ListRoutingTableUser();
    }

    public static class Creator implements MatrixCreator {
        @Override
        public ListRoutingTable createMatrices(Network graph) {
            return null;
        }
    }
}

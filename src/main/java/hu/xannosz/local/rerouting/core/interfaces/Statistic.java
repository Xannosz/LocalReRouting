package hu.xannosz.local.rerouting.core.interfaces;

import hu.xannosz.local.rerouting.core.Network;
import hu.xannosz.local.rerouting.core.algorithm.Message;
import hu.xannosz.local.rerouting.core.statistic.DataSet;
import lombok.Data;

import java.util.Map;
import java.util.Set;

public interface Statistic {

    DataSet getDataSet();

    void update(String key, Network network);
}

package hu.xannosz.local.rerouting.core.interfaces;

import hu.xannosz.local.rerouting.core.PathRunner;
import hu.xannosz.local.rerouting.core.statistic.DataSet;

public interface Statistic {

    DataSet getDataSet();
    
    void update(String key, PathRunner.PathResponse responses);
}

package hu.xannosz.local.rerouting.core.interfaces;

import hu.xannosz.local.rerouting.core.PathRunner;
import hu.xannosz.local.rerouting.core.statistic.DataSet;
import lombok.Getter;

public class Statistic {

    @Getter
    protected DataSet dataSet;

    public void update(String key, PathRunner.PathResponse responses){
        //Should be override
    }
}

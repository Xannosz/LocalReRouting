package hu.xannosz.local.rerouting.statistic;

import hu.xannosz.local.rerouting.core.PathRunner;
import hu.xannosz.local.rerouting.core.algorithm.Message;
import hu.xannosz.local.rerouting.core.interfaces.Statistic;
import hu.xannosz.local.rerouting.core.statistic.DataSet;
import hu.xannosz.local.rerouting.core.util.Util;

import java.util.Map;
import java.util.Set;

@hu.xannosz.local.rerouting.core.annotation.Statistic
public class MaxNumberOfCircles extends Statistic {

    public MaxNumberOfCircles() {
        dataSet = new DataSet("Max Number Of Circles");
    }

    @Override
    public void update(String key, PathRunner.PathResponse response) {
        int max = 0;
        for (Map.Entry<Integer, Set<Message>> entry : response.getMessages().entrySet()) {
            for (Message message : entry.getValue()) {
                int circlesNumber = Util.getCirclesNumber(message);
                if (circlesNumber > max) {
                    max = circlesNumber;
                }
            }
        }
        dataSet.addData(key, max);
    }
}

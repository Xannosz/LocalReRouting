package hu.xannosz.local.rerouting.statistic;

import hu.xannosz.local.rerouting.core.PathRunner;
import hu.xannosz.local.rerouting.core.algorithm.Message;
import hu.xannosz.local.rerouting.core.interfaces.Statistic;
import hu.xannosz.local.rerouting.core.statistic.DataSet;
import lombok.Getter;

import java.util.Map;
import java.util.Set;

@hu.xannosz.local.rerouting.core.annotation.Statistic
public class LostPackages implements Statistic {

    @Getter
    private final DataSet dataSet = new DataSet("Lost Packages");

    @Override
    public void update(String key, PathRunner.PathResponse response) {
        int count = 0;
        for (Map.Entry<Integer, Set<Message>> entry : response.getMessages().entrySet()) {
            for (Message message : entry.getValue()) {
                if (message.brokenRooting) {
                    count++;
                }
            }
        }
        dataSet.addData(key, count);
    }
}

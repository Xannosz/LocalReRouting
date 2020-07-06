package hu.xannosz.local.rerouting.core.statistic;

import hu.xannosz.local.rerouting.core.algorithm.Message;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class StatisticHelper {
    public static Map<String, Integer> getConnectionCongestion(Map<Integer, Set<Message>> messages, Map<Integer, Set<Message>> newMessages) {
        Map<String, Integer> result = new HashMap<>();
        for (Map.Entry<Integer, Set<Message>> messageSet : messages.entrySet()) {
            for (Map.Entry<Integer, Set<Message>> newMessageSet : newMessages.entrySet()) {
                for (Message message : messageSet.getValue()) {
                    for (Message newMessage : newMessageSet.getValue()) {
                       // System.out.println("Itt vok");//TODO
                        if (message.id.equals(newMessage.id)) {
                            int l, b;
                            if (messageSet.getKey() >= newMessageSet.getKey()) {
                                l = newMessageSet.getKey();
                                b = messageSet.getKey();
                            } else {
                                b = newMessageSet.getKey();
                                l = messageSet.getKey();
                            }
                            String key = String.format("E: N: %s -> N: %s", l, b);
                            if (!result.containsKey(key)) {
                                result.put(key, 0);
                            }
                            result.put(key, result.get(key) + 1);
                        }
                    }
                }
            }
        }
        return result;
    }
}

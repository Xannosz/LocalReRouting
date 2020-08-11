package hu.xannosz.local.rerouting.core.util;

import hu.xannosz.local.rerouting.core.algorithm.Message;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class Util {
    public static void foldMap(Map<Integer, Set<Message>> newMessages, Map<Integer, Set<Message>> route) {
        for (Map.Entry<Integer, Set<Message>> messages : route.entrySet()) {
            if (!newMessages.containsKey(messages.getKey())) {
                newMessages.put(messages.getKey(), new HashSet<>());
            }
            newMessages.get(messages.getKey()).addAll(messages.getValue());
        }
    }
}

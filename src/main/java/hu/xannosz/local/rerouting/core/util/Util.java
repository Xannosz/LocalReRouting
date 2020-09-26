package hu.xannosz.local.rerouting.core.util;

import hu.xannosz.local.rerouting.core.algorithm.Message;

import java.util.*;

public class Util {
    public static void foldMap(Map<Integer, Set<Message>> newMessages, Map<Integer, Set<Message>> route) {
        for (Map.Entry<Integer, Set<Message>> messages : route.entrySet()) {
            if (!newMessages.containsKey(messages.getKey())) {
                newMessages.put(messages.getKey(), new HashSet<>());
            }
            newMessages.get(messages.getKey()).addAll(messages.getValue());
        }
    }

    public static List<List<Integer>> getPermutations(List<Integer> list, int maxResults){
        List<List<Integer>> result = new ArrayList<>();

        Integer[] indexes = new Integer[list.size()];
        Integer[] elements = list.toArray(new Integer[0]);
        for (int i = 0; i < list.size(); i++) {
            indexes[i] = 0;
        }

        result.add(new ArrayList<>(Arrays.asList(elements)));

        int i = 0;
        while (i < list.size()) {
            if (indexes[i] < i) {
                swap(elements, i % 2 == 0 ? 0 : indexes[i], i);
                result.add(new ArrayList<>(Arrays.asList(elements)));
                indexes[i]++;
                i = 0;
            } else {
                indexes[i] = 0;
                i++;
            }
            if(result.size()>=maxResults){
                break;
            }
        }

        return result;
    }

    private static <T> void swap(T[] input, int a, int b) {
        T tmp = input[a];
        input[a] = input[b];
        input[b] = tmp;
    }
}

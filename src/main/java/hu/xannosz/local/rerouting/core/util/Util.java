package hu.xannosz.local.rerouting.core.util;

import hu.xannosz.local.rerouting.core.Network;
import hu.xannosz.local.rerouting.core.algorithm.Message;
import org.graphstream.algorithm.Dijkstra;
import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;

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

    public static List<List<Integer>> getPermutations(List<Integer> list, int maxResults) {
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
            if (result.size() >= maxResults) {
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

    public static List<Integer> removeItemFromList(List<Integer> list, int remove) {
        List<Integer> result = new ArrayList<>();
        for (int i : list) {
            if (i != remove) {
                result.add(i);
            }
        }
        return result;
    }

    public static Map<String, Integer> getConnectionCongestion(Map<Integer, Set<Message>> messages, Map<Integer, Set<Message>> newMessages) {
        Map<String, Integer> result = new HashMap<>();
        for (Map.Entry<Integer, Set<Message>> messageSet : messages.entrySet()) {
            for (Map.Entry<Integer, Set<Message>> newMessageSet : newMessages.entrySet()) {
                for (Message message : messageSet.getValue()) {
                    for (Message newMessage : newMessageSet.getValue()) {
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

    public static Map<Integer, Set<Integer>> getReachableNodes(int node, Graph graph) {
        Map<Integer, Set<Integer>> result = new HashMap<>();
        Dijkstra dijkstra = new Dijkstra(Dijkstra.Element.EDGE_AND_NODE, "result", "length");
        dijkstra.init(graph);
        dijkstra.setSource(graph.getNode("N: " + node));
        dijkstra.compute();
        Iterator<Node> iterator = graph.getNode("N: " + node).getNeighborNodeIterator();
        while (iterator.hasNext()) {
            result.put(Network.getNodeNumber(iterator.next()), new HashSet<>());
        }
        for (Node i : graph.getNodeSet()) {
            if (Network.getNodeNumber(i) != node) {
                System.out.println("###:" + dijkstra);
                System.out.println("###:" + dijkstra.getPath(i));
                System.out.println("###:" + dijkstra.getPath(i).getNodePath());
                List<Node> list = dijkstra.getPath(i).getNodePath();
                if (list.size() > 0) {
                    result.get(Network.getNodeNumber(list.get(0))).add(Network.getNodeNumber(i));
                }
            }
        }
        return result;
    }

    public static List<Node> concatLists(List<Node>... lists) {
        Set<Node> result = new HashSet<>();
        for (List<Node> list : lists) {
            result.addAll(list);
        }
        return new ArrayList<>(result);
    }
}

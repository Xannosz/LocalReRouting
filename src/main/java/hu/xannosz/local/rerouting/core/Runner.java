package hu.xannosz.local.rerouting.core;

import hu.xannosz.local.rerouting.core.algorithm.MatrixCreator;
import hu.xannosz.local.rerouting.core.algorithm.Message;
import hu.xannosz.local.rerouting.core.algorithm.ReRouter;
import hu.xannosz.local.rerouting.core.graph.GraphHelper;
import hu.xannosz.local.rerouting.core.statistic.Statistic;
import hu.xannosz.local.rerouting.core.util.Constants;
import org.graphstream.graph.Graph;

import java.util.*;


public class Runner {

    private final MatrixCreator<?> creator;
    private final ReRouter<?> reRouter;

    private final Graph graph;
    private Map<Integer, Set<Message>> messages = new HashMap<>();
    private Map<Integer, ?> routingTables;

    public Runner(MatrixCreator<?> creator, ReRouter<?> reRouter, Graph graph) {
        this.creator = creator;
        this.reRouter = reRouter;
        this.graph = graph;

        for (int i = 0; i < graph.getNodeCount(); i++) {
            Message message = new Message();
            message.from = i;
            message.to = (new Random()).nextInt(graph.getNodeCount());
            messages.put(i, Collections.singleton(message));
        }
    }

    public void createMatrices() {
        routingTables = creator.createMatrices(graph);
    }

    public void step() {
        Map<Integer, Set<Message>> newMessages = new HashMap<>();
        for (int i = 0; i < graph.getNodeCount(); i++) {
            foldMap(newMessages, reRouter.convertAndRoute(i, routingTables.get(i), messages.get(i), GraphHelper.getConnects(i, graph)));
        }
        for (Statistic statistic : Constants.STATISTICS) {
            statistic.update(messages, newMessages);
        }
        messages = newMessages;
    }

    private void foldMap(Map<Integer, Set<Message>> newMessages, Map<Integer, Set<Message>> route) {
        for (Map.Entry<Integer, Set<Message>> messages : route.entrySet()) {
            if (!newMessages.containsKey(messages.getKey())) {
                newMessages.put(messages.getKey(), new HashSet<>());
            }
            newMessages.get(messages.getKey()).addAll(messages.getValue());
        }
    }
}

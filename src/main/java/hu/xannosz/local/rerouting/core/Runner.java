package hu.xannosz.local.rerouting.core;

import hu.xannosz.local.rerouting.core.algorithm.MatrixCreator;
import hu.xannosz.local.rerouting.core.algorithm.Message;
import hu.xannosz.local.rerouting.core.algorithm.ReRouter;
import hu.xannosz.local.rerouting.core.graph.GraphHelper;
import hu.xannosz.local.rerouting.core.statistic.Statistic;
import org.graphstream.graph.Graph;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;


public class Runner<T> {

    private MatrixCreator<T> creator;
    private ReRouter<T> reRouter;

    private Graph graph;
    private Map<Integer, Set<Message>> messages;
    private Map<Integer, T> routingTables;
    private Set<Statistic> statistics;

    public Runner(MatrixCreator<T> creator, ReRouter<T> reRouter, Graph graph, Set<Statistic> statistics) {
        this.creator = creator;
        this.reRouter = reRouter;
        this.graph = graph;
        this.statistics = statistics;
    }

    public void createMatrices() {
        routingTables = creator.createMatrices(graph);
    }

    public void step() {
        Map<Integer, Set<Message>> newMessages = new HashMap<>();
        for (int i = 0; i < graph.getNodeCount(); i++) {
            foldMap(newMessages, reRouter.route(i, routingTables.get(i), messages.get(i), GraphHelper.getConnects(i, graph)));
        }

        for (Statistic statistic : statistics) {
            statistic.update(messages, newMessages);
        }
        messages = newMessages;
    }

    private void foldMap(Map<Integer, Set<Message>> newMessages, Map<Integer, Set<Message>> route) {
        for (Map.Entry<Integer, Set<Message>> messages : route.entrySet()) {
            newMessages.get(messages.getKey()).addAll(messages.getValue());
        }
    }
}

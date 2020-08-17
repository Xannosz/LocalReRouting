package hu.xannosz.local.rerouting.core;

import hu.xannosz.local.rerouting.core.interfaces.MatrixCreator;
import hu.xannosz.local.rerouting.core.algorithm.Message;
import hu.xannosz.local.rerouting.core.interfaces.ReRouter;
import hu.xannosz.local.rerouting.core.util.GraphHelper;
import hu.xannosz.local.rerouting.core.statistic.Visualiser;
import org.graphstream.graph.Graph;

import java.util.*;

import static hu.xannosz.local.rerouting.core.util.Util.foldMap;


public class Runner {

    private final MatrixCreator<?> creator;
    private final ReRouter<?> reRouter;

    private final Graph graph;
    private Map<Integer, Set<Message>> messages = new HashMap<>();
    private Map<Integer, ?> routingTables;
    private final Visualiser visualiser;

    public Runner(MatrixCreator<?> creator, ReRouter<?> reRouter, Graph graph, Visualiser visualiser) {
        this.creator = creator;
        this.reRouter = reRouter;
        this.graph = graph;
        this.visualiser = visualiser;

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
        visualiser.update(messages, newMessages);
        messages = newMessages;
    }
}

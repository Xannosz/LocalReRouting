package hu.xannosz.local.rerouting.core;

import hu.xannosz.local.rerouting.core.algorithm.ListRoutingTable;
import hu.xannosz.local.rerouting.core.algorithm.Message;
import hu.xannosz.local.rerouting.core.interfaces.MatrixCreator;
import hu.xannosz.local.rerouting.core.interfaces.MessageGenerator;
import hu.xannosz.local.rerouting.core.interfaces.ReRouter;
import hu.xannosz.local.rerouting.core.statistic.Visualiser;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import static hu.xannosz.local.rerouting.core.util.Util.foldMap;


public class Runner {

    private final MatrixCreator creator;
    private final ReRouter reRouter;

    private final Network graph;
    private Map<Integer, Set<Message>> messages = new HashMap<>();
    private ListRoutingTable routingTables;
    private final Visualiser visualiser;

    public Runner(MatrixCreator creator, ReRouter reRouter, Network graph, MessageGenerator<?> generator, Object generatorSettings) {
        this.creator = creator;
        this.reRouter = reRouter;
        this.graph = graph;
        this.visualiser = new Visualiser(graph);

        messages.putAll(generator.convertAndGetMessages(graph, generatorSettings));
    }

    public void createMatrices() {
        routingTables = creator.createMatrices(graph);
    }

    public void step() {
        Map<Integer, Set<Message>> newMessages = new HashMap<>();
        for (int i = 0; i < graph.getNodeCount(); i++) {
            foldMap(newMessages, reRouter.route(i, routingTables, messages.get(i), graph.getConnects(i)));
        }
        visualiser.update(messages, newMessages);
        messages = newMessages;
    }
}

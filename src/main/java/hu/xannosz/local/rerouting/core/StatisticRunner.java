package hu.xannosz.local.rerouting.core;

import hu.xannosz.local.rerouting.core.algorithm.Message;
import hu.xannosz.local.rerouting.core.interfaces.Algorithm;
import hu.xannosz.local.rerouting.core.interfaces.GraphType;
import hu.xannosz.local.rerouting.core.interfaces.MessageGenerator;
import hu.xannosz.local.rerouting.core.interfaces.Statistic;
import hu.xannosz.local.rerouting.core.util.Constants;
import hu.xannosz.local.rerouting.core.util.GraphHelper;
import lombok.Data;
import org.graphstream.graph.Graph;

import java.util.*;

import static hu.xannosz.local.rerouting.core.util.Util.foldMap;

public class StatisticRunner {

    private static int NUMBER = 0;

    private final GraphType<?> graphType;
    private final Algorithm<?> algorithm;
    private int number = 0;

    private final List<GraphContainer> containers = new ArrayList<>();

    public StatisticRunner(final GraphType<?> graphType, final Algorithm<?> algorithm, int count, final Object graphSettings, MessageGenerator<?> generator, Object generatorSettings) {
        this.graphType = graphType;
        this.algorithm = algorithm;
        number = ++NUMBER;
        init(count, graphSettings, generator, generatorSettings);
    }

    private void init(int count, Object graphSettings, MessageGenerator<?> generator, Object generatorSettings) {
        for (int e = 0; e < count; e++) {
            GraphContainer container = new GraphContainer();
            container.graph = graphType.convertAndCreateGraph(graphSettings);
            container.routingTables = algorithm.getCreator().createMatrices(container.graph);

            container.messages.putAll(generator.convertAndGetMessages(container.graph, generatorSettings));
            
            containers.add(container);
        }
    }

    public void step() {
        Set<Statistic.MessageContainer> messageContainers = new HashSet<>();
        for (GraphContainer container : containers) {
            Map<Integer, Set<Message>> newMessages = new HashMap<>();
            for (int i = 0; i < container.graph.getNodeCount(); i++) {
                foldMap(newMessages, algorithm.getReRouter().convertAndRoute(i, container.routingTables.get(i), container.messages.get(i), GraphHelper.getConnects(i, container.graph)));
            }

            Statistic.MessageContainer messageContainer = new Statistic.MessageContainer();
            messageContainer.setOldMessages(container.messages);
            messageContainer.setNewMessages(newMessages);
            messageContainers.add(messageContainer);

            container.messages = newMessages;
        }
        for (Statistic statistic : Constants.STATISTICS) {
            statistic.update(graphType.getName() + " (" + number + ")", messageContainers);
        }
    }

    @Data
    public static class GraphContainer {
        private Graph graph;
        private Map<Integer, Set<Message>> messages = new HashMap<>();
        private Map<Integer, ?> routingTables;
    }
}

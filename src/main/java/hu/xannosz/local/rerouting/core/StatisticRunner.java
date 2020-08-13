package hu.xannosz.local.rerouting.core;

import hu.xannosz.local.rerouting.core.algorithm.Algorithm;
import hu.xannosz.local.rerouting.core.algorithm.Message;
import hu.xannosz.local.rerouting.core.graph.GraphHelper;
import hu.xannosz.local.rerouting.core.graph.GraphType;
import hu.xannosz.local.rerouting.core.statistic.Statistic;
import hu.xannosz.local.rerouting.core.util.Constants;
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

    public StatisticRunner(final GraphType<?> graphType, final Algorithm<?> algorithm, int count, final Object settings) {
        this.graphType = graphType;
        this.algorithm = algorithm;
        number = ++NUMBER;
        init(count, settings);
    }

    private void init(int count, Object settings) {
        for (int e = 0; e < count; e++) {
            GraphContainer container = new GraphContainer();
            container.graph = graphType.convertAndCreateGraph(settings);
            container.routingTables = algorithm.getCreator().createMatrices(container.graph);

            for (int i = 0; i < container.graph.getNodeCount(); i++) {
                Message message = new Message();
                message.from = i;
                message.to = (new Random()).nextInt(container.graph.getNodeCount());
                container.messages.put(i, Collections.singleton(message));
            }

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

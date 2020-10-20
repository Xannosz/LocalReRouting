package hu.xannosz.local.rerouting.core;

import hu.xannosz.local.rerouting.core.algorithm.ListRoutingTable;
import hu.xannosz.local.rerouting.core.algorithm.Message;
import hu.xannosz.local.rerouting.core.interfaces.Algorithm;
import hu.xannosz.local.rerouting.core.interfaces.FailureGenerator;
import hu.xannosz.local.rerouting.core.interfaces.GraphType;
import hu.xannosz.local.rerouting.core.interfaces.MessageGenerator;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class PathRunner {

    private final Algorithm<?> algorithm;
    private final GraphType<?> graphType;
    private final Object graphSettings;
    private final MessageGenerator<?> messageGenerator;
    private final Object messageGeneratorSettings;
    private final FailureGenerator<?> failureGenerator;
    private final Object failureGeneratorSettings;
    private boolean multiTrees;
    private int maxCongestion;

    public PathRunner(final Algorithm<?> algorithm, final GraphType<?> graphType, final Object graphSettings,
                      MessageGenerator<?> messageGenerator, Object messageGeneratorSettings,
                      FailureGenerator<?> failureGenerator, Object failureGeneratorSettings,
                      boolean multiTrees, int maxCongestion) {
        this.algorithm = algorithm;
        this.graphType = graphType;
        this.graphSettings = graphSettings;
        this.messageGenerator = messageGenerator;
        this.messageGeneratorSettings = messageGeneratorSettings;
        this.failureGenerator = failureGenerator;
        this.failureGeneratorSettings = failureGeneratorSettings;
        this.multiTrees = multiTrees;
        this.maxCongestion = maxCongestion;
    }

    public PathResponse createPaths() {
        Network graph = graphType.convertAndCreateGraph(graphSettings);
        failureGenerator.convertAndCreateFailures(graph, failureGeneratorSettings);
        Map<Integer, Set<Message>> messages = messageGenerator.convertAndGetMessages(graph, messageGeneratorSettings);
        ListRoutingTable matrices = algorithm.getCreator().createMatrices(graph);

        for (Map.Entry<Integer, Set<Message>> entry : messages.entrySet()) {
            for (Message message : entry.getValue()) {
                run(graph, message, matrices);
            }
        }

        PathResponse pathResponse = new PathResponse();
        pathResponse.setGraph(graph);
        pathResponse.setGraphTypeName(graphType.getName());
        pathResponse.setFailureGeneratorName(failureGenerator.getName());
        pathResponse.setMessageGeneratorName(messageGenerator.getName());
        pathResponse.setAlgorithmName(algorithm.getName());
        pathResponse.setMultiTrees(multiTrees);
        pathResponse.setMaxCongestion(maxCongestion);
        return pathResponse;
    }

    private void run(Network graph, Message message, ListRoutingTable matrices) {
        int node = message.from;
        while (node != message.to) {
            List<Integer> possibleNodes = new ArrayList<>();
            addPossibleNode(graph, node, message.to, possibleNodes);
            for (int routingNode : matrices.getRouting(node, message.to)) {
                addPossibleNode(graph, node, routingNode, possibleNodes);
            }

            if (possibleNodes.size() == 0) {
                throw new BrokenRoutingException();
            }

            if (multiTrees) {
                //TODO
            } else {
                int next = possibleNodes.get(0);
                graph.increaseTreeLabel(node, next, 0);
                node = next;
            }
        }
    }

    private void addPossibleNode(Network graph, int node, int to, List<Integer> possibleNodes) {
        if (graph.hasEdge(node, to)) {
            if (maxCongestion == 0) {
                possibleNodes.add(to);
            } else {
                if (maxCongestion > graph.getTreeAggregateLabel(node, to)) {
                    possibleNodes.add(to);
                }
            }
        }
    }

    @Data
    public static class PathResponse {
        private Network graph;
        private String graphTypeName;
        private String failureGeneratorName;
        private String messageGeneratorName;
        private String algorithmName;
        private boolean multiTrees;
        private int maxCongestion;
    }
}

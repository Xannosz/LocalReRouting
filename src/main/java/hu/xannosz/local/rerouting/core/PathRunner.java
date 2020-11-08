package hu.xannosz.local.rerouting.core;

import hu.xannosz.local.rerouting.core.algorithm.ListRoutingTable;
import hu.xannosz.local.rerouting.core.algorithm.Message;
import hu.xannosz.local.rerouting.core.interfaces.Algorithm;
import hu.xannosz.local.rerouting.core.interfaces.FailureGenerator;
import hu.xannosz.local.rerouting.core.interfaces.GraphType;
import hu.xannosz.local.rerouting.core.interfaces.MessageGenerator;
import hu.xannosz.microtools.pack.Douplet;
import lombok.Data;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.Set;

public class PathRunner {

    private final Algorithm algorithm;
    private final GraphType<?> graphType;
    private final Object graphSettings;
    private final MessageGenerator<?> messageGenerator;
    private final Object messageGeneratorSettings;
    private final FailureGenerator<?> failureGenerator;
    private final Object failureGeneratorSettings;
    private final int multiTrees;
    private final int maxCongestion;

    public PathRunner(final Algorithm algorithm, final GraphType<?> graphType, final Object graphSettings,
                      MessageGenerator<?> messageGenerator, Object messageGeneratorSettings,
                      FailureGenerator<?> failureGenerator, Object failureGeneratorSettings,
                      int multiTrees, int maxCongestion) {
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
                try {
                    run(graph, message, matrices);
                } catch (BrokenRoutingException e) {
                    message.brokenRooting = true;
                }
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
        pathResponse.setMatrices(matrices);
        return pathResponse;
    }

    private void run(Network graph, Message message, ListRoutingTable matrices) {
        int node = message.from;
        int startedTree = multiTrees < 2 ? 0 : (new Random()).nextInt(multiTrees);
        int usedTree = startedTree;
        while (node != message.to) {
            Map<Integer, Integer> possibleNodes = new HashMap<>();
            int treeNumber = 0;
            addPossibleNode(graph, node, message.to, treeNumber, possibleNodes);
            for (int routingNode : matrices.getRouting(node, message.to)) {
                treeNumber++;
                addPossibleNode(graph, node, routingNode, treeNumber, possibleNodes);
            }

            usedTree = getUsedTree(startedTree, usedTree, possibleNodes, message.visitedNodesMap);
            int next = possibleNodes.get(usedTree);
            message.visitedNodesMap.add(new Douplet<>(usedTree, next));
            graph.increaseTreeLabel(node, next, usedTree);
            node = next;
        }
    }

    private int getUsedTree(int startedTree, int usedTree, Map<Integer, Integer> possibleNodes, Set<Douplet<Integer, Integer>> visitedNodesMap) {
        int resultTree = -1;
        for (Map.Entry<Integer, Integer> entry : possibleNodes.entrySet()) {
            if (entry.getKey() >= usedTree && (resultTree == -1 || entry.getKey() < resultTree) && !visitedNodesMap.contains(new Douplet<>(entry.getKey(), entry.getValue()))) {
                resultTree = entry.getKey();
            }
        }
        if (resultTree == -1) {
            for (Map.Entry<Integer, Integer> entry : possibleNodes.entrySet()) {
                if (entry.getKey() < startedTree && (resultTree == -1 || entry.getKey() < resultTree) && !visitedNodesMap.contains(new Douplet<>(entry.getKey(), entry.getValue()))) {
                    resultTree = entry.getKey();
                }
            }
            if (resultTree == -1) {
                throw new BrokenRoutingException();
            }
        }
        return resultTree;
    }

    private void addPossibleNode(Network graph, int node, int to, int tree, Map<Integer, Integer> possibleNodes) {
        if (graph.hasEdge(node, to)) {
            if (maxCongestion == 0) {
                possibleNodes.put(tree, to);
            } else {
                if (maxCongestion > graph.getTreeAggregateLabel(node, to)) {
                    possibleNodes.put(tree, to);
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
        private int multiTrees;
        private int maxCongestion;
        private ListRoutingTable matrices;
    }
}

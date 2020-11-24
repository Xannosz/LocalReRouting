package hu.xannosz.local.rerouting.core;

import hu.xannosz.local.rerouting.core.algorithm.Message;
import hu.xannosz.local.rerouting.core.algorithm.ReroutingMatrixList;
import hu.xannosz.local.rerouting.core.interfaces.Algorithm;
import hu.xannosz.local.rerouting.core.interfaces.FailureGenerator;
import hu.xannosz.local.rerouting.core.interfaces.GraphType;
import hu.xannosz.local.rerouting.core.interfaces.MessageGenerator;
import hu.xannosz.local.rerouting.core.launcher.AlgorithmSettingsPanel;
import hu.xannosz.microtools.pack.Douplet;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.Set;

public class PathRunner {

    private final Algorithm algorithm;
    private final AlgorithmSettingsPanel.Settings algorithmSettings;
    private final GraphType<?> graphType;
    private final Object graphSettings;
    private final MessageGenerator<?> messageGenerator;
    private final Object messageGeneratorSettings;
    private final FailureGenerator<?> failureGenerator;
    private final Object failureGeneratorSettings;

    public PathRunner(final Algorithm algorithm, final AlgorithmSettingsPanel.Settings algorithmSettings,
                      final GraphType<?> graphType, final Object graphSettings,
                      final MessageGenerator<?> messageGenerator, final Object messageGeneratorSettings,
                      final FailureGenerator<?> failureGenerator, final Object failureGeneratorSettings) {
        this.algorithm = algorithm;
        this.algorithmSettings = algorithmSettings;
        this.graphType = graphType;
        this.graphSettings = graphSettings;
        this.messageGenerator = messageGenerator;
        this.messageGeneratorSettings = messageGeneratorSettings;
        this.failureGenerator = failureGenerator;
        this.failureGeneratorSettings = failureGeneratorSettings;
    }

    public PathResponse createPaths() {
        Network graph = graphType.convertAndCreateGraph(graphSettings);
        failureGenerator.convertAndCreateFailures(graph, failureGeneratorSettings);
        Map<Integer, Set<Message>> messages = messageGenerator.convertAndGetMessages(graph, messageGeneratorSettings);
        ReroutingMatrixList matrices = algorithm.getCreator(algorithmSettings).createMatrices(graph);

        for (Map.Entry<Integer, Set<Message>> entry : messages.entrySet()) {
            for (Message message : entry.getValue()) {
                try {
                    run(graph, message, matrices);
                } catch (BrokenRoutingException e) {
                    message.brokenRooting = true;
                }
            }
        }

        return new PathResponse(graph
                , graphType.getName()
                , failureGenerator.getName()
                , messageGenerator.getName()
                , algorithm.getName()
                , matrices
                , graphSettings
                , messageGeneratorSettings
                , failureGeneratorSettings
                , messages);
    }

    private void run(Network graph, Message message, ReroutingMatrixList matrices) {
        int node = message.from;
        int startedTree = matrices.isMultiTrees() ? (new Random()).nextInt(graph.getNodeCount()) : 0;
        int usedTree = startedTree;
        while (node != message.to) {
            Map<Integer, Integer> possibleNodes = new HashMap<>();
            int treeNumber = 0;
            addPossibleNode(graph, node, message.to, treeNumber, possibleNodes, matrices.isUseCongestionBorder());
            for (int routingNode : matrices.getRouting(node, message.to)) {
                treeNumber++;
                addPossibleNode(graph, node, routingNode, treeNumber, possibleNodes, matrices.isUseCongestionBorder());
            }

            usedTree = getUsedTree(startedTree, usedTree, possibleNodes, message.visitedNodesMap, matrices.getGenre(), graph.getNodeCount());
            int next = possibleNodes.get(usedTree);
            message.visitedNodesMap.add(new Douplet<>(usedTree, next));
            graph.increaseTreeLabel(graph.getEdge(node, next).getId(), usedTree);
            node = next;
        }
    }

    private int getUsedTree(int startedTree, int usedTree, Map<Integer, Integer> possibleNodes,
                            Set<Douplet<Integer, Integer>> visitedNodesMap, ReroutingMatrixList.Genre genre, int modelSize) {

        int resultTree = -1;

        switch (genre) {
            case NORMAL:
                for (Map.Entry<Integer, Integer> entry : possibleNodes.entrySet()) {
                    if ((resultTree == -1 || entry.getKey() < resultTree) && !visitedNodesMap.contains(new Douplet<>(entry.getKey(), entry.getValue()))) {
                        resultTree = entry.getKey();
                    }
                }
                break;
            case TREE_MODEL:
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
                }
                break;
            case HYBRID:
                for (Map.Entry<Integer, Integer> entry : possibleNodes.entrySet()) {
                    if (entry.getKey() >= usedTree && entry.getKey() <= modelSize && (resultTree == -1 || entry.getKey() < resultTree) && !visitedNodesMap.contains(new Douplet<>(entry.getKey(), entry.getValue()))) {
                        resultTree = entry.getKey();
                    }
                }
                if (resultTree == -1) {
                    for (Map.Entry<Integer, Integer> entry : possibleNodes.entrySet()) {
                        if (entry.getKey() < startedTree && (resultTree == -1 || entry.getKey() < resultTree) && !visitedNodesMap.contains(new Douplet<>(entry.getKey(), entry.getValue()))) {
                            resultTree = entry.getKey();
                        }
                    }
                }
                if (resultTree == -1) {
                    for (Map.Entry<Integer, Integer> entry : possibleNodes.entrySet()) {
                        if ((resultTree == -1 || entry.getKey() < resultTree) && !visitedNodesMap.contains(new Douplet<>(entry.getKey(), entry.getValue()))) {
                            resultTree = entry.getKey();
                        }
                    }
                }
                break;
        }

        if (resultTree == -1) {
            throw new BrokenRoutingException();
        }

        return resultTree;
    }

    private void addPossibleNode(Network graph, int node, int to, int tree, Map<Integer, Integer> possibleNodes, boolean usedCongestionBorder) {
        if (graph.hasEdge(node, to)) {
            if (usedCongestionBorder) {
                if (Math.sqrt(graph.getNodeCount()) > graph.getTreeAggregateLabel(graph.getEdge(node, to).getId())) {
                    possibleNodes.put(tree, to);
                }
            } else {
                possibleNodes.put(tree, to);
            }
        }
    }

    @Data
    @AllArgsConstructor
    public static class PathResponse {
        private Network graph;
        private String graphTypeName;
        private String failureGeneratorName;
        private String messageGeneratorName;
        private String algorithmName;
        private ReroutingMatrixList matrices;
        private Object graphSettings;
        private Object messageGeneratorSettings;
        private Object failureGeneratorSettings;
        private Map<Integer, Set<Message>> messages;
    }
}

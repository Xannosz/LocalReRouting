package hu.xannosz.local.rerouting.core.graph;

import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;
import org.graphstream.graph.implementations.SingleGraph;

public class CreateSpecialGraph {
    public static Graph createCompleteGraph(String id, int nodes) {
        Graph graph = new SingleGraph(id);
        Node A[] = new Node[nodes];
        for (int i = 0; i < nodes; i++) {
            A[i] = graph.addNode("N: " + i);
            A[i].addAttribute("layout.frozen");
            A[i].addAttribute("xy", Math.sin((double) i/nodes*Math.PI*2), Math.cos((double)i/nodes*Math.PI*2));
        }
        for (int i = 0; i < nodes; i++) {
            for (int e = i; e < nodes; e++) {
                graph.addEdge("E: " + i + " -> " + e, A[i], A[e]);
            }
        }
        return graph;
    }

    public static Graph createPairGraph(String id, int aNodes, int bNodes) {
        Graph graph = new SingleGraph(id);
        Node A[] = new Node[aNodes];
        for (int i = 0; i < aNodes; i++) {
            A[i] = graph.addNode("A: " + i);
            A[i].addAttribute("layout.frozen");
            A[i].addAttribute("xy", i, 3);
        }
        Node B[] = new Node[bNodes];
        for (int i = 0; i < bNodes; i++) {
            B[i] = graph.addNode("B: " + i);
            B[i].addAttribute("layout.frozen");
            B[i].addAttribute("xy", i, -3);
        }
        for (int i = 0; i < aNodes; i++) {
            for (int e = 0; e < bNodes; e++) {
                graph.addEdge("E: " + i + " -> " + e, A[i], B[e]);
            }
        }
        return graph;
    }

    public static Graph createPetersenGraph(String id) {
        Graph graph = new SingleGraph(id);
        Node A[] = new Node[10];
        for (int i = 0; i < 10; i++) {
            A[i] = graph.addNode("N: " + i);
            A[i].addAttribute("layout.frozen");
        }

        A[0].addAttribute("xy", -2, -1);
        A[1].addAttribute("xy", 0, 3);
        A[2].addAttribute("xy", 5, 1);
        A[3].addAttribute("xy", 3, -5);
        A[4].addAttribute("xy", -3, -5);
        A[5].addAttribute("xy", -5, 1);
        A[6].addAttribute("xy", 0, 1);
        A[7].addAttribute("xy", 2, -1);
        A[8].addAttribute("xy", 1, -3);
        A[9].addAttribute("xy", -1, -3);

        addEdge(graph, A, 1, 6);
        addEdge(graph, A, 2, 7);
        addEdge(graph, A, 3, 8);
        addEdge(graph, A, 4, 9);
        addEdge(graph, A, 5, 0);

        addEdge(graph, A, 1, 2);
        addEdge(graph, A, 2, 3);
        addEdge(graph, A, 3, 4);
        addEdge(graph, A, 4, 5);
        addEdge(graph, A, 5, 1);

        addEdge(graph, A, 0, 7);
        addEdge(graph, A, 7, 9);
        addEdge(graph, A, 9, 6);
        addEdge(graph, A, 6, 8);
        addEdge(graph, A, 8, 0);
        return graph;
    }

    private static void addEdge(Graph graph, Node[] nodes, int i, int e) {
        graph.addEdge("E: " + i + " -> " + e, nodes[i], nodes[e]);
    }
}

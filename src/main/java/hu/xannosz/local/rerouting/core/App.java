package hu.xannosz.local.rerouting.core;

import hu.xannosz.local.rerouting.algorithm.AllToOneReRouter;
import hu.xannosz.local.rerouting.core.graph.GraphCreator;
import hu.xannosz.local.rerouting.core.statistic.Statistic;
import hu.xannosz.local.rerouting.core.statistic.Visualiser;
import hu.xannosz.microtools.Sleep;
import org.graphstream.graph.Graph;
import org.graphstream.ui.layout.Layout;
import org.graphstream.ui.layout.springbox.implementations.SpringBox;
import org.graphstream.ui.view.Viewer;
import org.graphstream.ui.view.ViewerListener;

import javax.swing.*;
import java.awt.*;
import java.util.HashSet;
import java.util.Set;

import static javax.swing.WindowConstants.DISPOSE_ON_CLOSE;

public class App implements ViewerListener {
    protected boolean loop = true;

    public static void main(String[] args) {
        App app = new App("", 0, 0, 0, "AllToOne");
    }

    public App(String graphType, int aNodes, int bNodes, int cNodes, String algorithm) {
        Graph graph = GraphCreator.createGraph(graphType, aNodes, bNodes, cNodes);
        Set<Statistic> statistics = new HashSet<>();
        statistics.add(new Visualiser(graph));
        Runner<?> runner;
        switch (algorithm) {
            case "AllToOne":
                runner = new Runner<>(new AllToOneReRouter(), new AllToOneReRouter(), graph, new HashSet<>());
            default:
                runner = new Runner<>(new AllToOneReRouter(), new AllToOneReRouter(), graph, new HashSet<>());
        }

        runner.createMatrices();

        //Set up environment
        JFrame frame = new JFrame();
        frame.setSize(640, 480);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        // a layout algorithm instance plugged to the graph
        Layout layout = new SpringBox(false);
        graph.addSink(layout);
        layout.addAttributeSink(graph);
        Viewer viewer = graph.display();
        viewer.setCloseFramePolicy(Viewer.CloseFramePolicy.EXIT);
        viewer.addDefaultView(false);
        viewer.disableAutoLayout();

        frame.add(viewer.getDefaultView(), BorderLayout.CENTER);

        //Run
        for (; ; ) {
            runner.step();
            Sleep.sleepSeconds(1);
        }
    }

    public void viewClosed(String id) {
        loop = false;
    }

    public void buttonPushed(String id) {
        System.out.println("Button pushed on node " + id);
    }

    public void buttonReleased(String id) {
        System.out.println("Button released on node " + id);
    }
}

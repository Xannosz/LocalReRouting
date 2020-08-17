package hu.xannosz.local.rerouting.core;

import hu.xannosz.local.rerouting.core.interfaces.Algorithm;
import hu.xannosz.local.rerouting.core.interfaces.GraphType;
import hu.xannosz.local.rerouting.core.statistic.Visualiser;
import hu.xannosz.local.rerouting.core.thread.RunnerThread;
import org.graphstream.graph.Graph;
import org.graphstream.ui.layout.Layout;
import org.graphstream.ui.layout.springbox.implementations.SpringBox;
import org.graphstream.ui.view.Viewer;

import javax.swing.*;
import java.awt.*;

import static javax.swing.WindowConstants.DISPOSE_ON_CLOSE;

public class App {
    public App(GraphType<?> graphType, Object settings, Algorithm<?> algorithm) {
        Graph graph = graphType.convertAndCreateGraph(settings);
        Runner runner = new Runner(algorithm.getCreator(), algorithm.getReRouter(), graph,new Visualiser(graph));

        runner.createMatrices();

        System.out.println("Matrices created.");

        //Set up environment
        JFrame frame = new JFrame();
        frame.setSize(640, 480);
        frame.setLocationRelativeTo(null);
        frame.setTitle(graphType.getName());
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
        RunnerThread runnerThread = new RunnerThread(runner);
        runnerThread.start();
    }
}

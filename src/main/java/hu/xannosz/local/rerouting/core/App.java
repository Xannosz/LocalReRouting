package hu.xannosz.local.rerouting.core;

import hu.xannosz.local.rerouting.core.graph.CreateSpecialGraph;
import org.graphstream.graph.Graph;
import org.graphstream.ui.view.Viewer;
import org.graphstream.ui.view.ViewerListener;
import org.graphstream.ui.view.ViewerPipe;

public class App implements ViewerListener {
    protected boolean loop = true;

    public static void main(String args[]) {
        new App();
    }

    public App() {
        Graph graph = CreateSpecialGraph.createCompleteGraph("Complete",10);
        //Graph graph = CreateSpecialGraph.createPairGraph("Pair",10,10);
        //Graph graph = CreateSpecialGraph.createPairGraph("Pair",10,5);
        //Graph graph = CreateSpecialGraph.createPetersenGraph("Petersen");
        Viewer viewer = graph.display();
        viewer.disableAutoLayout();

        viewer.setCloseFramePolicy(Viewer.CloseFramePolicy.EXIT);

        ViewerPipe fromViewer = viewer.newViewerPipe();
        fromViewer.addViewerListener(this);
        fromViewer.addSink(graph);

        while (loop) {
            fromViewer.pump();

            // here your simulation code.
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

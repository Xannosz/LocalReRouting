package hu.xannosz.local.rerouting.graph;

import hu.xannosz.local.rerouting.core.util.GraphHelper;
import hu.xannosz.local.rerouting.core.interfaces.GraphType;
import hu.xannosz.local.rerouting.core.launcher.SettingsPanel;
import lombok.Data;
import org.graphstream.graph.Graph;

import javax.swing.*;
import java.awt.*;

@hu.xannosz.local.rerouting.core.annotation.GraphType
public class Pair  implements GraphType<Pair.Settings> {
    @Override
    public Graph createGraph(Settings settings) {
        Graph result = GraphHelper.createGraph(getName(), settings.aNodes + settings.bNodes);
        GraphHelper.createPairGraph(result, GraphHelper.getNodesFromInterval(result, 0, settings.aNodes), GraphHelper.getNodesFromInterval(result, settings.aNodes, settings.aNodes + settings.bNodes), 0, 0, 3);
        return result;
    }

    @Override
    public String getName() {
        return "Pair";
    }

    @Override
    public Panel getPanel() {
        return new Panel();
    }

    @Data
    public static class Settings {
        private int aNodes;
        private int bNodes;
    }

    public static class Panel extends SettingsPanel<Settings> {

        private final JSpinner spinnerA = new JSpinner();
        private final JSpinner spinnerB = new JSpinner();

        public Panel(){
            JPanel a = new JPanel();
            a.add(new JLabel("A Nodes: "));
            spinnerA.setValue(10);
            a.add(spinnerA);
            a.setLayout(new GridLayout(1, 2));
            JPanel b = new JPanel();
            b.add(new JLabel("B Nodes: "));
            spinnerB.setValue(10);
            b.add(spinnerB);
            b.setLayout(new GridLayout(1, 2));
            add(a);
            add(b);
            setLayout(new GridLayout(2, 1));
        }

        @Override
        public Settings getSettings() {
            Settings result = new Settings();
            result.setANodes((int) spinnerA.getValue());
            result.setBNodes((int) spinnerB.getValue());
            return result;
        }
    }
}

package hu.xannosz.local.rerouting.failuregenerator;

import hu.xannosz.local.rerouting.core.Network;
import hu.xannosz.local.rerouting.core.interfaces.FailureGenerator;
import hu.xannosz.local.rerouting.core.launcher.FailureGeneratorSettingsPanel;
import hu.xannosz.local.rerouting.core.util.Util;
import lombok.Data;
import org.graphstream.graph.Edge;
import org.graphstream.graph.implementations.Graphs;

import javax.swing.*;
import java.awt.*;
import java.util.*;

//@hu.xannosz.local.rerouting.core.annotation.FailureGenerator
public class NonCriticalFailureGenerator implements FailureGenerator<NonCriticalFailureGenerator.Settings> {

    @Override
    public void createFailures(Network graph, NonCriticalFailureGenerator.Settings settings) {
        Network labelled = (Network) Graphs.clone(graph);
        Util.getCriticalWeights(labelled);

        Map<Integer, Set<Edge>> edges = Util.getCriticalEdges(labelled);
        int border = (int) Math.sqrt(edges.size());

        for (int i = 0; i < labelled.getNodeCount(); i--) {
            Set<Edge> edgeSet = edges.get(i);
            if (edgeSet != null) {
                border--;
                for (Edge edge : edgeSet) {
                    if (new Random().nextInt(100) < settings.chance) {
                        graph.removeEdge(edge);
                    }
                }
            }
            if (border < 0) {
                break;
            }
        }
    }

    @Override
    public String getName() {
        return "Non Critical FG";
    }

    @Override
    public FailureGeneratorSettingsPanel<NonCriticalFailureGenerator.Settings> getPanel() {
        return new NonCriticalFailureGenerator.PanelGraphMessage();
    }

    @Override
    public Collection<NonCriticalFailureGenerator.Settings> getSettings() {
        Set<NonCriticalFailureGenerator.Settings> settingses = new HashSet<>();
        for (int i = 10; i < 20; i += 10) {
            NonCriticalFailureGenerator.Settings settings = new NonCriticalFailureGenerator.Settings();
            settings.setChance(i);
            settingses.add(settings);
        }
        return settingses;
    }

    @Data
    public static class Settings {
        private int chance;
    }

    public static class PanelGraphMessage extends FailureGeneratorSettingsPanel<NonCriticalFailureGenerator.Settings> {

        private final JSpinner spinner = new JSpinner();

        public PanelGraphMessage() {
            add(new JLabel("Chance %: "));
            spinner.setValue(10);
            add(spinner);
            setLayout(new GridLayout(1, 2));
        }

        @Override
        public NonCriticalFailureGenerator.Settings getSettings() {
            NonCriticalFailureGenerator.Settings result = new NonCriticalFailureGenerator.Settings();
            result.setChance((int) spinner.getValue());
            return result;
        }
    }
}
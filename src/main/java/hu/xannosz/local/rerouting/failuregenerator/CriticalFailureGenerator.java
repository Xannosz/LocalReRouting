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

@hu.xannosz.local.rerouting.core.annotation.FailureGenerator
public class CriticalFailureGenerator implements FailureGenerator<CriticalFailureGenerator.Settings> {

    @Override
    public void createFailures(Network graph, CriticalFailureGenerator.Settings settings) {
        Network labelled = (Network) Graphs.clone(graph);
        Util.getCriticalWeights(labelled);

        Map<Integer, Set<Edge>> edges = Util.getCriticalEdges(labelled);
        int border = (int) Math.sqrt(edges.size());

        for (int i = labelled.getNodeCount(); i > 0; i--) {
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
        return "Critical FG";
    }

    @Override
    public FailureGeneratorSettingsPanel<CriticalFailureGenerator.Settings> getPanel() {
        return new CriticalFailureGenerator.PanelGraphMessage();
    }

    @Override
    public Collection<CriticalFailureGenerator.Settings> getSettings() {
        Set<CriticalFailureGenerator.Settings> settingses = new HashSet<>();
        for (int i = 10; i < 40; i += 10) {
            CriticalFailureGenerator.Settings settings = new CriticalFailureGenerator.Settings();
            settings.setChance(i);
            settingses.add(settings);
        }
        return settingses;
    }

    @Data
    public static class Settings {
        private int chance;
    }

    public static class PanelGraphMessage extends FailureGeneratorSettingsPanel<CriticalFailureGenerator.Settings> {

        private final JSpinner spinner = new JSpinner();

        public PanelGraphMessage() {
            add(new JLabel("Chance %: "));
            spinner.setValue(10);
            add(spinner);
            setLayout(new GridLayout(1, 2));
        }

        @Override
        public CriticalFailureGenerator.Settings getSettings() {
            CriticalFailureGenerator.Settings result = new CriticalFailureGenerator.Settings();
            result.setChance((int) spinner.getValue());
            return result;
        }
    }
}

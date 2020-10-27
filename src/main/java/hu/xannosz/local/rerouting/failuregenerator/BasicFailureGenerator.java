package hu.xannosz.local.rerouting.failuregenerator;

import hu.xannosz.local.rerouting.core.Network;
import hu.xannosz.local.rerouting.core.interfaces.FailureGenerator;
import hu.xannosz.local.rerouting.core.launcher.FailureGeneratorSettingsPanel;
import lombok.Data;
import org.graphstream.graph.Edge;

import javax.swing.*;
import java.awt.*;
import java.util.HashSet;
import java.util.Random;

@hu.xannosz.local.rerouting.core.annotation.FailureGenerator
public class BasicFailureGenerator implements FailureGenerator<BasicFailureGenerator.Settings> {

    @Override
    public void createFailures(Network graph, Settings settings) {
        for (Edge edge : new HashSet<Edge>(graph.getEdgeSet())) {
            if ((new Random()).nextInt(100) < settings.chance) {
                graph.removeEdge(edge);
            }
        }
    }

    @Override
    public String getName() {
        return "Basic FG";
    }

    @Override
    public FailureGeneratorSettingsPanel<BasicFailureGenerator.Settings> getPanel() {
        return new BasicFailureGenerator.PanelGraphMessage();
    }

    @Data
    public static class Settings {
        private int chance;
    }

    public static class PanelGraphMessage extends FailureGeneratorSettingsPanel<BasicFailureGenerator.Settings> {

        private final JSpinner spinner = new JSpinner();

        public PanelGraphMessage() {
            add(new JLabel("Chance %: "));
            spinner.setValue(10);
            add(spinner);
            setLayout(new GridLayout(1, 2));
        }

        @Override
        public BasicFailureGenerator.Settings getSettings() {
            Settings result = new Settings();
            result.setChance((int) spinner.getValue());
            return result;
        }
    }
}

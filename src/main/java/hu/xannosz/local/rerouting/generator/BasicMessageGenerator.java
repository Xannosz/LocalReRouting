package hu.xannosz.local.rerouting.generator;

import hu.xannosz.local.rerouting.core.algorithm.Message;
import hu.xannosz.local.rerouting.core.interfaces.MessageGenerator;
import hu.xannosz.local.rerouting.core.launcher.GeneratorSettingsPanel;
import org.graphstream.graph.Graph;

import java.util.*;

@hu.xannosz.local.rerouting.core.annotation.MessageGenerator
public class BasicMessageGenerator implements MessageGenerator<BasicMessageGenerator.Settings> {

    @Override
    public Map<Integer, Set<Message>> getMessages(Graph graph, Settings settings) {
        Map<Integer, Set<Message>> messages = new HashMap<>();
        for (int i = 0; i < graph.getNodeCount(); i++) {
            Message message = new Message();
            message.from = i;
            message.to = (new Random()).nextInt(graph.getNodeCount());
            messages.put(i, Collections.singleton(message));
        }
        return messages;
    }

    @Override
    public String getName() {
        return "Basic";
    }

    @Override
    public GeneratorSettingsPanel<Settings> getPanel() {
        return new PanelGraph();
    }

    public static class Settings {

    }

    public static class PanelGraph extends GeneratorSettingsPanel<Settings> {
        @Override
        public Settings getSettings() {
            return new Settings();
        }
    }
}

package hu.xannosz.local.rerouting.messagegenerator;

import hu.xannosz.local.rerouting.core.Network;
import hu.xannosz.local.rerouting.core.algorithm.Message;
import hu.xannosz.local.rerouting.core.interfaces.MessageGenerator;
import hu.xannosz.local.rerouting.core.launcher.MessageGeneratorSettingsPanel;

import java.util.*;

@hu.xannosz.local.rerouting.core.annotation.MessageGenerator
public class AllToAllMessageGenerator implements MessageGenerator<AllToAllMessageGenerator.Settings> {

    @Override
    public Map<Integer, Set<Message>> getMessages(Network graph, Settings settings) {
        Map<Integer, Set<Message>> messages = new HashMap<>();
        for (int from = 0; from < graph.getNodeCount(); from++) {
            Set<Message> messageSet = new HashSet<>();
            for (int i = 0; i < graph.getNodeCount(); i++) {
                if (i != from) {
                    Message message = new Message();
                    message.from = from;
                    message.to = i;
                    messageSet.add(message);
                }
            }
            messages.put(from, messageSet);
        }
        return messages;
    }

    @Override
    public String getName() {
        return "All To All MG";
    }

    @Override
    public MessageGeneratorSettingsPanel<Settings> getPanel() {
        return new PanelGraphMessage();
    }

    @Override
    public Collection<Settings> getSettings() {
        return Collections.singleton(new Settings());
    }

    public static class Settings {

    }

    public static class PanelGraphMessage extends MessageGeneratorSettingsPanel<Settings> {
        @Override
        public Settings getSettings() {
            return new Settings();
        }
    }
}

package hu.xannosz.local.rerouting.messagegenerator;

import hu.xannosz.local.rerouting.core.Network;
import hu.xannosz.local.rerouting.core.algorithm.Message;
import hu.xannosz.local.rerouting.core.interfaces.MessageGenerator;
import hu.xannosz.local.rerouting.core.launcher.MessageGeneratorSettingsPanel;

import java.util.*;

@hu.xannosz.local.rerouting.core.annotation.MessageGenerator
public class OneToAllMessageGenerator implements MessageGenerator<OneToAllMessageGenerator.Settings> {

    @Override
    public Map<Integer, Set<Message>> getMessages(Network graph, Settings settings) {
        Map<Integer, Set<Message>> messages = new HashMap<>();
        int from = (new Random()).nextInt(graph.getNodeCount());
        Set<Message> messageSet = new HashSet<>();
        for (int i = 0; i < graph.getNodeCount(); i++) {
            Message message = new Message();
            message.from = from;
            message.to = i;
            messageSet.add(message);
        }
        messages.put(from, messageSet);
        return messages;
    }

    @Override
    public String getName() {
        return "One To All MG";
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

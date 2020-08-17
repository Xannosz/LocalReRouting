package hu.xannosz.local.rerouting.generator;

import hu.xannosz.local.rerouting.core.algorithm.Message;
import hu.xannosz.local.rerouting.core.interfaces.MessageGenerator;
import org.graphstream.graph.Graph;

import java.util.Map;
import java.util.Set;

@hu.xannosz.local.rerouting.core.annotation.MessageGenerator
public class BasicMessageGenerator implements MessageGenerator {

    @Override
    public String getName() {
        return "Basic";
    }

    @Override
    public Map<Integer, Set<Message>> getMessages(Graph graph) {
        return null;
    }
}

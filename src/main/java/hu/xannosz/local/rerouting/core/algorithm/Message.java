package hu.xannosz.local.rerouting.core.algorithm;

import java.util.Set;
import java.util.UUID;

public class Message {
    public int from;
    public int to;
    public Set<Integer> visitedNodes;
    public final String id = UUID.randomUUID().toString();
}

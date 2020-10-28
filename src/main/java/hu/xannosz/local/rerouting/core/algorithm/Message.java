package hu.xannosz.local.rerouting.core.algorithm;

import hu.xannosz.microtools.pack.Douplet;

import java.util.HashSet;
import java.util.Set;

public class Message {
    public boolean brokenRooting = false;
    public int from;
    public int to;
    public Set<Douplet<Integer, Integer>> visitedNodesMap = new HashSet<>();
}

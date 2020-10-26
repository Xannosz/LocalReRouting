package hu.xannosz.local.rerouting.core.algorithm;

import hu.xannosz.microtools.pack.Douplet;

import java.util.*;

public class Message {
    public boolean brokenRooting =false;
    public int from;
    public int to;
    public Set<Integer> visitedNodes = new HashSet<>();
    public Set<Douplet<Integer,Integer>> visitedNodesMap = new HashSet<>();
    public final String id = UUID.randomUUID().toString();
}

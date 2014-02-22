package de.rudi.algorithm.graph;

public class Edge {
    public final NewVertex target;
    public final double weight;
    public Edge(NewVertex argTarget, double argWeight)
    { target = argTarget; weight = argWeight; }
}

package de.rudi.algorithm.graph;

public class NewVertex implements Comparable<NewVertex>
{
    public final String name;
    public Edge[] adjacencies;
    public double minDistance = Double.POSITIVE_INFINITY;
    public NewVertex previous;
    public NewVertex(String argName) { name = argName; }
    public String toString() { return name; }
    public int compareTo(NewVertex other)
    {
        return Double.compare(minDistance, other.minDistance);
    }
}
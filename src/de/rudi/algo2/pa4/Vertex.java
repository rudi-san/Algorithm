package de.rudi.algo2.pa4;

public class Vertex implements Comparable<Vertex>
{
    public final String name;
    public DEdge[] adjacencies;
    public int minDistance = Integer.MAX_VALUE;
    public Vertex previous;
    public Vertex(String argName) { name = argName; }
    public String toString() { return name; }
    public int compareTo(Vertex other)
    {
        return Double.compare(minDistance, other.minDistance);
    }

}
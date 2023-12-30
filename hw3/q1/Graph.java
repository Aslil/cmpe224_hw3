
//-----------------------------------------------------
// Title: Graph class
// Author: Aslı Algın
// Section: 02
// Assignment: 3
// Description: The Graph class represents a method to add edges between vertices, where each edge is assigned a weight, 
//and it uses an adjacency list structure to represent an undirected graph.
//-----------------------------------------------------
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Graph {
	Map<String, List<Edge>> adjacencyList = new HashMap<>();

	public void addEdge(String source, String destination, double weight) {
		// --------------------------------------------------------
		// Summary: This method adds an undirected edge to a graph, creating a
		// connection between vertices specified by the source and destination
		// parameters.
		// Precondition: source and destination are String, weight is double.
		// Postcondition: The method updates the graph's adjacency list, adding the edge
		// to the source vertex's list.
		// --------------------------------------------------------
		Edge edge = new Edge(source, destination, weight);

		if (!adjacencyList.containsKey(source)) {
			adjacencyList.put(source, new ArrayList<>());
		}
		adjacencyList.get(source).add(edge);

		if (!source.equals(destination)) {
			if (!adjacencyList.containsKey(destination)) {
				adjacencyList.put(destination, new ArrayList<>());
			}
			adjacencyList.get(destination).add(edge);
		}
	}

}

class Edge implements Comparable<Edge> {
	String source, destination;
	double weight;

	public Edge(String source, String destination, double weight) { // constructor
		this.source = source;
		this.destination = destination;
		this.weight = weight;
	}

	@Override
	public int compareTo(Edge other) {
		return Double.compare(this.weight, other.weight);
	}
}

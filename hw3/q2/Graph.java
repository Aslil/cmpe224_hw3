
//-----------------------------------------------------
// Title: Graph class
// Author: Aslı Algın
// Section: 02
// Assignment: 3
// Description: The Graph class represents a graph data structure with weighted edges.
//-----------------------------------------------------
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Graph {
	private Map<String, List<Edge>> adjacencyList;

	public Graph() {
		this.adjacencyList = new HashMap<>();
	}

	public void addEdge(String source, String destination, int weight) {
		// --------------------------------------------------------
		// Summary: This method adds a weighted edge between source and destination
		// nodes in a graph, updating the adjacency list to include the new edge in both
		// directions.
		// Precondition: source and destination are String, weight is integer.
		// --------------------------------------------------------
		Edge edge = new Edge(destination, weight);
		if (!adjacencyList.containsKey(source)) {
			List<Edge> list = new ArrayList<>();
			list.add(edge);
			adjacencyList.put(source, list);
		} else {
			adjacencyList.get(source).add(edge);
		}

		edge = new Edge(source, weight);
		if (!adjacencyList.containsKey(destination)) {
			List<Edge> list = new ArrayList<>();
			list.add(edge);
			adjacencyList.put(destination, list);
		} else {
			adjacencyList.get(destination).add(edge);
		}
	}

	public List<Edge> getNeighbors(String node) {
		return adjacencyList.getOrDefault(node, Collections.emptyList());
	}
}

class Edge {
	String destination;
	int weight;

	public Edge(String destination, int weight) {
		this.destination = destination;
		this.weight = weight;
	}
}

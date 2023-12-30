
//-----------------------------------------------------
// Title: ElectricityDistribution class
// Author: Aslı Algın
// Section: 02
// Assignment: 3
// Description: The ElectricityDistribution class reads city information from a file, constructs a graph representing pairwise distances between cities, 
//finds the minimum spanning tree using Prim's algorithm, and prints the paths of the tree in ascending order of their weights.
//-----------------------------------------------------
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class ElectricityDistribution {

	public static void main(String[] args) {

		try (Scanner scanner = new Scanner(System.in)) {
			System.out.print("");
			String fileName = scanner.nextLine();

			List<City> cities = readCitiesFromFile(fileName);
			Graph graph = buildGraph(cities);
			List<Edge> minimumSpanningTree = findMinimumSpanningTree(graph);

			printPaths(minimumSpanningTree);
		}
	}

	private static List<City> readCitiesFromFile(String fileName) {
		// --------------------------------------------------------
		// Summary: This method reads city information from a file specified by the
		// fileName parameter.
		// Precondition: fileName is String.
		// Postcondition: The method parses this information, creates City objects, and
		// returns a list containing these City objects.
		// --------------------------------------------------------
		List<City> cities = new ArrayList<>();
		try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
			String line;
			while ((line = reader.readLine()) != null) {
				String[] parts = line.split(",");
				String name = parts[0];
				int x = Integer.parseInt(parts[1]);
				int y = Integer.parseInt(parts[2]);
				cities.add(new City(name, x, y));
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return cities;
	}

	private static Graph buildGraph(List<City> cities) {
		// --------------------------------------------------------
		// Summary: This method builds a graph using the given list of cities by
		// iterating through all pairs of cities.
		// Precondition: cities is List which takes City.
		// Postcondition: The resulting graph represents the pairwise distances between
		// cities in the input list.
		// --------------------------------------------------------
		Graph graph = new Graph();
		for (int i = 0; i < cities.size(); i++) {
			for (int j = i + 1; j < cities.size(); j++) {
				City city1 = cities.get(i);
				City city2 = cities.get(j);
				double distance = calculateDistance(city1, city2);
				graph.addEdge(city1.name, city2.name, distance);
			}
		}
		return graph;
	}

	private static double calculateDistance(City city1, City city2) { // Calculating distance between 2 points by using
																		// Euclidian distance.
		return Math.sqrt(Math.pow(city1.x - city2.x, 2) + Math.pow(city1.y - city2.y, 2));
	}

	private static List<Edge> findMinimumSpanningTree(Graph graph) {
		// --------------------------------------------------------
		// Summary: This method finds the minimum spanning tree of a given graph using
		// Prim's algorithm, starting with the alphabetically first city as the initial
		// vertex.
		// Precondition: graph is Graph.
		// Postcondition: The algorithm continues until all cities are visited, avoiding
		// the creation of cycles.
		// --------------------------------------------------------
		List<Edge> minimumSpanningTree = new ArrayList<>();
		Set<String> visited = new HashSet<>();
		PriorityQueue<Edge> minHeap = new PriorityQueue<>(Comparator.comparingDouble(edge -> edge.weight));

		// Start with the alphabetically first city
		String startCity = graph.adjacencyList.keySet().stream().min(String::compareTo).orElse(null);
		visited.add(startCity);
		minHeap.addAll(graph.adjacencyList.get(startCity));

		while (!minHeap.isEmpty()) {
			Edge edge = minHeap.poll();

			// Check if adding this edge would create a cycle
			if (!(visited.contains(edge.source) && visited.contains(edge.destination))) {
				visited.add(edge.source);
				visited.add(edge.destination);
				minimumSpanningTree.add(edge);

				// Add all edges connected to the newly visited city
				minHeap.addAll(graph.adjacencyList.get(edge.destination));
			}
		}

		return minimumSpanningTree;
	}

	private static void printPaths(List<Edge> paths) { // This method prints the paths represented by a list of edges in
														// ascending order of their weights.
		System.out.println("Paths are:");

		paths.sort(Comparator.comparingDouble(edge -> edge.weight));

		for (Edge edge : paths) {
			System.out.printf("%s-%s: %.1f\n", edge.source, edge.destination, edge.weight);
		}
	}

}

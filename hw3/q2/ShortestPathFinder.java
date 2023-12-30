
//-----------------------------------------------------
// Title: ShortestPathFinder class
// Author: Aslı Algın
// Section: 02
// Assignment: 3
// Description: The ShortestPathFinder class is designed to find the shortest route between two cities 
// in a graph-based representation of a road network. It reads input data from a file and
// constructs an undirected graph, and then calculates the shortest route.
//-----------------------------------------------------
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class ShortestPathFinder {
	private static Graph graph;

	public static void main(String[] args) {
		try (Scanner scanner = new Scanner(System.in)) {
			System.out.print(""); // the name of the input file
			String inputFile = scanner.nextLine();

			readGraphFromFile(inputFile);

			System.out.print(""); // the source city
			String sourceCity = scanner.nextLine();

			System.out.print(""); // the destination city
			String destinationCity = scanner.nextLine();

			System.out.print(""); // the number of cities you want to visit
			int numberOfCitiesToVisit = scanner.nextInt();
			scanner.nextLine();

			List<String> citiesToVisit = new ArrayList<>();
			for (int i = 0; i < numberOfCitiesToVisit; i++) {
				System.out.print(""); // city to visit
				citiesToVisit.add(scanner.nextLine());
			}

			findShortestRoute(sourceCity, destinationCity, citiesToVisit);
		}
	}

	private static void readGraphFromFile(String filename) {
		// --------------------------------------------------------
		// Summary: This method initializes a graph, reads data from a specified file,
		// and creates an undirected graph structure.
		// Precondition: filename is String.
		// Postcondition: Creates Undirected graph structure by adding edges with
		// corresponding weights based on the information provided in each line of the
		// file.
		// --------------------------------------------------------
		graph = new Graph();
		try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
			String line;
			while ((line = br.readLine()) != null) {
				String[] parts = line.split(",");
				String source = parts[0];
				String destination = parts[1];
				int weight = Integer.parseInt(parts[2]);
				graph.addEdge(source, destination, weight); // creating undirected graph structure
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private static void findShortestRoute(String source, String destination, List<String> citiesToVisit) {
		// --------------------------------------------------------
		// Summary: This method finds the shortest route between a given source and
		// destination, visiting specified cities along the way, based on a weighted
		// graph, and outputs the route along with its total length.
		// Precondition: source and destination are String, citiesToVisit is List which
		// gives String.
		// Postcondition: It prints routes if it finds, if not it prints no route found.
		// --------------------------------------------------------
		List<String> route = new ArrayList<>();
		int totalLength = 0;

		route.add(source);

		for (int i = 0; i < citiesToVisit.size(); i++) {
			String city = citiesToVisit.get(i);
			List<Edge> neighbors = graph.getNeighbors(route.get(route.size() - 1));
			Optional<Edge> edgeToVisit = null;

			for (Edge e : neighbors) {
				if (e.destination.equals(city)) {
					if (edgeToVisit == null || e.weight < edgeToVisit.get().weight) {
						edgeToVisit = Optional.of(e);
					}
				}
			}

			if (edgeToVisit.isPresent()) {
				totalLength += edgeToVisit.get().weight;
				route.add(edgeToVisit.get().destination);
			} else {
				System.out.println("No route found.");
				return;
			}
		}

		List<Edge> lastLeg = graph.getNeighbors(route.get(route.size() - 1));
		Optional<Edge> edgeToDestination = null;

		for (Edge e : lastLeg) {
			if (e.destination.equals(destination)) {
				if (edgeToDestination == null || e.weight < edgeToDestination.get().weight) {
					edgeToDestination = Optional.of(e);
				}
			}
		}

		if (edgeToDestination.isPresent()) {
			totalLength += edgeToDestination.get().weight;
			route.add(edgeToDestination.get().destination);
		} else {
			System.out.println("No route found.");
			return;
		}

		System.out.println("Routes are:");
		System.out.println(String.join("-", route));
		System.out.println("Length of route is: " + totalLength);
	}

}

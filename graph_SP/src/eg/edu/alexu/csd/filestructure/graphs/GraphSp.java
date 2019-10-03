package eg.edu.alexu.csd.filestructure.graphs;

import java.awt.Point;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.PriorityQueue;

import javax.management.RuntimeErrorException;

public class GraphSp implements IGraph {// a graph having the implementation of initializing the graph and finding the
										// shortest path algorithms

	private ArrayList<ArrayList<Edge>> adjacencyList = new ArrayList<ArrayList<Edge>>();
	private ArrayList<Integer> vertices = new ArrayList<Integer>();
	private int numberOfEdges = 0;
	private int numberOfVertices = 0;
	boolean validDijkstra = true;// set to false if a negative weight is detected.
	private static final int infinity = Integer.MAX_VALUE / 2;// the infinity value that is to be initialized for the
																// distances implementations.
	private static final String space = " ";
	private ArrayList<Integer> verticesOrder = new ArrayList<Integer>();// to keep the order of processing in Dijkstra
																		// algorithm.

	@Override
	public void readGraph(File file) {// read the file line by line and while doing so initialize the values and the
										// array lists it is O(E).

		String currLine = "";
		String separated[];
		try {
			BufferedReader reader = new BufferedReader(new FileReader(file));
			currLine = reader.readLine();// get the first line having number of vertices and number of edges.
			separated = currLine.split(space);
			numberOfVertices = Integer.parseInt(separated[0]);
			numberOfEdges = Integer.parseInt(separated[1]);
			// initialize array lists:
			for (int i = 0; i < numberOfVertices; i++) {
				adjacencyList.add(new ArrayList<Edge>());
				vertices.add(i);
			}
			// add the edges by reading each line for each edge:
			for (int i = 0; i < numberOfEdges; i++) {
				currLine = reader.readLine();
				separated = currLine.split(space);
				Edge temp = new Edge(Integer.parseInt(separated[0]), Integer.parseInt(separated[1]),
						Integer.parseInt(separated[2]));
				if (temp.getWeight() < 0)// negative weight
					validDijkstra = false;
				adjacencyList.get(temp.getFrom()).add(temp);
			}
			if (reader.readLine() != null) {// extra file lines more than the given number of edges
				reader.close();
				throw new RuntimeErrorException(null);
			}
			reader.close();

		} // file wasn't found or a problem occurred while reading the file.
		catch (FileNotFoundException e) {
			throw new RuntimeErrorException(null);
		} catch (IOException e) {
			throw new RuntimeErrorException(null);
		}

	}

	@Override
	public int size() {
		return numberOfEdges;
	}

	@Override
	public ArrayList<Integer> getVertices() {
		return vertices;
	}

	@Override
	public ArrayList<Integer> getNeighbors(int v) {
		ArrayList<Integer> result = new ArrayList<Integer>();
		for (int i = 0; i < adjacencyList.get(v).size(); i++)
			result.add(adjacencyList.get(v).get(i).getTo());
		return result;
	}

	class nodeComparator implements Comparator<Point> {// comparator used to implement the priority queue used in the
														// Dijkstra algorithm

		// Overriding compare()method of Comparator
		// for descending order of y which is the distance of each node.
		public int compare(Point p1, Point p2) {
			if (p1.y > p2.y)
				return 1;
			else if (p1.y < p2.y)
				return -1;
			return 0;
		}
	}

	@Override
	public void runDijkstra(int src, int[] distances) {// O(VlgV + ElgV) performs the algorithm and also fills the array
														// list having the order of the processed vertices during the
														// Dijkstra algorithm
		// initialization:
		verticesOrder.clear();
		boolean[] inQueue = new boolean[numberOfVertices];
		PriorityQueue<Point> pq = new PriorityQueue<Point>(numberOfVertices, new nodeComparator());
		ArrayList<Point> nodes = new ArrayList<Point>();// used to keep the pointers to the nodes added to the priority
														// queue.
		for (int i = 0; i < numberOfVertices; i++) {
			if (i != src) {
				distances[i] = infinity;
				inQueue[i] = true;
				Point temp = new Point(i, distances[i]);
				nodes.add(temp);
				pq.add(temp);
			} else // source point
			{
				distances[i] = 0;
				inQueue[i] = true;
				Point temp = new Point(i, distances[i]);
				nodes.add(temp);
				pq.add(temp);
			}
		}

		// The algorithm:
		while (!pq.isEmpty()) {
			int v = pq.remove().x;
			verticesOrder.add(v);
			inQueue[v] = false;
			ArrayList<Edge> neighbours = adjacencyList.get(v);
			for (int i = 0; i < neighbours.size(); i++) {// loop on neighbors
				int u = neighbours.get(i).getTo();
				int weight = neighbours.get(i).getWeight();
				if (inQueue[u] && distances[v] + weight < distances[u]) {
					distances[u] = distances[v] + weight;
					// decrease minimum is implemented by consecutive remove and add operations.
					pq.remove(nodes.get(u));
					nodes.get(u).y = distances[u];
					pq.add(nodes.get(u));
				}
			}

		}

	}

	@Override
	public ArrayList<Integer> getDijkstraProcessedOrder() {
		return verticesOrder;
	}

	@Override
	public boolean runBellmanFord(int src, int[] distances) { // O(VE)
		// Initialization
		// initially all distances are set to infinity except that of the source node:
		for (int i = 0; i < distances.length; i++) {
			distances[i] = infinity;
		}
		distances[src] = 0;
		// algorithm of arbitrary edges relaxation:
		boolean finished = false;
		for (int i = 0; i < numberOfVertices - 1 && !finished; i++) {// runs V-1 as proven by its correctness if more
																		// relaxes are
			// needed then a negative cycle exists.
			finished = true;
			for (int j = 0; j < adjacencyList.size(); j++) {
				ArrayList<Edge> currEdges = adjacencyList.get(j);
				for (int k = 0; k < currEdges.size(); k++) {
					int v = currEdges.get(k).getFrom();
					int u = currEdges.get(k).getTo();
					if (distances[v] + currEdges.get(k).getWeight() < distances[u]) {
						finished = false;
						distances[u] = distances[v] + currEdges.get(k).getWeight();

					}
				}
			}
		}
		// checking for negative cycles:
		for (int j = 0; j < adjacencyList.size(); j++) {
			ArrayList<Edge> currEdges = adjacencyList.get(j);
			for (int k = 0; k < currEdges.size(); k++) {
				int v = currEdges.get(k).getFrom();
				int u = currEdges.get(k).getTo();
				if (distances[v] + currEdges.get(k).getWeight() < distances[u])// negative cycle is detected.
					return false;
			}
		}
		return true;
	}

}

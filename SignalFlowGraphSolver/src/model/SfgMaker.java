package model;

import java.util.ArrayList;

public class SfgMaker {

	private ArrayList<ArrayList<Edge>> EdgesWithSameFrom;// we use an array of array lists of edges having the same
															// starting
	// point to study them adjacently.
	private int NumberOfNodes;
	private int NumberOfEdges;
	private int Input;
	private int Output;

	public SfgMaker(int Input, int Output, int NumberOfNodes) {
		this.Input = Input;
		this.Output = Output;
		this.NumberOfNodes = NumberOfNodes;
		this.NumberOfEdges = 0;
		this.EdgesWithSameFrom = new ArrayList<ArrayList<Edge>>();
	}

	public ArrayList<ArrayList<Edge>> getEdgesWithSameFrom() {
		return EdgesWithSameFrom;
	}

	public void setEdgesWithSameFrom(ArrayList<ArrayList<Edge>> edgesWithSameFrom) {
		EdgesWithSameFrom = edgesWithSameFrom;
	}

	public int getNumberOfNodes() {
		return NumberOfNodes;
	}

	public void setNumberOfNodes(int numberOfNodes) {
		NumberOfNodes = numberOfNodes;
	}

	public int getNumberOfEdges() {
		return NumberOfEdges;
	}

	public void setNumberOfEdges(int numberOfEdges) {
		NumberOfEdges = numberOfEdges;
	}

	public void addEdge(Edge e) {
		int i;
		for (i = 0; i < EdgesWithSameFrom.size(); i++) {
			if (EdgesWithSameFrom.get(i).get(0).getFrom() == e.getFrom()) {
				EdgesWithSameFrom.get(i).add(e);
				break;
			}
		}
		if (i == EdgesWithSameFrom.size()) {
			EdgesWithSameFrom.add(new ArrayList<Edge>());
			EdgesWithSameFrom.get(EdgesWithSameFrom.size() - 1).add(e);
		}
		NumberOfEdges++;

	}

	public ArrayList<Edge> getEdgesWithFrom(int from) {
		return EdgesWithSameFrom.get(from);
	}

	public int getInput() {
		return Input;
	}

	public void setInput(int input) {
		Input = input;
	}

	public int getOutput() {
		return Output;
	}

	public void setOutput(int output) {
		Output = output;
	}

}

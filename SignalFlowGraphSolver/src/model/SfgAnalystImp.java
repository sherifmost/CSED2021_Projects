package model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.Stack;

public class SfgAnalystImp implements SfgAnalyst {

	private SfgMaker sfg;
	private boolean[] visited;
	private ArrayList<ArrayList<Edge>> forwardPaths;
	private ArrayList<ArrayList<Edge>> cycles;
	private ArrayList<ArrayList<Integer>> testCycles;

	public SfgAnalystImp(SfgMaker sfg) {
		this.sfg = sfg;
		visited = new boolean[sfg.getNumberOfNodes()];
	}

	public SfgMaker getSfg() {
		return sfg;
	}

	public void setSfg(SfgMaker sfg) {
		this.sfg = sfg;
	}

	@Override
	public ArrayList<ArrayList<Edge>> lookForForwardPaths(int Input, int Output) {
		visited = new boolean[sfg.getNumberOfNodes()];
		setForwardPaths(new ArrayList<ArrayList<Edge>>());
		Stack<Integer> sequence = new Stack<Integer>(); // used to keep the sequence of the forward path during the dfs.
		sequence.add(Input);// add input node
		visited[Input] = true;
		forwardPathsDfs(Input, Output, sequence);

		return forwardPaths;
	}

	private void forwardPathsDfs(int from, int output, Stack<Integer> sequence) {// an algorithm using dfs and
																					// backtracking to get all possible
																					// forward paths

		if (from == output) { // we reached the end of this forward path.
			addPath(sequence);
			return;// a stopping for the recursion.
		}
		ArrayList<Edge> withSameFrom = sfg.getEdgesWithFrom(from);
		int numberOfEdgesWithThisFrom = withSameFrom.size();
		for (int j = 0; j < numberOfEdgesWithThisFrom; j++) {
			int to = withSameFrom.get(j).getTo();
			if (!visited[to]) {// no loop is visited twice in the forward path.
				sequence.add(to);
				visited[to] = true;
				forwardPathsDfs(to, output, sequence);
				sequence.pop();
				visited[to] = false;// this two lines are for the backtracking part of the algorithm to get all the
									// possible paths between these two nodes

			}

		}

	}

	private void addPath(Stack<Integer> sequence) {// sequence is from then to ending with to.
		ArrayList<Edge> path = new ArrayList<Edge>();
		Iterator<Integer> iterator = sequence.iterator();
		int from = iterator.next();
		int to = from;
		while (iterator.hasNext()) {
			from = to;
			to = iterator.next();
			path.add(getEdge(from, to));
		}
		forwardPaths.add(path);
	}

	private Edge getEdge(int from, int to) {
		ArrayList<Edge> temp = sfg.getEdgesWithFrom(from);
		for (int j = 0; j < temp.size(); j++) {
			if (temp.get(j).getTo() == to)
				return temp.get(j);
		}

		return null;
	}

	public ArrayList<ArrayList<Edge>> getForwardPaths() {
		if (forwardPaths == null)
			return lookForForwardPaths(sfg.getInput(), sfg.getOutput());
		return forwardPaths;
	}

	public void setForwardPaths(ArrayList<ArrayList<Edge>> forwardPaths) {
		this.forwardPaths = forwardPaths;
	}

	// ----------------------------------------------------------------------------------------------------------------------------------------------------------------
	@Override
	public ArrayList<ArrayList<Edge>> lookForCycles() {
		setCycles(new ArrayList<ArrayList<Edge>>());
		testCycles = new ArrayList<ArrayList<Integer>>();
		Stack<Integer> sequence = new Stack<Integer>();
		visited = new boolean[sfg.getNumberOfNodes()];
		for (int i = 0; i < sfg.getNumberOfNodes(); i++) {
			if (!visited[i] && i != sfg.getOutput())
				cycleDfs(i, sequence);
		}

		return cycles;
	}

	private void cycleDfs(int start, Stack<Integer> sequence) {
		ArrayList<Edge> withSameFrom = sfg.getEdgesWithFrom(start);
		int numberOfEdgesWithThisFrom = withSameFrom.size();

		visited[start] = true;// so we don't re add the cycles again.
		sequence.push(start);
		for (int i = 0; i < numberOfEdgesWithThisFrom; i++) {// get the cycle and backtracking to get all possible
																// cycles containing the start
			int to = withSameFrom.get(i).getTo();
			if (to != sfg.getOutput() && visited[to]) {// a cycle was reached where a node is traversed twice.
				addCycle(sequence);

			} else if (to != sfg.getOutput()) {
				cycleDfs(to, sequence);
			}
		}
		sequence.pop();
		visited[start] = false;// the backtracking part.

	}

	private void addCycle(Stack<Integer> sequence) {// sequence is from then to ending with to and we need to test if
													// this cycle is already present.
		ArrayList<Edge> path = new ArrayList<Edge>();
		ArrayList<Integer> test = new ArrayList<Integer>();
		Iterator<Integer> iterator = sequence.iterator();
		int start = iterator.next();
		int from = start;
		int to = from;
		while (iterator.hasNext()) {
			to = iterator.next();
			test.add(from);
			path.add(getEdge(from, to));
			from = to;

		}
		test.add(from);
		path.add(getEdge(to, start));
		Collections.sort(test);
		if (!hasTheCyle(test) && !path.contains(null)) {
			cycles.add(path);
			testCycles.add(test);
		}
	}

	private boolean hasTheCyle(ArrayList<Integer> test) {
		for (int i = 0; i < testCycles.size(); i++) {
			if (testCycles.get(i).size() == test.size()) {
				int j = 0;
				for (j = 0; j < test.size(); j++) {
					if (test.get(j) != testCycles.get(i).get(j))
						break;
				}
				if (j == test.size() && j != 0)
					return true;
			}
		}
		return false;
	}

	public ArrayList<ArrayList<Edge>> getCycles() {
		if (cycles == null)
			return lookForCycles();
		return cycles;
	}

	public void setCycles(ArrayList<ArrayList<Edge>> cycles) {
		this.cycles = cycles;
	}

	// ----------------------------------------------------------------------------------------------------------------------------------------------------------------
	@Override
	public boolean nonTouching(ArrayList<Edge> path1, ArrayList<Edge> path2) {

		ArrayList<Integer> nodes1 = getNodes(path1);
		ArrayList<Integer> nodes2 = getNodes(path2);
		for (int i = 0; i < nodes1.size(); i++) {
			if (nodes2.contains(nodes1.get(i)))
				return false;
		}
		return true;
	}

	private ArrayList<Integer> getNodes(ArrayList<Edge> path) {
		ArrayList<Integer> nodes = new ArrayList<Integer>();
		for (int i = 0; i < path.size(); i++)
			nodes.add(path.get(i).getFrom());
		nodes.add(path.get(path.size() - 1).getTo());
		return nodes;
	}

}

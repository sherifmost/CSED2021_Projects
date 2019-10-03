package test;

import model.Edge;
import model.SfgAnalystImp;
import model.SfgCombination;
import model.SfgMaker;
import model.SfgSolverImp;

public class TestOverallGainFunctions {

	public static void main(String[] args) {
		SfgMaker graph = new SfgMaker(0, 6, 7);
		graph.addEdge(new Edge(0, 1, 1));
		graph.addEdge(new Edge(1, 2, 5));
		graph.addEdge(new Edge(2, 3, 10));
		graph.addEdge(new Edge(3, 4, 2));
		graph.addEdge(new Edge(4, 6, 1));
		graph.addEdge(new Edge(5, 4, 2));
		graph.addEdge(new Edge(4, 3, -2));
		graph.addEdge(new Edge(3, 2, -1));
		graph.addEdge(new Edge(4, 1, -1));
		graph.addEdge(new Edge(1, 5, 10));
		graph.addEdge(new Edge(5, 5, -1));
		SfgAnalystImp imp = new SfgAnalystImp(graph);
		SfgCombination combination = new SfgCombination(imp);
		SfgSolverImp solverImp = new SfgSolverImp(imp, combination);
		System.out.println(solverImp.getOverallGain());
	}

}

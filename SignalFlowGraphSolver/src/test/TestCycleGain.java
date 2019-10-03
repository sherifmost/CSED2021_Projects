package test;

import java.util.ArrayList;

import model.Edge;
import model.SfgAnalystImp;
import model.SfgMaker;
import model.SfgSolverImp;

public class TestCycleGain {

	public static void main(String[] args) {
		SfgMaker graph = new SfgMaker(0, 4, 5);
		graph.addEdge(new Edge(0, 1, 2));
		graph.addEdge(new Edge(1, 1, 2));
		graph.addEdge(new Edge(1, 2, 3));
		graph.addEdge(new Edge(2, 1, 3));
		graph.addEdge(new Edge(2, 3, 5));
		graph.addEdge(new Edge(3, 1, 5));
		graph.addEdge(new Edge(1, 3, 10.5));
		graph.addEdge(new Edge(3, 0, 10.5));
		graph.addEdge(new Edge(0, 2, 5));
		graph.addEdge(new Edge(0, 3, 7));
		graph.addEdge(new Edge(3, 4, 5));

		SfgAnalystImp analystImp = new SfgAnalystImp(graph);
		SfgSolverImp solverImp = new SfgSolverImp(analystImp,null);

		ArrayList<ArrayList<Edge>> cycles = analystImp.lookForCycles();
		double[] gains = solverImp.getCyclesGains();
		for (int i = 0; i < cycles.size(); i++) {
			for (int j = 0; j < cycles.get(i).size(); j++)
				System.out.print(cycles.get(i).get(j).getFrom() + " ");
			System.out.println(cycles.get(i).get(cycles.get(i).size() - 1).getTo() + " " + gains[i]);
		}

	}

}

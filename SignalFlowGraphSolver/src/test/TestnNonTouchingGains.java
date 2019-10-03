package test;

import java.util.ArrayList;

import model.Edge;
import model.SfgAnalystImp;
import model.SfgCombination;
import model.SfgMaker;
import model.SfgSolverImp;

public class TestnNonTouchingGains {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		SfgMaker graph = new SfgMaker(0, 6, 7);
		for (int i = 0; i < 5; i++)
			graph.addEdge(new Edge(i, i + 1, i + 2));
		graph.addEdge(new Edge(1, 0, 10));
		graph.addEdge(new Edge(2, 1, 9));
		graph.addEdge(new Edge(3, 2, 8));
		graph.addEdge(new Edge(3, 0, 15.5));
		graph.addEdge(new Edge(5, 4, 7));
		SfgAnalystImp analystImp = new SfgAnalystImp(graph);

		SfgCombination combination = new SfgCombination(analystImp);
		ArrayList<ArrayList<ArrayList<ArrayList<Edge>>>> nNonTouch = combination.getnNonTouchingCycles();
		SfgSolverImp solverImp = new SfgSolverImp(analystImp, combination);
		double[] gains = solverImp.getnNonTouchingGains(nNonTouch);

		for (int i = 0; i < nNonTouch.size(); i++) {
			System.out.println((i + 1) + " : " + gains[i] + ": ");
			for (int j = 0; j < nNonTouch.get(i).size(); j++) {
				for (int k = 0; k < nNonTouch.get(i).get(j).size(); k++) {
					int l = 0;
					for (l = 0; l < nNonTouch.get(i).get(j).get(k).size(); l++) {
						System.out.print(nNonTouch.get(i).get(j).get(k).get(l).getFrom());

					}
					System.out.print(nNonTouch.get(i).get(j).get(k).get(l - 1).getTo());
					if (k < nNonTouch.get(i).get(j).size() - 1)
						System.out.print("--");
				}
				System.out.println();
			}
		}
	}

}
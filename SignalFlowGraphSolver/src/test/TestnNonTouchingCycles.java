package test;

import java.util.ArrayList;

import model.Edge;
import model.SfgAnalystImp;
import model.SfgCombination;
import model.SfgMaker;
import model.SfgSolverImp;

public class TestnNonTouchingCycles {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		SfgMaker graph = new SfgMaker(0, 7, 8);
		for(int i = 0;i < 7;i++) {
			graph.addEdge(new Edge(0, i, i+1));
			
		}
		for(int i = 1;i<7;i++) {
			graph.addEdge(new Edge(i, i-1, i+1));
		}
		for(int i = 1;i<6;i++)
			graph.addEdge(new Edge(i, i+1, i+1));
		graph.addEdge(new Edge(3, 1, 0));
		graph.addEdge(new Edge(5, 2, 0));
		graph.addEdge(new Edge(6, 0, 0));
		graph.addEdge(new Edge(4, 6, 0));
		graph.addEdge(new Edge(6, 7, 0));
		graph.addEdge(new Edge(5, 7, 0));
		
		SfgAnalystImp analystImp = new SfgAnalystImp(graph);
		SfgSolverImp solverImp = new SfgSolverImp(analystImp,null);

		ArrayList<ArrayList<Edge>> cycles = analystImp.lookForCycles();
		SfgCombination combination = new SfgCombination(analystImp);
		ArrayList<ArrayList<ArrayList<ArrayList<Edge>>>> nNonTouch = combination.getnNonTouchingCycles();
		for (int i = 0; i < nNonTouch.size(); i++) {
			System.out.println((i + 1) + " :");
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

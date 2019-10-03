package test;

import model.Edge;
import model.SfgAnalystImp;
import model.SfgMaker;
import model.SfgSolverImp;

public class TestForwardPathGain {

	public static void main(String[] args) {
		
		SfgMaker graph = new SfgMaker(0, 3, 5);
		graph.addEdge(new Edge(0, 1, 2));
		graph.addEdge(new Edge(1, 1, 2));
        graph.addEdge(new Edge(1, 2, 3));
        graph.addEdge(new Edge(2, 3, 5));
        graph.addEdge(new Edge(1, 3, 10.5));
        graph.addEdge(new Edge(0, 2, 5));
        graph.addEdge(new Edge(0, 3, 7));
        SfgSolverImp solver = new SfgSolverImp(new SfgAnalystImp(graph),null);
        double[] result = solver.getForwardPathsGains();
        for(int i = 0;i < result.length;i++)
        	System.out.println(result[i]);
	}
	

}

package model;

import java.util.ArrayList;

public interface SfgAnalyst {

	public ArrayList<ArrayList<Edge>> lookForForwardPaths(int Input, int Output);

	public ArrayList<ArrayList<Edge>> lookForCycles();

	public boolean nonTouching(ArrayList<Edge> path1, ArrayList<Edge> path2);

}

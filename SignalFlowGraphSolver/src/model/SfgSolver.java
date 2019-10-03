package model;

import java.util.ArrayList;

public interface SfgSolver {

	public double getOverallGain();

	public double[] getForwardPathsGains();

	public double[] getCyclesGains();

	public double[] getDeltas();

	public double[] getnNonTouchingGains(ArrayList<ArrayList<ArrayList<ArrayList<Edge>>>> combinations);

}

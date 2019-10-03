package model;

import java.util.ArrayList;

public class SfgSolverImp implements SfgSolver {

	private SfgAnalystImp sfgAnalyst;
	private SfgCombination sfgCombination;
	private ArrayList<ArrayList<ArrayList<ArrayList<Edge>>>> combinations;
	private ArrayList<ArrayList<Edge>> forwardPaths;
	private ArrayList<ArrayList<Edge>> cycles;

	public SfgSolverImp(SfgAnalystImp sfgAnalyst, SfgCombination sfgCombination) {
		this.sfgAnalyst = sfgAnalyst;
		this.setSfgCombination(sfgCombination);
		this.combinations = sfgCombination.getnNonTouchingCycles();
		this.forwardPaths = sfgAnalyst.getForwardPaths();
		this.cycles = sfgAnalyst.getCycles();
	}

	@Override
	public double getOverallGain() {
		double[] pathGains = getForwardPathsGains();
		double[] deltas = getDeltas();
		double numerator = 0;
		for (int i = 0; i < pathGains.length; i++) {
			numerator += pathGains[i] * deltas[i + 1];
		}
		return numerator / deltas[0];
	}

	// -------------------------------------------------------------------------------------------------------
	@Override
	public double[] getForwardPathsGains() {
		ArrayList<ArrayList<Edge>> forwardPaths = sfgAnalyst.getForwardPaths();
		double[] gains = new double[forwardPaths.size()];
		for (int i = 0; i < gains.length; i++) {
			gains[i] = getPathGain(forwardPaths.get(i));
		}
		return gains;
	}

	private double getPathGain(ArrayList<Edge> path) {
		double result = 1;
		for (int i = 0; i < path.size(); i++) {
			result *= path.get(i).getGain();
		}

		return result;

	}

	// -------------------------------------------------------------------------------------------------------
	@Override
	public double[] getCyclesGains() {
		double[] gains = new double[cycles.size()];
		for (int i = 0; i < gains.length; i++) {
			gains[i] = getCycleGain(cycles.get(i));
		}
		return gains;
	}

	private double getCycleGain(ArrayList<Edge> cycle) {
		double result = 1;
		for (int i = 0; i < cycle.size(); i++) {
			result *= cycle.get(i).getGain();
		}
		return result;
	}

	@Override
	public double[] getDeltas() {
		double[] deltas = new double[forwardPaths.size() + 1];
		deltas[0] = getDelta(getnNonTouchingGains(combinations));
		for (int i = 1; i < deltas.length; i++) {
			deltas[i] = getDelta(getnNonTouchingGains(
					sfgCombination.lookFornNonTouchingCylces(getCyclesWithoutIntersectionWithPath(i - 1))));
		}
		return deltas;
	}

	private double getDelta(double[] gains) {
		double delta = 1;
		int sign = -1;
		if (gains == null)
			return delta;
		for (int i = 0; i < gains.length; i++) {

			delta += sign * gains[i];
			sign *= -1;
		}
		return delta;
	}

	private ArrayList<ArrayList<Edge>> getCyclesWithoutIntersectionWithPath(int pathNumber) {
		ArrayList<ArrayList<Edge>> cyclesWithRemoval = new ArrayList<ArrayList<Edge>>();
		ArrayList<Edge> thisPath = forwardPaths.get(pathNumber);
		for (int i = 0; i < cycles.size(); i++) {
			if (sfgAnalyst.nonTouching(thisPath, cycles.get(i))) {
				cyclesWithRemoval.add(cycles.get(i));
			}
		}
	
		return cyclesWithRemoval;
	}

	public SfgAnalyst getSfgAnalyst() {
		return sfgAnalyst;
	}

	public void setSfgAnalyst(SfgAnalystImp sfgAnalyst) {
		this.sfgAnalyst = sfgAnalyst;
	}

	@Override
	public double[] getnNonTouchingGains(ArrayList<ArrayList<ArrayList<ArrayList<Edge>>>> combinations) {// need to
																											// check
																											// that it
																											// isn't
																											// null when
																											// it is to
																											// be used
		int totalNomber = combinations.size();
		if (totalNomber == 0) {
			return null;
		}
		double[] total = new double[totalNomber];
		for (int i = 0; i < totalNomber; i++) {
			total[i] = getNonTouchingGain(i + 1, combinations);
		}

		return total;
	}

	private double getNonTouchingGain(int numberOfNonTouching,
			ArrayList<ArrayList<ArrayList<ArrayList<Edge>>>> combinations) {
		double sum = 0;
		ArrayList<ArrayList<ArrayList<Edge>>> nontouch = combinations.get(numberOfNonTouching - 1);
		int combinationsCyclesNumber = nontouch.size();
		for (int i = 0; i < combinationsCyclesNumber; i++) {
			ArrayList<ArrayList<Edge>> currentCombination = nontouch.get(i);
			int numberOfCycles = currentCombination.size();
			double combinationGain = 1;
			for (int j = 0; j < numberOfCycles; j++) {
				combinationGain *= getCycleGain(currentCombination.get(j));

			}
			sum += combinationGain;
		}
		return sum;
	}

	public SfgCombination getSfgCombination() {
		return sfgCombination;
	}

	public void setSfgCombination(SfgCombination sfgCombination) {
		this.sfgCombination = sfgCombination;
	}

}

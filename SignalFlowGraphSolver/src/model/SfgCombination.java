package model;

import java.util.ArrayList;

public class SfgCombination {

	private ArrayList<ArrayList<ArrayList<ArrayList<Edge>>>> nNonTouchingCycles;
	private SfgAnalystImp analyst;

	public SfgCombination(SfgAnalystImp analyst) {
		this.analyst = analyst;
	}

	public ArrayList<ArrayList<ArrayList<ArrayList<Edge>>>> lookFornNonTouchingCylces(
			ArrayList<ArrayList<Edge>> cycles) {

		nNonTouchingCycles = new ArrayList<ArrayList<ArrayList<ArrayList<Edge>>>>();
		int totalNumberOfCycles = cycles.size();

		for (int i = 1; i <= totalNumberOfCycles; i++) {
			nNonTouchingCycles.add(new ArrayList<ArrayList<ArrayList<Edge>>>());
			completeSearchForNonTouchingCombination(cycles, new int[i], 0, totalNumberOfCycles - 1, 0, i);
			if (nNonTouchingCycles.get(i - 1).isEmpty())// sheel ala tyehgy el break dy we test laptopak haysta7mel wala
														// la2 XDDD law sheltaha hay7seb 3dd e7temalat ysawy
														// 2^totalNumberof loops beeha haywa2af lama mayla2eesh 5alas
														// ba2a asra3 bkteeeeeeeeer we kan sa3at by3od bta3 10 min
														// by7seb 7arfyan we may5alassh we msh infinite loop ;)
				break;
		}

		return nNonTouchingCycles;
	}

	private void completeSearchForNonTouchingCombination(ArrayList<ArrayList<Edge>> cycles, int[] CombinationIndeces,
			int start, int end, int index, int n) {

		if (index == n) {// add the new combination if it isn't touching.
			if (TestNonTouching(cycles, CombinationIndeces)) {
				nNonTouchingCycles.get(n - 1).add(new ArrayList<ArrayList<Edge>>());
				for (int j = 0; j < CombinationIndeces.length; j++) {
					nNonTouchingCycles.get(n - 1).get(nNonTouchingCycles.get(n - 1).size() - 1)
							.add(cycles.get(CombinationIndeces[j]));

				}
			}
			return;
		}

		for (int i = start; i <= end && end - i + 1 >= n - index; i++) {

			CombinationIndeces[index] = i;
			completeSearchForNonTouchingCombination(cycles, CombinationIndeces, i + 1, end, index + 1, n);// recursively
																											// get
																											// all
																											// possible
																											// combinations.
		}

	}

	private boolean TestNonTouching(ArrayList<ArrayList<Edge>> cycles, int[] indeces) {
		for (int i = 0; i < indeces.length; i++) {
			for (int j = i + 1; j < indeces.length; j++) {

				if (!analyst.nonTouching(cycles.get(indeces[i]), cycles.get(indeces[j])))
					return false;
			}
		}
		return true;
	}

	public ArrayList<ArrayList<ArrayList<ArrayList<Edge>>>> getnNonTouchingCycles() {// returns the nNon-touchingCycles
																						// without any path removal.
		return lookFornNonTouchingCylces(analyst.getCycles());

	}

	public void setnNonTouchingCycles(ArrayList<ArrayList<ArrayList<ArrayList<Edge>>>> nNonTouchingCycles) {
		this.nNonTouchingCycles = nNonTouchingCycles;
	}
}

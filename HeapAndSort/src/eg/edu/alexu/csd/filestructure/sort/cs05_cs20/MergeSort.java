package eg.edu.alexu.csd.filestructure.sort.cs05_cs20;

import java.util.ArrayList;

public class MergeSort<T extends Comparable<T>> {
	private ArrayList<T> tempArray;
	private ArrayList<T> mainArray;

	public MergeSort(ArrayList<T> unordered) {
		tempArray = new ArrayList<T>();
		mainArray = unordered;
	}

	public void Sort() {
		mergeSort(mainArray, 0, mainArray.size() - 1);
	}

	public void print() {
		for (int i = 0; i < mainArray.size(); i++)
			System.out.println(mainArray.get(i));
	}

	public void mergeSort(ArrayList<T> theArray, int first, int last) {
		if (first < last) {
			int mid = (first + last) / 2;
			mergeSort(theArray, first, mid);
			mergeSort(theArray, mid + 1, last);
			merge(theArray, first, mid, last);
		}
		return;
	}

	public void merge(ArrayList<T> theArray, int first, int mid, int last) {
		tempArray.clear();
		int first1 = first;
		int last1 = mid;
		int first2 = mid + 1;
		int last2 = last;
		int index = first1;
		for (; (first1 <= last1) && (first2 <= last2); index++) {
			if (theArray.get(first1).compareTo(theArray.get(first2)) < 0) {
				tempArray.add(theArray.get(first1));
				first1++;
			} else {
				tempArray.add(theArray.get(first2));
				first2++;
			}
		}

		for (; first1 <= last1; first1++, index++)
			tempArray.add(theArray.get(first1));

		for (; first2 <= last2; first2++, index++)
			tempArray.add(theArray.get(first2));

		int i = 0;
		for (index = first; index <= last; index++, i++)
			theArray.set(index, tempArray.get(i));

	}

}

package eg.edu.alexu.csd.filestructure.sort.cs05_cs20;

import java.util.ArrayList;

import javax.management.RuntimeErrorException;

import eg.edu.alexu.csd.filestructure.sort.IHeap;
import eg.edu.alexu.csd.filestructure.sort.ISort;

public class MySort<T extends Comparable<T>> implements ISort<T> {

	@Override
	public IHeap<T> heapSort(ArrayList<T> unordered) {// a clone is to be returned..........
		if (unordered == null) {
			throw new RuntimeErrorException(null);
		}
		IHeap<T> original = new MyHeap<>();
		original.build(unordered);
		int size = original.size();
		// here we should clone...........

		for (int i = unordered.size() - 1; i >= 0; i--) {
			unordered.set(i, original.extract());// put maximum at end of the array list and repeat.
		}
		IHeap<T> clone = new MyHeap<T>(unordered, size);
		return clone;
	}

	@Override
	public void sortSlow(ArrayList<T> unordered) {// bubble sort implementation.
		boolean sorted = false;
		if (unordered == null)
			return;
		int size = unordered.size();
		int last = size - 1;
		for (int i = 0; i < last && !sorted; i++) {
			sorted = true;
			for (int j = last; j > i; j--) {
				if (unordered.get(j - 1).compareTo(unordered.get(j)) > 0) {
					T temp = unordered.get(j - 1);
					unordered.set(j - 1, unordered.get(j));
					unordered.set(j, temp);
					sorted = false;
				}
			}
		}

	}

	@Override
	public void sortFast(ArrayList<T> unordered) {
		if (unordered == null)
			return;
		MergeSort<T> ms = new MergeSort<T>(unordered);
		ms.Sort();
	}

}

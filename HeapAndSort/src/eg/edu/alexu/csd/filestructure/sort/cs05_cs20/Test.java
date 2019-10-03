package eg.edu.alexu.csd.filestructure.sort.cs05_cs20;

import java.util.ArrayList;

import eg.edu.alexu.csd.filestructure.sort.IHeap;
import eg.edu.alexu.csd.filestructure.sort.ISort;

public class Test {

	public static void main(String[] args) {
		ArrayList<Integer> unsorted = new ArrayList<>();
		IHeap<Integer> heap = new MyHeap<Integer>();
		for (int i = 0; i < 10; i++) {
			unsorted.add(i);
			heap.insert(i);
		}
//		unsorted.add(5);
//		heap.insert(5);
//		unsorted.add(4);
//		heap.insert(4);
//		unsorted.add(7);
//		heap.insert(7);
//		unsorted.add(7);
//		heap.insert(7);
//		unsorted.add(3);
//		heap.insert(3);
//		unsorted.add(8);
//		heap.insert(8);
//		unsorted.add(15);
//		heap.insert(15);
//		unsorted.add(12);
//		heap.insert(12);
		System.out.println(heap.size());
		System.out.println(heap.getRoot().getValue());
		
		ISort<Integer> sort = new MySort<Integer>();
		IHeap<Integer> heap2 = sort.heapSort(unsorted);

		for (int i = 0; i < unsorted.size(); i++) {

			System.out.println(unsorted.get(i));
		}
		for (int i = 0; i < unsorted.size(); i++) {

			System.out.println(heap2.extract());
		}
	}
}

package eg.edu.alexu.csd.filestructure.sort.cs05_cs20;

import java.util.ArrayList;

public class TestMSort {

	public static void main(String[] args) {
		ArrayList<Integer> testArray;
		testArray = new ArrayList<>();
		testArray.add(5);
		testArray.add(4);
		testArray.add(7);
		testArray.add(7);
		testArray.add(3);
		testArray.add(8);
		testArray.add(15);
		testArray.add(12);
		MergeSort<Integer> ms = new MergeSort<>(testArray);
		ms.Sort();
		ms.print();
//		ArrayList<Integer> test = testArray;
		//ms.mergeSort(testArray, 0, testArray.size());
//		for (int index = 0; index < test.size(); index++)
//			System.out.println(test.get(index));
	}

}

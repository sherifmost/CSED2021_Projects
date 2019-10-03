package eg.edu.alexu.csd.filestructure.redblacktree.cs05_cs20;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.Map;
import java.util.Random;
import java.util.TreeMap;

import javax.management.RuntimeErrorException;

import eg.edu.alexu.csd.filestructure.redblacktree.INode;
import eg.edu.alexu.csd.filestructure.redblacktree.IRedBlackTree;
import eg.edu.alexu.csd.filestructure.redblacktree.ITreeMap;

public class testing {

	public static void main(String[] args) {

		ITreeMap<Integer, String> treemap = new MyTreeMap<>();
		try {
			TreeMap<Integer, String> t = new TreeMap();
			Random r = new Random();
			for (int i = 0; i < 1000; i++) {
				int key = r.nextInt(10000);
				t.put(Integer.valueOf(key), "soso" + key);
				treemap.put(Integer.valueOf(key), "soso" + key);
			}
			Iterator<Map.Entry<Integer, String>> itr1 = treemap.entrySet().iterator();
			Iterator<Map.Entry<Integer, String>> itr2 = t.entrySet().iterator();
			do {
				Map.Entry<Integer, String> entry1 = (Map.Entry) itr1.next();
				Map.Entry<Integer, String> entry2 = (Map.Entry) itr2.next();
				System.out.println(entry1 + " " + entry2);
				if (!itr1.hasNext())
					break;
			} while (itr2.hasNext());

		} catch (Throwable e) {
			System.out.println("error");
			e.printStackTrace();
		}

	}
}
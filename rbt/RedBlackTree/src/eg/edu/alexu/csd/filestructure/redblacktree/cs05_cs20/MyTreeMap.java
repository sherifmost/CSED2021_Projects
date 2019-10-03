package eg.edu.alexu.csd.filestructure.redblacktree.cs05_cs20;

import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Map.Entry;

import javax.management.RuntimeErrorException;

import java.util.Set;

import eg.edu.alexu.csd.filestructure.redblacktree.INode;
import eg.edu.alexu.csd.filestructure.redblacktree.ITreeMap;

public class MyTreeMap<T extends Comparable<T>, V extends Comparable<V>> implements ITreeMap<T, V> {
	
	private MyRedBlackTree<T, V> rbTree = new MyRedBlackTree<T, V>();// the tree data structure to be used which is the
																		// implemented red black tree.
	@Override
	public Entry<T, V> ceilingEntry(T key) {
		if (key == null)
			throw new RuntimeErrorException(null);
		INode<T, V> node = rbTree.nil;
		INode<T, V> traversal = rbTree.getRoot();
		while (traversal != rbTree.nil) {// traverse the tree while updating the ceil value if we go to the left
											// subtree.
			if (key.compareTo(traversal.getKey()) > 0) {
				traversal = traversal.getRightChild();
			} else if (key.compareTo(traversal.getKey()) < 0) {
				node = traversal;
				traversal = traversal.getLeftChild();
			} else {
				return new AbstractMap.SimpleEntry<T, V>(traversal.getKey(), traversal.getValue());
			}

		}
		if (node != rbTree.nil)
			return new AbstractMap.SimpleEntry<T, V>(node.getKey(), node.getValue());
		return null;

	}

	@Override
	public T ceilingKey(T key) {
		Entry<T, V> found = ceilingEntry(key);
		if (found == null)
			return null;
		return found.getKey();
	}

	@Override
	public void clear() {
		rbTree.clear();
	}

	@Override
	public boolean containsKey(T key) {
		return rbTree.contains(key);
	}

	@Override
	public boolean containsValue(V value) {// will have to traverse the tree which is O(n).
		if (value == null)
			throw new RuntimeErrorException(null);
		Collection<V> checker = new ArrayList<V>();
		fillValuesInorder(checker, rbTree.getRoot());// still O(n) if we traverse the whole tree to check for the value.
		return checker.contains(value);
	}

	@Override
	public Set<Entry<T, V>> entrySet() {
		if (rbTree.isEmpty())
			throw new RuntimeErrorException(null);
		Set<Entry<T, V>> values = new LinkedHashSet<Entry<T, V>>();
		fillEntriesInorder(values, rbTree.getRoot());
		return values;
	}

	private void fillEntriesInorder(Collection<Entry<T, V>> values, INode<T, V> node) {
		if (node == rbTree.nil)
			return;
		fillEntriesInorder(values, node.getLeftChild());
		values.add(new AbstractMap.SimpleEntry<T, V>(node.getKey(), node.getValue()));
		fillEntriesInorder(values, node.getRightChild());
	}

	@Override
	public Entry<T, V> firstEntry() {
		if (rbTree.isEmpty())
			return null;
		INode<T, V> node = rbTree.minimum(rbTree.getRoot());
		return new AbstractMap.SimpleEntry<T, V>(node.getKey(), node.getValue());
	}

	@Override
	public T firstKey() {
		if (rbTree.isEmpty())
			return null;
		return rbTree.minimum(rbTree.getRoot()).getKey();
	}

	@Override
	public Entry<T, V> floorEntry(T key) {
		if (key == null)
			throw new RuntimeErrorException(null);
		INode<T, V> node = rbTree.nil;
		INode<T, V> traversal = rbTree.getRoot();
		while (traversal != rbTree.nil) {// traverse the tree while updating the floor value if we go to the left
											// subtree.
			if (key.compareTo(traversal.getKey()) > 0) {
				node = traversal;
				traversal = traversal.getRightChild();
			} else if (key.compareTo(traversal.getKey()) < 0) {
				traversal = traversal.getLeftChild();
			} else {
				return new AbstractMap.SimpleEntry<T, V>(traversal.getKey(), traversal.getValue());
			}

		}
		if (node != rbTree.nil)
			return new AbstractMap.SimpleEntry<T, V>(node.getKey(), node.getValue());
		return null;
	}

	@Override
	public T floorKey(T key) {
		Entry<T, V> found = floorEntry(key);
		if (found == null)
			return null;
		return found.getKey();
	}

	@Override
	public V get(T key) {
		return rbTree.getNode(key).getValue();
	}

	@Override
	public ArrayList<Entry<T, V>> headMap(T toKey) {
		ArrayList<Entry<T, V>> values = new ArrayList<Entry<T, V>>();
		if (toKey == null)
			throw new RuntimeErrorException(null);
		fillEntriesInorder(values, rbTree.getRoot(), toKey, false);
		return values;
	}

	@Override
	public ArrayList<Entry<T, V>> headMap(T toKey, boolean inclusive) {
		ArrayList<Entry<T, V>> values = new ArrayList<Entry<T, V>>();
		if (toKey == null)
			throw new RuntimeErrorException(null);
		fillEntriesInorder(values, rbTree.getRoot(), toKey, inclusive);
		return values;
	}

	private void fillEntriesInorder(Collection<Entry<T, V>> values, INode<T, V> node, T toKey, boolean inclusive) {
		if (node == rbTree.nil)
			return;
		fillEntriesInorder(values, node.getLeftChild(), toKey, inclusive);
		if ((node.getKey().compareTo(toKey) < 0 || (node.getKey() == toKey && inclusive)))
			values.add(new AbstractMap.SimpleEntry<T, V>(node.getKey(), node.getValue()));
		fillEntriesInorder(values, node.getRightChild(), toKey, inclusive);
	}

	@Override
	public Set<T> keySet() {
		if (rbTree.isEmpty())
			throw new RuntimeErrorException(null);
		Set<T> values = new LinkedHashSet<T>();
		fillKeysInorder(values, rbTree.getRoot());
		return values;
	}

	private void fillKeysInorder(Collection<T> values, INode<T, V> node) {
		if (node == rbTree.nil)
			return;
		fillKeysInorder(values, node.getLeftChild());
		values.add(node.getKey());
		fillKeysInorder(values, node.getRightChild());
	}

	@Override
	public Entry<T, V> lastEntry() {
		if (rbTree.isEmpty())
			return null;
		INode<T, V> node = rbTree.maximum(rbTree.getRoot());
		return new AbstractMap.SimpleEntry<T, V>(node.getKey(), node.getValue());
	}

	@Override
	public T lastKey() {
		if (rbTree.isEmpty())
			return null;
		return rbTree.maximum(rbTree.getRoot()).getKey();
	}

	@Override
	public Entry<T, V> pollFirstEntry() {
		if (rbTree.isEmpty())
			return null;
		INode<T, V> largest = rbTree.minimum(rbTree.getRoot());
		rbTree.delete(largest.getKey());
		return new AbstractMap.SimpleEntry<T, V>(largest.getKey(), largest.getValue());
	}

	@Override
	public Entry<T, V> pollLastEntry() {
		if (rbTree.isEmpty())
			return null;
		INode<T, V> largest = rbTree.maximum(rbTree.getRoot());
		rbTree.delete(largest.getKey());
		return new AbstractMap.SimpleEntry<T, V>(largest.getKey(), largest.getValue());
	}

	@Override
	public void put(T key, V value) {
		rbTree.insert(key, value);

	}

	@Override
	public void putAll(Map<T, V> map) {
		if (map == null)
			throw new RuntimeErrorException(null);
		for (Entry<T, V> entry : map.entrySet()) {
			if (entry != null)
				rbTree.insert(entry.getKey(), entry.getValue());
		}

	}

	@Override
	public boolean remove(T key) {
		return rbTree.delete(key);
	}

	@Override
	public int size() {
		return rbTree.getSize();
	}

	@Override
	public Collection<V> values() {
		Collection<V> returned = new ArrayList<V>();
		fillValuesInorder(returned, rbTree.getRoot());
		return returned;
	}

	private void fillValuesInorder(Collection<V> values, INode<T, V> node) {
		if (node == rbTree.nil)
			return;
		fillValuesInorder(values, node.getLeftChild());
		values.add(node.getValue());
		fillValuesInorder(values, node.getRightChild());
	}

}

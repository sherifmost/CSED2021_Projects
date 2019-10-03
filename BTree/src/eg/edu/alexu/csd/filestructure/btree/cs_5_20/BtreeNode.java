package eg.edu.alexu.csd.filestructure.btree.cs_5_20;

import java.util.ArrayList;
import java.util.List;

import eg.edu.alexu.csd.filestructure.btree.IBTreeNode;

public class BtreeNode<K extends Comparable<K>, V> implements IBTreeNode<K, V> {

	private int numOfKeys;
	// Keys and values in this node index based from 0 to numberOfKeys-1
	private List<K> keys = new ArrayList<K>();
	private List<V> values = new ArrayList<V>();
	// An array list containing all the children of this node.
	private List<IBTreeNode<K, V>> children = new ArrayList<IBTreeNode<K, V>>();
	private boolean isLeaf;

	public BtreeNode() {// allocates a new node
		setNumOfKeys(0);
		setLeaf(true);
	}

	@Override
	public int getNumOfKeys() {
		return numOfKeys;
	}

	@Override
	public void setNumOfKeys(int numOfKeys) {
		this.numOfKeys = numOfKeys;
	}

	@Override
	public boolean isLeaf() {
		return isLeaf;
	}

	@Override
	public void setLeaf(boolean isLeaf) {
		this.isLeaf = isLeaf;
	}

	@Override
	public List<K> getKeys() {
		return keys;
	}

	@Override
	public void setKeys(List<K> keys) {
		this.keys = keys;
		setNumOfKeys(keys.size());
	}

	@Override
	public List<V> getValues() {
		return values;
	}

	@Override
	public void setValues(List<V> values) {
		this.values = values;
	}

	@Override
	public List<IBTreeNode<K, V>> getChildren() {
		return children;
	}

	@Override
	public void setChildren(List<IBTreeNode<K, V>> children) {
		this.children = children;
	}

	// Additional functions:
	public void addChild(IBTreeNode<K, V> child) { // Adds a child at the end of the list
		children.add(child);
	}

	public void addChild(IBTreeNode<K, V> child, int location) {// Adds a child at the specified location right shifting
																// any children at higher locations.
		children.add(location, child);
	}

	public void removeChild(IBTreeNode<K, V> child) {// removes the given child node from the list possibly shifting all
														// those children to its right to the left
		children.remove(child);
	}

	public void removeChild(int location) {// removes child node from the given location in the list possibly shifting
											// all those children to its right to the left
		children.remove(location);
	}

	// same with the keys/values lists:
	public void addEntry(K key, V value) {
		numOfKeys++;
		keys.add(key);
		values.add(value);
	}

	public void addEntry(K key, V value, int location) {
		numOfKeys++;
		keys.add(location, key);
		values.add(location, value);
	}

	public void removeEntry(K key) {// given the key remove this entry.
		int entryLoc = keys.indexOf(key);
		keys.remove(entryLoc);
		values.remove(entryLoc);
		numOfKeys--;
	}

	public void removeEntryWithLocation(int location) {// given the location remove this entry.
		keys.remove(location);
		values.remove(location);
		numOfKeys--;
	}

	// Testing function to print out the sub tree rooted at this node by recursively
	// traversing this subtree in order:
	public void printInOrder() {// number of children = number of keys +1 unless this node is a leaf.
		int i;
		for (i = 0; i < getNumOfKeys(); i++) {// in order of this node and its first n children.
			if (!isLeaf())
				((BtreeNode<K, V>) children.get(i)).printInOrder();
			System.out.print(" " + keys.get(i));
		}
		if (!isLeaf())
			((BtreeNode<K, V>) children.get(i)).printInOrder();// for n+1 child.
	}

}

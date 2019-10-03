package eg.edu.alexu.csd.filestructure.btree.cs_5_20;

import java.util.ArrayList;
import javax.management.RuntimeErrorException;
import eg.edu.alexu.csd.filestructure.btree.IBTree;
import eg.edu.alexu.csd.filestructure.btree.IBTreeNode;

public class MyBtree<K extends Comparable<K>, V> implements IBTree<K, V> {// the main class implementing the B tree data
																			// Structure.

	private int t;// the minimum degree of the B tree to be determined in the constructor.
	private BtreeNode<K, V> root;// to be set as a newly allocated node in the B tree in case the B tree was
									// allocated.

	public MyBtree() {// if t wasn't specified set it as the minimum possible degree 2.
		this.t = 2;
	}

	public MyBtree(int t) {
		this.t = t;
		if (t < 2)
			throw new RuntimeErrorException(null);
	}

	@Override
	public int getMinimumDegree() {
		return t;
	}

	public void setRoot(BtreeNode<K, V> root) {
		this.root = root;
	}

	public boolean isEmpty() {
		return root.getNumOfKeys() == 0;
	}

	@Override
	public IBTreeNode<K, V> getRoot() {
		return root;
	}

	@Override
	public void insert(K key, V value) {
		if (root == null)
			root = new BtreeNode<>();
		if (key == null || value == null) {
			throw new RuntimeErrorException(null);
		}
		if (this.contains(key, root)) {// the tree already contains an entry with the given key so do nothing.
			return;
		}
		// first if the root is full we split the root:
		if (root.getNumOfKeys() == 2 * getMinimumDegree() - 1) {
			BtreeNode<K, V> newRoot = new BtreeNode<K, V>();
			// new root is made and the old root is its first child, this increases the
			// height of the tree by one from up
			newRoot.setLeaf(false);
			newRoot.addChild(root);
			splitChild(0, newRoot);
			// after splitting we must decide which of the two resulting children is the
			// correct insertion position.
			int i = 0;
			if (key.compareTo(newRoot.getKeys().get(0)) > 0)// key is in the second position.
				i = 1;
			insertNonFull(key, value, (BtreeNode<K, V>) newRoot.getChildren().get(i));
			setRoot(newRoot);
		} else
			insertNonFull(key, value, root);
	}

	private void insertNonFull(K key, V value, BtreeNode<K, V> node) {// a recursive function that assumes that the
																		// recursion
		// descends to a non full node and goes on till
		// inserting in a leaf

		int i = node.getNumOfKeys() - 1;// last key location in this node.
		if (node.isLeaf()) {
			while (i >= 0 && node.getKeys().get(i).compareTo(key) > 0)
				i--;
			i++;
			node.addEntry(key, value, i);// add the new key value mapping possibly shifting other existing entries to
											// the right
		} else// not a leaf so recursively descend
		{
			// find correct child:
			while (i >= 0 && node.getKeys().get(i).compareTo(key) > 0)
				i--;
			i++;
			if (node.getChildren().get(i).getNumOfKeys() == 2 * getMinimumDegree() - 1) {// found child was full so need
																							// to split it before
																							// descend
				splitChild(i, node);
				if (key.compareTo(node.getKeys().get(i)) > 0)// should be placed in the second child
					i++;
			}
			insertNonFull(key, value, (BtreeNode<K, V>) node.getChildren().get(i));
		}
	}

	private void splitChild(int location, IBTreeNode<K, V> node) {// given the parent node and the child location it
																	// splits the child into two children and pushes the
																	// median key up to the parent
		IBTreeNode<K, V> child = node.getChildren().get(location);
		IBTreeNode<K, V> sibling = new BtreeNode<K, V>();
		sibling.setNumOfKeys(getMinimumDegree() - 1);
		sibling.setLeaf(child.isLeaf());
		// to remove the entries from the array list of the child, extra temporary array
		// lists are to be used to decrease the time complexity
		ArrayList<K> tempKeys = new ArrayList<K>();
		ArrayList<V> tempValues = new ArrayList<V>();
		ArrayList<IBTreeNode<K, V>> tempChildren = new ArrayList<IBTreeNode<K, V>>();
		// set the entries of the key as the last t entries in the node and also the
		// children if the node wasn't a leaf
		for (int i = 0; i < getMinimumDegree() - 1; i++) {
			tempKeys.add(child.getKeys().get(i));
			tempValues.add(child.getValues().get(i));
			sibling.getKeys().add(child.getKeys().get(i + getMinimumDegree()));
			sibling.getValues().add(child.getValues().get(i + getMinimumDegree()));
			if (!child.isLeaf()) {
				tempChildren.add(child.getChildren().get(i));
				sibling.getChildren().add(child.getChildren().get(i + getMinimumDegree()));
			}
		}
		if (!child.isLeaf()) {
			sibling.getChildren().add(child.getChildren().get(2 * getMinimumDegree() - 1));
			tempChildren.add(child.getChildren().get(getMinimumDegree() - 1));
		}
		node.getChildren().add(location + 1, sibling);
		node.getKeys().add(location, child.getKeys().get(getMinimumDegree() - 1));
		node.getValues().add(location, child.getValues().get(getMinimumDegree() - 1));
		node.setNumOfKeys(node.getNumOfKeys() + 1);
		child.setChildren(tempChildren);
		child.setKeys(tempKeys);
		child.setValues(tempValues);
		child.setNumOfKeys(getMinimumDegree() - 1);
	}

	@Override
	public V search(K key) {// searches for the value corresponding to the given key and returns it.
							// returns null if the key is not in the b tree.
		if (key == null) {
			throw new RuntimeErrorException(null);
		}
		return searchByNode(key, root);
	}

	private V searchByNode(K key, IBTreeNode<K, V> node) {// recursively search for the key in the subtree rooted by
															// this node.
		int i = 0;
		while (i < node.getNumOfKeys() && key.compareTo(node.getKeys().get(i)) > 0)
			i++;// traverse the node keys till finding the range in which the required key
				// should exist.
		if (i < node.getNumOfKeys() && key.compareTo(node.getKeys().get(i)) == 0)// key was found in this node at this
																					// location.
			return node.getValues().get(i);
		if (node.isLeaf())// key wasn't found in this subtree.
			return null;
		// recursively descend the search.
		return searchByNode(key, node.getChildren().get(i));
	}

	public boolean contains(K key, IBTreeNode<K, V> node) {// returns true if the subtree rooted by this node contains
															// the given key : similar to the searching implementation
															// but is used to differentiate between a null value and a
															// non existing key
		int i = 0;
		while (i < node.getNumOfKeys() && key.compareTo(node.getKeys().get(i)) > 0)
			i++;
		if (i < node.getNumOfKeys() && key.compareTo(node.getKeys().get(i)) == 0)
			return true;
		if (node.isLeaf())
			return false;
		return contains(key, node.getChildren().get(i));
	}

	private void delete(K key, IBTreeNode<K, V> node) {// a function that recursively descends to delete the key from
														// the tree, as it does so it makes sure that the descend is to
														// a node containing at least t keys
		// get key range location
		int i = 0;
		while (i < node.getNumOfKeys() && key.compareTo(node.getKeys().get(i)) > 0)
			i++;
		// case key is in the node:
		if (i < node.getNumOfKeys() && key.compareTo(node.getKeys().get(i)) == 0) {
			if (node.isLeaf())
				deleteLeaf(i, node);
			else
				deleteNonLeaf(i, node);
		} else {
			// if descend will be to a child with less than t keys we need to add a key to
			// this child
			boolean last = (i == node.getNumOfKeys());
			if (node.getChildren().get(i).getNumOfKeys() < getMinimumDegree())
				addKey(i, node);
			// in case of merging the number of keys decreases
			boolean merged = (i > node.getNumOfKeys());
			// now the recursive descend
			if (last && merged)
				delete(key, node.getChildren().get(i - 1));
			else
				delete(key, node.getChildren().get(i));
		}
	}

	private void deleteLeaf(int location, IBTreeNode<K, V> node) {
		node.getKeys().remove(location);
		node.getValues().remove(location);
		node.setNumOfKeys(node.getNumOfKeys() - 1);
	}

	private void deleteNonLeaf(int location, IBTreeNode<K, V> node) {
		// if previous child has more that t-1 keys work with predecessor
		if (node.getChildren().get(location).getNumOfKeys() > getMinimumDegree() - 1) {
			K predecessorKey = getPredecessorKey(location, node);
			V predecessorValue = getPredecessorValue(location, node);
			node.getKeys().set(location, predecessorKey);
			node.getValues().set(location, predecessorValue);
			delete(predecessorKey, node.getChildren().get(location));
		}
		// else if next child has more than t-1 keys work with successor
		else if (node.getChildren().get(location + 1).getNumOfKeys() > getMinimumDegree() - 1) {
			K successorKey = getSuccessorKey(location, node);
			V successorValue = getSuccessorValue(location, node);
			node.getKeys().set(location, successorKey);
			node.getValues().set(location, successorValue);
			delete(successorKey, node.getChildren().get(location + 1));

		}
		// else we have to merge
		else {
			K key = node.getKeys().get(location);
			merge(location, node);
			delete(key, node.getChildren().get(location));
		}
	}

	// to get predecessor node traverse all the way down from the nearest keys to
	// your left
	private K getPredecessorKey(int location, IBTreeNode<K, V> node) {
		IBTreeNode<K, V> currentNode = node.getChildren().get(location);
		while (!currentNode.isLeaf()) {
			currentNode = currentNode.getChildren().get(currentNode.getNumOfKeys());
		}
		return currentNode.getKeys().get(currentNode.getNumOfKeys() - 1);
	}

	private V getPredecessorValue(int location, IBTreeNode<K, V> node) {
		IBTreeNode<K, V> currentNode = node.getChildren().get(location);
		while (!currentNode.isLeaf()) {
			currentNode = currentNode.getChildren().get(currentNode.getNumOfKeys());
		}
		return currentNode.getValues().get(currentNode.getNumOfKeys() - 1);
	}

	// to get successor node traverse all the way down to the nearest key location
	// from your right.
	private K getSuccessorKey(int location, IBTreeNode<K, V> node) {
		IBTreeNode<K, V> currentNode = node.getChildren().get(location + 1);
		while (!currentNode.isLeaf()) {
			currentNode = currentNode.getChildren().get(0);
		}
		return currentNode.getKeys().get(0);
	}

	private V getSuccessorValue(int location, IBTreeNode<K, V> node) {
		IBTreeNode<K, V> currentNode = node.getChildren().get(location + 1);
		while (!currentNode.isLeaf()) {
			currentNode = currentNode.getChildren().get(0);
		}
		return currentNode.getValues().get(0);
	}

	private void addKey(int location, IBTreeNode<K, V> node) {
		// check to borrow from previous or next child:
		if (location != 0 && node.getChildren().get(location - 1).getNumOfKeys() > getMinimumDegree() - 1)
			getFromPrevious(location, node);
		else if (location != node.getNumOfKeys()
				&& node.getChildren().get(location + 1).getNumOfKeys() > getMinimumDegree() - 1)
			getFromNext(location, node);
		// otherwise merge the child with one of his siblings.
		else {
			if (location != node.getNumOfKeys())
				merge(location, node);
			else
				merge(location - 1, node);
		}
	}

	// get a key value mapping from left sibling to the child
	private void getFromPrevious(int location, IBTreeNode<K, V> node) {
		IBTreeNode<K, V> child = node.getChildren().get(location);
		IBTreeNode<K, V> sibling = node.getChildren().get(location - 1);
		// last from sibling goes to parent and entry at location location - 1 in the
		// node goes to the child
		child.getKeys().add(0, node.getKeys().get(location - 1));
		child.getValues().add(0, node.getValues().get(location - 1));
		if (!child.isLeaf())
			child.getChildren().add(0, sibling.getChildren().get(sibling.getNumOfKeys()));
		node.getKeys().set(location - 1, sibling.getKeys().get(sibling.getNumOfKeys() - 1));
		node.getValues().set(location - 1, sibling.getValues().get(sibling.getNumOfKeys() - 1));
		sibling.getKeys().remove(sibling.getNumOfKeys() - 1);
		sibling.getValues().remove(sibling.getNumOfKeys() - 1);
		if (!sibling.isLeaf())
			sibling.getChildren().remove(sibling.getNumOfKeys());
		child.setNumOfKeys(child.getNumOfKeys() + 1);
		sibling.setNumOfKeys(sibling.getNumOfKeys() - 1);
	}

	// get a key value mapping from right sibling to the child
	private void getFromNext(int location, IBTreeNode<K, V> node) {
		IBTreeNode<K, V> child = node.getChildren().get(location);
		IBTreeNode<K, V> sibling = node.getChildren().get(location + 1);
		// Analogous to getting from previous sibling:
		child.getKeys().add(node.getKeys().get(location));
		child.getValues().add(node.getValues().get(location));
		if (!child.isLeaf())
			child.getChildren().add(sibling.getChildren().get(0));
		node.getKeys().set(location, sibling.getKeys().get(0));
		node.getValues().set(location, sibling.getValues().get(0));
		sibling.getKeys().remove(0);
		sibling.getValues().remove(0);
		if (!sibling.isLeaf())
			sibling.getChildren().remove(0);
		child.setNumOfKeys(child.getNumOfKeys() + 1);
		sibling.setNumOfKeys(sibling.getNumOfKeys() - 1);
	}

	// merging children at location and location + 1.
	private void merge(int location, IBTreeNode<K, V> node) {
		IBTreeNode<K, V> child = node.getChildren().get(location);
		IBTreeNode<K, V> sibling = node.getChildren().get(location + 1);
		// placing the median key
		child.getKeys().add(node.getKeys().get(location));
		child.getValues().add(node.getValues().get(location));
		for (int i = 0; i < sibling.getNumOfKeys(); i++) {
			child.getKeys().add(sibling.getKeys().get(i));
			child.getValues().add(sibling.getValues().get(i));
			if (!child.isLeaf())
				child.getChildren().add(sibling.getChildren().get(i));
		}
		if (!child.isLeaf())
			child.getChildren().add(sibling.getChildren().get(sibling.getNumOfKeys()));
		node.getKeys().remove(location);
		node.getValues().remove(location);
		node.getChildren().remove(location + 1);// removing the sibling node to undergo garbage collection
		child.setNumOfKeys(child.getNumOfKeys() + sibling.getNumOfKeys() + 1);
		node.setNumOfKeys(node.getNumOfKeys() - 1);
	}

	@Override
	public boolean delete(K key) {
		if (key == null)
			throw new RuntimeErrorException(null);
		if (root == null || !contains(key, root))
			return false;
		delete(key, root);
		// if now root is empty we remove it
		if (root.getNumOfKeys() == 0) {
			if (root.isLeaf())
				root = null;// now tree is completely empty
			else
				setRoot((BtreeNode<K, V>) root.getChildren().get(0));// set root as its only child
		}
		return true;
	}

}

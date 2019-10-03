package eg.edu.alexu.csd.filestructure.sort.cs05_cs20;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import javax.management.RuntimeErrorException;

import eg.edu.alexu.csd.filestructure.sort.IHeap;
import eg.edu.alexu.csd.filestructure.sort.INode;

public class MyHeap<T extends Comparable<T>> implements IHeap<T> {

	public MyHeap(ArrayList<T> cloned, int size) {
		setSize(size);
		setHeap(cloned);
	}

	public MyHeap() {

	}

	private ArrayList<INode<T>> heap = new ArrayList<>(); // the heap is implemented as an array list which is a
															// dynamic array 1 indexed.
	private int size = 0;

	private void setHeap(ArrayList<T> heap) {
		this.heap.add(null);
		for (int i = 1; i <= heap.size(); i++) {
			INode<T> added = new MyNode<T>();
			added.setValue(heap.get(i - 1));
			((MyNode<T>) added).setIndex(i);
			this.heap.add(added);
		}
	}

	public void setSize(int size) {
		this.size = size;
	}

	@Override
	public INode<T> getRoot() {
		if (size > 0)
			return heap.get(1);
		throw new RuntimeErrorException(null);
	}

	@Override
	public int size() {
		return size;
	}

	@Override
	public void heapify(INode<T> node) {
		if (node != null) {
			INode<T> largest = node;// casting should be checked.
			INode<T> left = node.getLeftChild();
			INode<T> right = node.getRightChild();
			if (left != null && left.getValue().compareTo(largest.getValue()) > 0) {
				largest = left;
			}
			if (right != null && right.getValue().compareTo(largest.getValue()) > 0) {
				largest = right;
			}
			if (largest != node) {// swapping should be checked.
				swap(node, largest);
				heapify(largest);
			}
		}
	}

	@Override
	public T extract() {
		if (size > 0) {
			T max = getRoot().getValue();
			swap(getRoot(), getLast());
			heap.remove(heap.size() - 1);
			size--;
			if (size != 0)
				heapify(getRoot());
			return max;
		}
		return null;
	}

	@Override
	public void insert(T element) {
		if (element != null) {
			if (heap.size() == 0) {// nothing inserted yet.
				heap.add(null);// add a null at the first position indicating the null root parent.
			}
			int last = heap.size();
			INode<T> added = new MyNode<>();
			added.setValue(element);
			((MyNode<T>) added).setIndex(last);
			heap.add(added);
			size++;
			siftUp(added);
		}
	}

	@Override
	public void build(Collection<T> unordered) {

		if (unordered == null) {
			throw new RuntimeErrorException(null);
		}
		// heap.clear();// check its running time......
		Iterator<T> iterator = unordered.iterator();
		while (iterator.hasNext()) {
			insert(iterator.next());
		}

	}

	private void siftUp(INode<T> node) {

		if (node.getParent() != null) {

			if (node.getValue().compareTo(node.getParent().getValue()) > 0) {
				swap(node, node.getParent());
				siftUp(node.getParent());
			}
		}
	}

	private INode<T> getLast() {
		return heap.get(size);
	}

	private void swap(INode<T> first, INode<T> second) {// just swap values and indeces.
		T tempValue = first.getValue();
		first.setValue(second.getValue());
		second.setValue(tempValue);

	}

	@SuppressWarnings("hiding")
	private class MyNode<T extends Comparable<T>> implements INode<T> {
		private T value;
		private int index;

		@SuppressWarnings("unchecked")
		@Override
		public INode<T> getLeftChild() {
			if (2 * index < heap.size())
				return (INode<T>) heap.get(2 * index);
			return null;
		}

		@SuppressWarnings("unchecked")
		@Override
		public INode<T> getRightChild() {
			if (2 * index + 1 < heap.size())
				return (INode<T>) heap.get(2 * index + 1);
			return null;
		}

		@SuppressWarnings("unchecked")
		@Override
		public INode<T> getParent() {
			if (index != 1)
				return (INode<T>) heap.get(index / 2);
			return null;
		}

		@Override
		public T getValue() {
			return value;
		}

		@Override
		public void setValue(T value) {
			this.value = value;

		}

		public void setIndex(int index) {
			this.index = index;
		}

	}
}

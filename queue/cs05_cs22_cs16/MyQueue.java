package eg.edu.alexu.csd.datastructure.queue.cs05_cs22_cs16;


import eg.edu.alexu.csd.datastructure.linkedList.cs22_cs05.DoublyLinkedList;
import eg.edu.alexu.csd.datastructure.queue.ILinkedBased;
import eg.edu.alexu.csd.datastructure.queue.IQueue;

/**
 * @author HP
 */
public class MyQueue implements IQueue, ILinkedBased {
	/**
	 * @
	 */
	DoublyLinkedList d = new DoublyLinkedList();

	@Override
	public void enqueue(final Object item) {
		d.add(item);
	}

	@Override
	public Object dequeue() {
		if (d.size() == 0) {
			throw new RuntimeException();
		}
		Object item = d.get(0);
		d.remove(0);
		return item;
	}

	@Override
	public boolean isEmpty() {
		if (d.isEmpty()) {
			return true;
		}
		return false;
	}

	@Override
	public int size() {
		return d.size();
	}

}

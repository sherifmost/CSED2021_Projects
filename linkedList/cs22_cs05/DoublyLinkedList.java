package eg.edu.alexu.csd.datastructure.linkedList.cs22_cs05;

import eg.edu.alexu.csd.datastructure.linkedList.ILinkedList;

/**
 *
 * @author Ahmed Ali
 *
 */
public class DoublyLinkedList implements ILinkedList {

	/**
	 * @author Ahmed Ali
	 */
	public DoublyNode head = new DoublyNode(null, null, null);
	/**
	 * @author Ahmed Ali
	 */
	public DoublyNode trailer = new DoublyNode(null, null, null);
	/**
	 * @author Ahmed Ali
	 */
	public int size = 0;
	/**
	 * @author Ahmed Ali
	 */
	public DoublyLinkedList() {
		head.next = trailer;
		trailer.prev = head;
		size = 0;
	}

	@Override
	/**
	 * @author Ahmed Ali
	 */
	public void add(final int index, final Object element) {
		int i;
		DoublyNode j, k;
		j = head;
		k = new DoublyNode(element, null, null);
		if (index < 0 || element == null) {
			throw new RuntimeException();
		}
		for (i = 0; i < index; i++) {
			j = j.next;
			if (j == trailer) {
				throw new RuntimeException();
			}

		}
		if (size == 0) {
			k.next = head.next;
			k.prev = head;
			head.next.prev = k;
			head.next = k;
			size++;

		} else {
			k.next = j.next;
			k.prev = j;
			j.next.prev = k;
			j.next = k;
			size++;
		}
	}

	@Override
	/**
	 * @author Ahmed Ali
	 */
	public void add(final Object element) {
		if (element == null) {
			throw new RuntimeException();
		}
		DoublyNode n = new DoublyNode(element, trailer, trailer.prev);
		if (size == 0) {
			head.next = n;
			trailer.prev = n;
			size++;

		} else {
			trailer.prev.next = n;
			trailer.prev = n;
			size++;
		}
	}

	@Override
	/**
	 * @author Ahmed Ali
	 */
	public Object get(final int index) {
		if (index < 0) {
			throw new RuntimeException();
		}
		int i;
		DoublyNode n = head;
		for (i = 0; i <= index; i++) {
			n = n.next;
			if (n == trailer) {
				throw new RuntimeException();
			}

		}

		return n.data;
	}

	@Override
	/**
	 * @author Ahmed Ali
	 */
	public void set(final int index, final Object element) {
		int i;
		DoublyNode n;
		n = head;
		if (index < 0 || element == null) {
			throw new RuntimeException();
		}
		for (i = 0; i <= index; i++) {
			n = n.next;
			if (n == trailer) {
				throw new RuntimeException();
			}
		}
		n.data = element;
	}

	@Override
	/**
	 * @author Ahmed Ali
	 */
	public void clear() {
		head.next = trailer;
		trailer.prev = head;
		size = 0;
	}

	@Override
	/**
	 * @author Ahmed Ali
	 */
	public boolean isEmpty() {
		if (size == 0) {
			return true;
		}
		return false;
	}

	@Override
	/**
	 * @author Ahmed Ali
	 */
	public void remove(final int index) {
		int c;
		DoublyNode n = head;
		if (index < 0) {
			throw new RuntimeException();
		}
		for (c = 0; c <= index; c++) {
			n = n.next;
			if (n == trailer) {
				throw new RuntimeException();
			}
		}
		n.prev.next = n.next;
		n.next.prev = n.prev;
		n.next = null;
		n.data = null;
		n.prev = null;
		n = null;
		size--;
	}

	@Override
	/**
	 * @author Ahmed Ali
	 */
	public int size() {

		return size;
	}

	@Override
	/**
	 * @author Ahmed Ali
	 */
	public ILinkedList sublist(final int fromIndex, final int toIndex) {
		if (fromIndex < 0 || toIndex < 0 || fromIndex > toIndex) {
			throw new RuntimeException();
		}
		int i;
		DoublyNode n = head;
		DoublyLinkedList a = new DoublyLinkedList();
		for (i = 0; i <= fromIndex; i++) {
			n = n.next;
			if (n == trailer) {
				throw new RuntimeException();
			}
		}
		for (i = fromIndex; i <= toIndex; i++) {
			if (n == trailer) {
				throw new RuntimeException();
			}
			a.add(n.data);
			n = n.next;
		}
		return a;
	}

	@Override
	/**
	 * @author Ahmed Ali
	 */
	public boolean contains(final Object o) {
		if (o == null) {
			throw new RuntimeException();
		}
		int i;
		DoublyNode n = head.next;
		for (i = 0; i < size; i++) {
			if (n.data.equals(o)) {
				return true;
			}
			n = n.next;
		}

		return false;
	}

}

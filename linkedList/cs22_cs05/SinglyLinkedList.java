package eg.edu.alexu.csd.datastructure.linkedList.cs22_cs05;

import eg.edu.alexu.csd.datastructure.linkedList.ILinkedList;

/**
 *
 * @author Ahmed Ali
 *
 */
public class SinglyLinkedList implements ILinkedList {

	/**
	 * @author Ahmed Ali
	 */
	public SinglyNode head = new SinglyNode(null, null);
	/**
	 * @author Ahmed Ali
	 */
	public int size = 0;

	@Override
	/**
	 * @author Ahmed Ali
	 */
	public void add(final int index, final Object element) {
		int i;
		SinglyNode j, k;
		j = head;
		k = new SinglyNode(element, null);
		if (index < 0 || element == null) {
			throw new RuntimeException();
		}
		for (i = 0; i < index; i++) {
			j = j.next;
			if (j == null) {
				throw new RuntimeException();
			}

		}
		k.next = j.next;
		j.next = k;
		size++;
	}

	@Override
	/**
	 * @author Ahmed Ali
	 */
	public void add(final Object element) {
		if (element == null) {
			throw new RuntimeException();
		}
		SinglyNode i, j;
		i = head;
		j = new SinglyNode(element, null);
		while (i.next != null) {
			i = i.next;
		}
		i.next = j;
		size++;
	}

	@Override
	/**
	 * @author Ahmed Ali
	 */
	public Object get(final int index) {
		int c;
		SinglyNode i;
		i = head;
		if (index < 0) {
			throw new RuntimeException();
		}
		for (c = 0; c <= index; c++) {
			i = i.next;
			if (i == null) {
				throw new RuntimeException();
			}
		}
		return i.data;
	}

	@Override
	/**
	 * @author Ahmed Ali
	 */
	public void set(final int index, final Object element) {
		int c;
		SinglyNode i;
		i = head;
		if (index < 0 || element == null) {
			throw new RuntimeException();
		}
		for (c = 0; c <= index; c++) {
			i = i.next;
			if (i == null) {
				throw new RuntimeException();
			}

		}
		i.data = element;
	}

	@Override
	/**
	 * @author Ahmed Ali
	 */
	public void clear() {
		size = 0;
		head.next = null;
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
		SinglyNode i, prev;
		i = head;
		prev = head;
		if (index < 0) {
			throw new RuntimeException();
		}
		for (c = 0; c <= index; c++) {

			prev = i;
			i = i.next;
			if (i == null) {
				throw new RuntimeException();
			}
		}
		prev.next = i.next;
		i.next = null;
		i.data = null;
		i = null;
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
		SinglyNode c = head;
		SinglyLinkedList a = new SinglyLinkedList();
		for (i = 0; i <= fromIndex; i++) {
			c = c.next;
			if (c == null) {
				throw new RuntimeException();
			}

		}
		for (i = fromIndex; i <= toIndex; i++) {
			if (c == null) {
				throw new RuntimeException();
			}
			a.add(c.data);
			c = c.next;
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
		SinglyNode a = head.next;
		for (i = 0; i < size; i++) {
			if (a.data.equals(o)) {
				return true;
			}
			a = a.next;
		}
		return false;
	}

}

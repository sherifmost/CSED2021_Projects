package eg.edu.alexu.csd.datastructure.stack.cs05;

import eg.edu.alexu.csd.datastructure.stack.IStack;
import eg.edu.alexu.csd.datastructure.linkedList.cs22_cs05.SinglyLinkedList;

/**
 *
 * @author Ahmed Ali
 *
 */
public class MyStack implements IStack {
	/**
	 * @author Ahmed Ali
	 */
	public SinglyLinkedList stack = new SinglyLinkedList();

	@Override
	/**
	 * @author Ahmed Ali
	 */
	public Object pop() {
		if (stack.size == 0) {
			throw new RuntimeException();
		}
		Object element = stack.get(0);
		stack.remove(0);

		return element;
	}

	@Override
	/**
	 * @author Ahmed Ali
	 */
	public Object peek() {
		if (stack.isEmpty()) {
			throw new RuntimeException();
		}
		Object element = stack.get(0);
		return element;
	}

	@Override
	/**
	 * @author Ahmed Ali
	 */
	public void push(final Object element) {
		stack.add(0, element);
	}

	@Override
	/**
	 * @author Ahmed Ali
	 */
	public boolean isEmpty() {
		return stack.isEmpty();
	}

	@Override
	/**
	 * @author Ahmed Ali
	 */
	public int size() {
		return stack.size();
	}

}

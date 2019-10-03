package eg.edu.alexu.csd.datastructure.linkedList.cs22_cs05;

/**
 *
 * @author Ahmed Ali
 *
 */
public class DoublyNode {
	/**
	 * @author Ahmed Ali
	 */
	public Object data;
	/**
	 * @author Ahmed Ali
	 */
	public DoublyNode next, prev;

	/**
	 *
	 * @param data1
	 *            data1
	 * @param next1
	 *            next1
	 * @param prev1
	 *            prev1
	 */
	public DoublyNode(final Object data1,
			final DoublyNode next1, final DoublyNode prev1) {

		this.data = data1;
		this.next = next1;
		this.prev = prev1;
	}

}

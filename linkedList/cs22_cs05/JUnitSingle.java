package eg.edu.alexu.csd.datastructure.linkedList.cs22_cs05;

import static org.junit.Assert.fail;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertEquals;

import org.junit.Test;

import eg.edu.alexu.csd.datastructure.linkedList.ILinkedList;
/**
 *
 * @author Ahmed Ali
 *
 */
public class JUnitSingle {
	/**
	 * @author Ahmed Ali
	 */
	SinglyLinkedList list = new SinglyLinkedList();
	/**
	 * @author Ahmed Ali
	 */
	public static final int THREE = 3;
	/**
	 * @author Ahmed Ali
	 */
	public static final int SEVEN = 7;
	/**
	 * a given test.
	 */
	@Test
	public void cs65TestSet() {
		ILinkedList instance = (ILinkedList) list;
		instance.add(1);
		instance.add(THREE);
		instance.add(THREE + 2);
		instance.set(1, 'F');
		assertEquals('F', instance.get(1));
	}
	/**
	 * a given test.
	 */
	@Test
	public void cs65TestSetError() {
		ILinkedList instance = (ILinkedList) list;
		try {
			instance.add(1);
			instance.add(2);
			instance.set(THREE + 1, 'F');
			fail("You should throw an exception "
					+ "when trying to "
					+ "set in a wrong index");
		} catch (RuntimeException f) {
			throw new RuntimeException();
		}
	}
	/**
	 * a given test.
	 */
	@Test
	public void cs65TestSubList() {
		ILinkedList instance = (ILinkedList) list;
		instance.add('a');
		instance.add('b');
		instance.add('c');
		instance.add('d');
		ILinkedList temp = instance.sublist(1, 2);
		assertEquals(temp.get(0), instance.get(1));
		assertEquals(temp.get(1), instance.get(2));

	}
	/**
	 * a given test.
	 */
	@Test
	public void cs56TestContains() {
		ILinkedList c = (ILinkedList) list;
		for (int i = 0; i < THREE; i++) {
			c.add(i);
		}
		c.add(0, THREE);
		c.add(THREE + 1, THREE + 1);

		assertTrue(c.contains(THREE + 1));
		assertTrue(c.contains(0));
		assertTrue(c.contains(1));
		assertTrue(c.contains(2));
		assertTrue(c.contains(THREE));
		assertFalse(c.contains(SEVEN + 2));
		assertFalse(c.contains(SEVEN));
	}
	/**
	 * a given test.
	 */
	@Test
	public void cs56TestAddRemoveTwoLists5() {
		ILinkedList c = (ILinkedList) list;
		for (int i = 0; i < THREE; i++) {
			c.add(i);
		}
		c.add(0, THREE);
		c.add(THREE + 1, THREE + 1);
		c.set(1, SEVEN);
		c.set(THREE + 1, SEVEN + 2);
		ILinkedList d = (ILinkedList) list;
		d.add(THREE);
		d.add(SEVEN);
		d.add(1);
		d.add(2);
		d.add(SEVEN + 2);
		for (int i = 0; i < THREE + 2; i++) {
			assertEquals(c.get(i), d.get(i));
		}
	}
	/**
	 * a given test.
	 */
	@Test
	public void cs56TestRemoveContains() {
		ILinkedList c = (ILinkedList) list;
		for (int i = 0; i < THREE; i++) {
			c.add(i);
		}
		c.add(0, THREE);
		c.add(THREE + 1, THREE + 1);
		c.add(1);
		c.add(THREE + 2, SEVEN + 2);
		c.remove(THREE + 2);
		assertTrue(c.contains(THREE + 1));
		assertTrue(c.contains(0));
		assertTrue(c.contains(1));
		assertTrue(c.contains(2));
		assertTrue(c.contains(THREE));
		assertFalse(c.contains(SEVEN + 2));
		assertFalse(c.contains(SEVEN));
	}
	/**
	 * a given test.
	 */
	@Test
	public void cs56TestClear() {
		ILinkedList c = (ILinkedList) list;
		for (int i = 0; i < THREE; i++) {
			c.add(i);
		}
		c.add(0, THREE);
		c.add(THREE + 1, THREE + 1);
		c.clear();

		assertFalse(c.contains(THREE + 1));
		assertFalse(c.contains(0));
		assertFalse(c.contains(1));
		assertFalse(c.contains(2));
		assertFalse(c.contains(THREE));
		assertFalse(c.contains(SEVEN + 2));
		assertFalse(c.contains(SEVEN));
	}

}

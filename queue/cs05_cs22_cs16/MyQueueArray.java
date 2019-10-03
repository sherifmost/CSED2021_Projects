package eg.edu.alexu.csd.datastructure.queue.cs05_cs22_cs16;

import eg.edu.alexu.csd.datastructure.queue.IArrayBased;
import eg.edu.alexu.csd.datastructure.queue.IQueue;

/**
 * @author HP
 */
public class MyQueueArray implements IQueue, IArrayBased {
	/**
	 * @
	 */
	int f = 0, r = 0;
	/**
	 * @
	 */
     int n = 0;
     /**
      * @
      */
     Object [] items;
    /**
     * @
     */
    public MyQueueArray() { }
    /**
     * @param maximum maximum
     */
    public MyQueueArray(final int maximum) {
    	n = maximum;
    	items = new Object [n];
    	f = 0;
    	r = 0;
    }

	@Override
	public void enqueue(final Object item) {
		if (size() == n) {
			throw new RuntimeException();
		}
		items [(int) r] = item;
		r = (r + 1) % n;
	}

	@Override
	public Object dequeue() {
		if (isEmpty()) {
			throw new RuntimeException();
		}
		Object t = items [f];
		items [f] = null;
		f = (f + 1) % n;
		return t;
	}

	@Override
	public boolean isEmpty() {
		if (size() == 0) {
			return true;
		}
		return false;
	}

	@Override
	public int size() {
		if (f == r && items [f] != null) {
			return ((n - f + r) % (n + 1));
		}
		return ((n - f + r) % n);
	}

}

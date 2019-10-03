package eg.edu.alexu.csd.filestructure.redblacktree.cs05_cs20;

import java.util.Map;

public class MyEntry<K, V> implements Map.Entry<K, V> {// a class implementing the entry object
	private final K key;
	private V value;

	public MyEntry(K key, V value) {
		this.key = key;
		this.value = value;
	}

	@Override
	public K getKey() {
		return key;
	}

	@Override
	public V getValue() {
		return value;
	}

	@Override
	public V setValue(V arg0) {
		V oldValue = this.value;
		this.value = arg0;
		return oldValue;
	}
	@Override
	public String toString() {
		return key+"="+value;
	}

}

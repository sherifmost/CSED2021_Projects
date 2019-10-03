package eg.edu.alexu.csd.filestructure.redblacktree.cs05_cs20;

import eg.edu.alexu.csd.filestructure.redblacktree.INode;

public class RbNode<T extends Comparable<T>, V> implements INode<T, V> {
	static final boolean RED   = true;
    static final boolean BLACK = false;
    private T key;
    private V value;
    private INode<T,V> parent;
    private INode<T,V> leftChild;
    private INode<T,V> rightChild;
    private boolean color;
    public RbNode() {
    	
    }
    public RbNode(T key,V value) {
    	setKey(key);
    	setValue(value);
    	setColor(RED);
    	setLeftChild(null);
    	setRightChild(null);
    }
    
	@Override
	public void setParent(INode<T, V> parent) {
		this.parent = parent;
	}

	@Override
	public INode<T, V> getParent() {
		return parent;
	}

	@Override
	public void setLeftChild(INode<T, V> leftChild) {
		this.leftChild = leftChild;
	}

	@Override
	public INode<T, V> getLeftChild() {
		return leftChild;
	}

	@Override
	public void setRightChild(INode<T, V> rightChild) {
		this.rightChild = rightChild;
	}

	@Override
	public INode<T, V> getRightChild() {
		return rightChild;
	}

	@Override
	public T getKey() {
		return key;
	}

	@Override
	public void setKey(T key) {
		this.key = key;
	}

	@Override
	public V getValue() {
		return value;
	}

	@Override
	public void setValue(V value) {
		this.value = value;
	}

	@Override
	public boolean getColor() {
		return color;
	}

	@Override
	public void setColor(boolean color) {
		this.color = color;
	}

	@Override
	public boolean isNull() {
		if (key == null && value == null)
			return true;
		return false;
	}
	
}

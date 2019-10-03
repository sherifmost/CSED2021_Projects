package eg.edu.alexu.csd.filestructure.redblacktree.cs05_cs20;

import javax.management.RuntimeErrorException;
import eg.edu.alexu.csd.filestructure.redblacktree.INode;
import eg.edu.alexu.csd.filestructure.redblacktree.IRedBlackTree;

public class MyRedBlackTree<T extends Comparable<T>, V> implements IRedBlackTree<T, V> {

	private int size;
	private INode<T, V> root;
	public INode<T, V> nil;

	public MyRedBlackTree() {// initialize the tree and create the T.Nil.
		nil = new RbNode<T, V>(null, null);
		nil.setColor(RbNode.BLACK);
		root = nil;
		setSize(0);
	}

	@Override
	public INode<T, V> getRoot() {
		return root;
	}

	@Override
	public boolean isEmpty() {
		return size == 0;
	}

	@Override
	public void clear() {// simply let the root be T.Nil.
		size = 0;
		this.root = this.nil;
	}

	@Override
	public V search(T key) {
		if (key == null)
			throw new RuntimeErrorException(null);
		return getNode(key).getValue();
	}

	@Override
	public boolean contains(T key) {
		if (key == null)
			throw new RuntimeErrorException(null);
		return getNode(key) != this.nil;
	}

	public INode<T, V> getNode(T key) {// get the node given its key.
		if (key == null)
			throw new RuntimeErrorException(null);
		INode<T, V> node;
		if (root != nil)
			node = getRoot();
		else
			return nil;
		while (node != nil) {
			if (key.compareTo(node.getKey()) < 0)
				node = node.getLeftChild();
			else if (key.compareTo(node.getKey()) > 0)
				node = node.getRightChild();
			else
				return node;
		}
		return nil;
	}

	@Override
	public void insert(T key, V value) {
		if (key == null || value == null)
			throw new RuntimeErrorException(null);
		INode<T, V> node = getNode(key);
		if (node != this.nil) {// the key is found so only update its value.
			node.setValue(value);
		} else {// the key is not found so we need to insert a new node.
			size++;
			INode<T, V> inserted = new RbNode<T, V>(key, value);
			INode<T, V> location = this.root;
			INode<T, V> parent = this.nil;
			while (location != this.nil) {// get the correct location to insert the new node (finding its parent).
				parent = location;// is going to be the parent.
				if (key.compareTo(location.getKey()) < 0)
					location = location.getLeftChild();
				else
					location = location.getRightChild();
			}
			inserted.setParent(parent);
			if (parent == this.nil)
				setRoot(inserted);// first node to be inserted.
			else if (key.compareTo(parent.getKey()) < 0)// left child
				parent.setLeftChild(inserted);
			else// right child
				parent.setRightChild(inserted);
			// set the children of the inserted node to be nil
			inserted.setLeftChild(nil);
			inserted.setRightChild(nil);
			insertFixup(inserted);// make sure that the red black tree properties are not violated.
		}
	}

	private void insertFixup(INode<T, V> node) {
		INode<T, V> grand;// the grandparent.
		INode<T, V> uncle;// the uncle.
		while (node.getParent().getColor() == RbNode.RED) {
			grand = node.getParent().getParent();
			if (node.getParent() == grand.getLeftChild()) {
				uncle = grand.getRightChild(); // uncle is the grandparent's right child.
				if (uncle != this.nil && uncle.getColor() == RbNode.RED) {// case 1 uncle is red, re color.
					node.getParent().setColor(RbNode.BLACK);
					uncle.setColor(RbNode.BLACK);
					grand.setColor(RbNode.RED);
					node = grand;
				}

				else {
					if (node == node.getParent().getRightChild()) {// case 2:the node is the right child and the parent
																	// is the left child so change to case 3.
						node = node.getParent();
						leftRotate(node);
					}
					node.getParent().setColor(RbNode.BLACK);// case 3.
					grand.setColor(RbNode.RED);
					rightRotate(grand);
				}
			} else {
				uncle = grand.getLeftChild(); // uncle is grandparent's left child.

				if (uncle != this.nil && uncle.getColor() == RbNode.RED) {// case 1 uncle is red, re color.
					node.getParent().setColor(RbNode.BLACK);
					uncle.setColor(RbNode.BLACK);
					grand.setColor(RbNode.RED);
					node = grand;
				}

				else {
					if (node == node.getParent().getLeftChild()) {// case 2: the node is the left child and the parent
																	// is the right child so change to case 3.
						node = node.getParent();
						rightRotate(node);
					}
					node.getParent().setColor(RbNode.BLACK);// case 3.
					grand.setColor(RbNode.RED);
					leftRotate(grand);
				}
			}
		}
		root.setColor(RbNode.BLACK);// make sure that the root is black
	}

	private void leftRotate(INode<T, V> x) {// rotate the subtree rooted at x anti clockwise.
		INode<T, V> y = x.getRightChild();
		x.setRightChild(y.getLeftChild());
		if (y.getLeftChild() != this.nil)
			y.getLeftChild().setParent(x);
		y.setParent(x.getParent());
		if (x.getParent() == this.nil)
			setRoot(y);
		else if (x == x.getParent().getLeftChild())
			x.getParent().setLeftChild(y);
		else
			x.getParent().setRightChild(y);
		y.setLeftChild(x);
		x.setParent(y);
	}

	private void rightRotate(INode<T, V> x) {// rotate the subtree rooted at x clockwise.
		INode<T, V> y = x.getLeftChild();
		x.setLeftChild(y.getRightChild());
		if (y.getRightChild() != this.nil)
			y.getRightChild().setParent(x);
		y.setParent(x.getParent());
		if (x.getParent() == this.nil)
			setRoot(y);
		else if (x == x.getParent().getLeftChild())
			x.getParent().setLeftChild(y);
		else
			x.getParent().setRightChild(y);
		y.setRightChild(x);
		x.setParent(y);
	}

	private void setRoot(INode<T, V> newRoot) {// only sets the root and its parent without changing its children (to be
		// assigned outside this method)
		this.root = newRoot;
		this.root.setColor(RbNode.BLACK);
		this.root.setParent(nil);
	}

	@Override
	public boolean delete(T key) {
		if (key == null) {
			throw new RuntimeErrorException(null);
		}
		INode<T, V> foundNode = getNode(key);// we search for the node by its given key.
		if (foundNode == nil) {// node wasn't found in the tree.
			return false;
		}
		INode<T, V> replacingNode = foundNode;// node to replace the original node to be deleted.
		INode<T, V> fixNode = new RbNode<T, V>();// node to be really deleted that undergoes deleteFixup.
		boolean originalColor = foundNode.getColor();
		if (foundNode.getLeftChild() == nil) {// only has right child or no children at all so just replace with its
												// right child.
			fixNode = foundNode.getRightChild();
			transplant(foundNode, foundNode.getRightChild());
		} else if (foundNode.getRightChild() == nil) {// only has the left child so replace with its left child.
			fixNode = foundNode.getLeftChild();
			transplant(foundNode, foundNode.getLeftChild());
		} else {// has two children so we get the successor replace with it and now we have to
				// delete the successor.
			replacingNode = successor(foundNode);
			originalColor = replacingNode.getColor();
			fixNode = replacingNode.getRightChild();// the successor is the minimum in the right subtree so it has no
													// left children.
			if (replacingNode.getParent() == foundNode) {// the successor was the direct child.
				fixNode.setParent(replacingNode);
			} else {
				transplant(replacingNode, replacingNode.getRightChild());
				replacingNode.setRightChild(foundNode.getRightChild());
				replacingNode.getRightChild().setParent(replacingNode);
			} // exchange the replacing node with the original found node to be deleted.
			transplant(foundNode, replacingNode);
			replacingNode.setLeftChild(foundNode.getLeftChild());
			replacingNode.getLeftChild().setParent(replacingNode);
			replacingNode.setColor(foundNode.getColor());
		}
		if (originalColor == INode.BLACK) {// here the red black tree properties may be violated as the black height
											// etc....
			deleteFixUp(fixNode);
		}
		size--;
		return true;

	}

	private void transplant(INode<T, V> oldNode, INode<T, V> newNode) {// replace the old node with the new node without
		// setting its children (to be assigned from
		// outside this method).
		if (oldNode.getParent() == nil)
			setRoot(newNode);
		else if (oldNode == oldNode.getParent().getLeftChild())
			oldNode.getParent().setLeftChild(newNode);
		else
			oldNode.getParent().setRightChild(newNode);
		newNode.setParent(oldNode.getParent());
	}

	private void deleteFixUp(INode<T, V> fixNode) {// a function performing the fixUp operation to maintain the red
													// black tree properties after deletion handling all the possible
													// cases
		INode<T, V> sibling;// the sibling of the node that requires fixing.
		while (fixNode != getRoot() && fixNode.getColor() == INode.BLACK) {// we terminate when we reach a red node or
																			// the root of the tree because they don't
																			// require fixing.
			if (fixNode == fixNode.getParent().getLeftChild()) {// the node is the left child
				sibling = fixNode.getParent().getRightChild();
				if (sibling.getColor() == INode.RED) {// case1 : sibling is red so both its children are black: re color
														// and rotate to change to one of cases 2,3,4.
					sibling.setColor(INode.BLACK);// parent's color must be black
					fixNode.getParent().setColor(INode.RED);
					leftRotate(fixNode.getParent());
					sibling = fixNode.getParent().getRightChild();
				}

				if (sibling.getLeftChild().getColor() == INode.BLACK
						&& sibling.getRightChild().getColor() == INode.BLACK) {// case2 : the sibling and both its
																				// children are black: re color and move
																				// the problem to parent of the node so
																				// the loop continues again.
					sibling.setColor(INode.RED);// we remove one black from the node and its sibling.
					fixNode = fixNode.getParent();
				}

				else {// case3: sibling and its right child are black: perform re coloring and
						// rotation
						// to transform to case4.
					if (sibling.getRightChild().getColor() == INode.BLACK) {
						sibling.getLeftChild().setColor(INode.BLACK);
						sibling.setColor(INode.RED);
						rightRotate(sibling);
						sibling = fixNode.getParent().getRightChild();
					} // case4: sibling is black with a red right child: we perform some re coloring
						// and rotation that solves the black height problem by equating the number of
						// black nodes on both sides leaving the node singly black and setting it to be
						// the root for termination.
					sibling.setColor(fixNode.getParent().getColor());
					fixNode.getParent().setColor(INode.BLACK);
					sibling.getRightChild().setColor(INode.BLACK);
					leftRotate(fixNode.getParent());
					fixNode = getRoot();
				}

			} else {// same as previous but with exchanging left and right as here the node is the
					// right child.
				sibling = fixNode.getParent().getLeftChild();
				if (sibling.getColor() == INode.RED) {
					sibling.setColor(INode.BLACK);
					fixNode.getParent().setColor(INode.RED);
					rightRotate(fixNode.getParent());
					sibling = fixNode.getParent().getLeftChild();
				}

				if (sibling.getLeftChild().getColor() == INode.BLACK
						&& sibling.getRightChild().getColor() == INode.BLACK) {
					sibling.setColor(INode.RED);
					fixNode = fixNode.getParent();
				}

				else {
					if (sibling.getLeftChild().getColor() == INode.BLACK) {
						sibling.getRightChild().setColor(INode.BLACK);
						sibling.setColor(INode.RED);
						leftRotate(sibling);
						sibling = fixNode.getParent().getLeftChild();
					}
					sibling.setColor(fixNode.getParent().getColor());
					fixNode.getParent().setColor(INode.BLACK);
					sibling.getLeftChild().setColor(INode.BLACK);
					rightRotate(fixNode.getParent());
					fixNode = getRoot();
				}

			}
		}
		fixNode.setColor(INode.BLACK);// for case 2 and 4 (terminating cases) to make sure that the root is black and
										// that when the problem goes to the parent the parent is black not red.
	}

	// Additional functions mostly for use in insert and delete and for use in
	// treeMap.
	public INode<T, V> successor(INode<T, V> x) {// find the successor element for x which is the smallest number bigger
													// than x
		if (size == 0)
			return nil;
		INode<T, V> y;
		if (x.getRightChild() != nil)
			return minimum(x.getRightChild());
		y = x.getParent();
		while (y != this.nil && x == y.getRightChild()) {
			x = y;
			y = x.getParent();
		}
		return y;
	}

	public INode<T, V> predecessor(INode<T, V> x) {// find the predecessor element for x which is the biggest number
													// smaller than x
		if (size == 0)
			return nil;
		INode<T, V> y;
		if (x.getLeftChild() != nil)
			return maximum(x.getLeftChild());
		y = x.getParent();
		while (y != this.nil && x == y.getLeftChild()) {
			x = y;
			y = x.getParent();
		}
		return y;
	}

	public INode<T, V> minimum(INode<T, V> x) {// find minimum number in the subtree rooted at x (x may be the root)
		if (size == 0)
			return nil;
		while (x.getLeftChild() != nil)
			x = x.getLeftChild();
		return x;
	}

	public INode<T, V> maximum(INode<T, V> x) {// find maximum number in the subtree rooted at x (x may be the root)
		if (size == 0)
			return nil;
		while (x.getRightChild() != nil)
			x = x.getRightChild();
		return x;
	}

	public int getSize() {
		return size;
	}

	public void setSize(int size) {
		this.size = size;
	}

}

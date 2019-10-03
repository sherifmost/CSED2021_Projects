package eg.edu.alexu.csd.datastructure.linkedList.cs22_cs05;

import eg.edu.alexu.csd.datastructure.linkedList.IPolynomialSolver;

/**
 *
 * @author Ahmed Ali
 *
 */
public class Polynomial implements IPolynomialSolver {

	/**
	 * @author Ahmed Ali
	 */
	DoublyLinkedList a = new DoublyLinkedList();
	/**
	 * @author Ahmed Ali
	 */
	DoublyLinkedList b = new DoublyLinkedList();
	/**
	 * @author Ahmed Ali
	 */
	DoublyLinkedList c = new DoublyLinkedList();
	/**
	 * @author Ahmed Ali
	 */
	DoublyLinkedList r = new DoublyLinkedList();

	/**
	 *
	 * @param array
	 * array
	 */
	private void sort(final int[][] array) {
		int i, j, sorted = 0, temp;
		for (i = 0; i < (array.length) && sorted == 0; i++) {
			sorted = 1;
			for (j = 0; j < array.length - i - 1; j++) {
				if (array[j][1] < array[j + 1][1]) {
					temp = array[j][1];
					array[j][1] = array[j + 1][1];
					array[j + 1][1] = temp;
					temp = array[j][0];
					array[j][0] = array[j + 1][0];
					array[j + 1][0] = temp;
					sorted = 0;
				}
			}

		}

	}

	/**
	 *
	 * @param poly
	 *            poly
	 * @return retrun
	 */
	private DoublyLinkedList getPolynomial(final char poly) {
		if (poly == 'A') {
			return a;
		}
		if (poly == 'B') {
			return b;
		}
		if (poly == 'C') {
			return c;
		}
		if (poly == 'R') {
			return r;
		}

		return null;

	}

	@Override
	/**
	 * @author Ahmed Ali
	 */
	public void setPolynomial(final char poly, final int[][] terms) {
		if (getPolynomial(poly) == null) {
			throw new RuntimeException();
		}
		sort(terms);
		clearPolynomial(poly);
		int i;
		for (i = 0; i < terms.length; i++) {
			Term x = new Term(0, 0);
			x.coef = terms[i][0];
			x.exp = terms[i][1];
			getPolynomial(poly).add(x);

		}
	}

	@Override
	/**
	 * @author Ahmed Ali
	 */
	public String print(final char poly) {
		if (getPolynomial(poly) == null) {
			return null;
		}

		DoublyLinkedList temp = getPolynomial(poly);
		String s = "";
		DoublyNode term = temp.head.next;
		Term x;
		if (temp.size == 0) {
			return null;
		}
		int i;
		for (i = 0; i < temp.size; i++) {
			x = (Term) term.data;
			if (x.coef != 0) {

				if (x.exp == 0) {
					if (x.coef < 0) {
						s += x.coef;
					} else {
						if (s != "") {
							s += "+" + x.coef;
						} else {
							s += x.coef;
						}
					}
				} else if (x.coef == 1) {
					if (s != "") {
						if (x.exp != 1) {
							s += "+x^" + x.exp;
						}
						if (x.exp == 1) {
							s += "+x";
						}
					} else {
						if (x.exp != 1) {
							s += "x^" + x.exp;
						}
						if (x.exp == 1) {
							s += "x";
						}
					}
				} else if (x.coef == -1) {
					if (x.exp != 1) {
						s += "-x^" + x.exp;
					} else {
						s += "-x";
					}
				} else if (x.coef > 0 && s != "") {
					if (x.exp != 1) {
						s += "+" + x.coef
								+ "x^" + x.exp;
					} else {
						s += "+" + x.coef + "x";
					}
				} else if (x.exp != 1) {
					s += x.coef + "x^" + x.exp;
				} else {
					s += x.coef + "x";
				}
			}
			term = term.next;
		}
		if (temp.size > 0 && s == "") {
			s = "0";
		}

		return s;
	}

	@Override
	/**
	 * @author Ahmed Ali
	 */
	public void clearPolynomial(final char poly) {
		if (getPolynomial(poly) == null) {
			throw new RuntimeException();
		}

		getPolynomial(poly).clear();
	}

	@Override
	/**
	 * @author Ahmed Ali
	 */
	public float evaluatePolynomial(final char poly, final float value) {
		if (getPolynomial(poly) == null) {
			throw new RuntimeException();
		}
		float result = 0;
		DoublyLinkedList temp = getPolynomial(poly);
		DoublyNode term;
		term = temp.head;
		Term x;
		if (temp.size == 0) {
			throw new RuntimeException();
		}
		int i;
		for (i = 0; i < temp.size; i++) {
			term = term.next;
			x = (Term) term.data;
			result += java.lang.Math.pow(value, x.exp) * x.coef;
		}

		return result;
	}

	@Override
	/**
	 * @author Ahmed Ali
	 */
	public int[][] add(final char poly1, final char poly2) {
		if (getPolynomial(poly1) == null
				|| getPolynomial(poly2) == null) {
			throw new RuntimeException();
		}
		int[][] add;
		r.clear();
		DoublyLinkedList temp1 = getPolynomial(poly1);
		DoublyLinkedList temp2 = getPolynomial(poly2);
		DoublyNode term1 = temp1.head;
		DoublyNode term2 = temp2.head;
		Term x1, x2, x3;
		if (temp1.size == 0 || temp2.size == 0) {
			throw new RuntimeException();
		}

		boolean added = false;
		while (term1.next.data != null) {
			added = false;
			term1 = term1.next;
			x1 = new Term(0, 0);
			x1 = (Term) term1.data;
			term2 = temp2.head;
			while (term2.next.data != null) {
				term2 = term2.next;
				x2 = new Term(0, 0);
				x2 = (Term) term2.data;
				if (x1.exp == x2.exp) {
					x3 = new Term(0, 0);
					x3.coef = x1.coef + x2.coef;
					x3.exp = x1.exp;
					r.add(x3);
					added = true;
				}
			}
			if (!added) {
				r.add(x1);
			}
		}
		term1 = new DoublyNode(null, null, null);
		term1 = temp1.head;
		term2 = new DoublyNode(null, null, null);
		term2 = temp2.head;
		while (term2.next.data != null) {
			added = false;
			term2 = term2.next;
			x2 = new Term(0, 0);
			x2 = (Term) term2.data;
			term1 = temp1.head;
			while (term1.next.data != null) {
				term1 = term1.next;
				x1 = new Term(0, 0);
				x1 = (Term) term1.data;
				if (x1.exp == x2.exp) {
					added = true;
				}
			}
			if (!added) {
				r.add(x2);
			}
		}
		add = new int[r.size][2];
		term1 = r.head;
		int i;
		for (i = 0; i < r.size; i++) {
			term1 = term1.next;
			x1 = new Term(0, 0);
			x1 = (Term) term1.data;
			add[i][0] = x1.coef;
			add[i][1] = x1.exp;
		}
		sort(add);
		int size = 0;
		for (i = 0; i < r.size; i++) {
			if (add[i][0] != 0) {
				size++;
			}
		}
		int[][] fadd = new int[size][2];
		for (i = 0; i < r.size; i++) {
			if (add[i][0] != 0) {
				fadd[i][0] = add[i][0];
				fadd[i][1] = add[i][1];
			}
		}
		int[][] zeroarray = new int[1][2];
		zeroarray[0][0] = 0;
		zeroarray[0][1] = 0;
		if (size == 0) {
			return zeroarray;
		}
		return fadd;
	}

	@Override
	/**
	 * @author Ahmed Ali
	 */
	public int[][] subtract(final char poly1, final char poly2) {
		if (getPolynomial(poly1) == null
				|| getPolynomial(poly2) == null) {
			throw new RuntimeException();
		}
		int[][] sub;
		r.clear();
		DoublyLinkedList temp1 = getPolynomial(poly1);
		DoublyLinkedList temp2 = getPolynomial(poly2);
		DoublyNode term1 = temp1.head;
		DoublyNode term2 = temp2.head;
		Term x1, x2, x3;
		if (temp1.size == 0 || temp2.size == 0) {
			throw new RuntimeException();
		}

		boolean added = false;
		while (term1.next.data != null) {
			added = false;
			term1 = term1.next;
			x1 = new Term(0, 0);
			x1 = (Term) term1.data;
			term2 = temp2.head;
			while (term2.next.data != null) {
				term2 = term2.next;
				x2 = new Term(0, 0);
				x2 = (Term) term2.data;
				if (x1.exp == x2.exp) {
					x3 = new Term(0, 0);
					x3.coef = x1.coef - x2.coef;
					x3.exp = x1.exp;
					r.add(x3);
					added = true;
				}
			}
			if (!added) {
				r.add(x1);
			}
		}
		term1 = new DoublyNode(null, null, null);
		term1 = temp1.head;
		term2 = new DoublyNode(null, null, null);
		term2 = temp2.head;
		while (term2.next.data != null) {
			added = false;
			term2 = term2.next;
			x2 = new Term(0, 0);
			x2 = (Term) term2.data;
			term1 = temp1.head;
			while (term1.next.data != null) {
				term1 = term1.next;
				x1 = new Term(0, 0);
				x1 = (Term) term1.data;
				if (x1.exp == x2.exp) {
					added = true;
				}
			}
			if (!added) {
				x3 = new Term(0, 0);
				x3.coef = -1 * x2.coef;
				x3.exp = x2.exp;
				r.add(x3);
			}
		}
		sub = new int[r.size][2];
		term1 = r.head;
		int i;
		for (i = 0; i < r.size; i++) {
			term1 = term1.next;
			x1 = new Term(0, 0);
			x1 = (Term) term1.data;
			sub[i][0] = x1.coef;
			sub[i][1] = x1.exp;
		}
		sort(sub);
		int size = 0;
		for (i = 0; i < r.size; i++) {
			if (sub[i][0] != 0) {
				size++;
			}
		}
		int[][] fsub = new int[size][2];
		for (i = 0; i < r.size; i++) {
			if (sub[i][0] != 0) {
				fsub[i][0] = sub[i][0];
				fsub[i][1] = sub[i][1];
			}
		}
		int[][] zeroarray = new int[1][2];
		zeroarray[0][0] = 0;
		zeroarray[0][1] = 0;
		if (size == 0) {
			return zeroarray;
		}
		return fsub;
	}

	@Override
	/**
	 * @author Ahmed Ali
	 */
	public int[][] multiply(final char poly1, final char poly2) {
		if (getPolynomial(poly1) == null
				|| getPolynomial(poly2) == null) {
			throw new RuntimeException();
		}
		r.clear();
		DoublyLinkedList temp1 = getPolynomial(poly1);
		DoublyLinkedList temp2 = getPolynomial(poly2);
		DoublyNode term1 = temp1.head;
		DoublyNode term2 = temp2.head;
		Term x1, x2, x3;
		if (temp1.size == 0 || temp2.size == 0) {
			throw new RuntimeException();
		}

		int i, j;
		while (term1.next.data != null) {
			term1 = term1.next;
			x1 = new Term(0, 0);
			x1 = (Term) term1.data;
			term2 = temp2.head;
			while (term2.next.data != null) {
				term2 = term2.next;
				x2 = new Term(0, 0);
				x2 = (Term) term2.data;
				x3 = new Term(0, 0);
				x3.coef = x2.coef * x1.coef;
				x3.exp = x1.exp + x2.exp;
				r.add(x3);
			}
		}
		term1 = r.head;
		j = 0;
		while (term1.next.data != null) {
			int c1 = -1;
			term1 = term1.next;
			j++;
			term2 = term1;
			while (term2.next.data != null) {

				c1++;
				x1 = new Term(0, 0);
				x1 = (Term) term1.data;
				x2 = new Term(0, 0);
				x2 = (Term) term2.next.data;
				if (x1.exp == x2.exp) {
					x1.coef += x2.coef;
					r.remove(c1 + j);

				}
				term2 = term2.next;
			}
		}
		int[][] mult = new int[r.size][2];
		term1 = r.head;
		for (i = 0; i < r.size; i++) {
			term1 = term1.next;
			x1 = (Term) term1.data;
			mult[i][0] = x1.coef;
			mult[i][1] = x1.exp;
		}
		sort(mult);
		int size = 0;
		for (i = 0; i < r.size; i++) {
			if (mult[i][0] != 0) {
				size++;
			}
		}
		int[][] fmult = new int[size][2];
		for (i = 0; i < r.size; i++) {
			if (mult[i][0] != 0) {
				fmult[i][0] = mult[i][0];
				fmult[i][1] = mult[i][1];
			}
		}
		int[][] zeroarray = new int[1][2];
		zeroarray[0][0] = 0;
		zeroarray[0][1] = 0;
		if (size == 0) {
			return zeroarray;
		}
		return fmult;
	}

}

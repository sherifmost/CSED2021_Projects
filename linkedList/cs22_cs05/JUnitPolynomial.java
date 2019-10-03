package eg.edu.alexu.csd.datastructure.linkedList.cs22_cs05;
import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import org.junit.Test;

import eg.edu.alexu.csd.datastructure.linkedList.IPolynomialSolver;
/**
 *
 * @author Ahmed Ali
 *
 */
public class JUnitPolynomial {
	/**
	 * @author Ahmed Ali
	 */
	Polynomial poly = new Polynomial();
	/**
	 * @author Lenovo
	 */
	final int three = 3;
	/**
	 * @author Lenovo
	 */
	  final int zero = 0;
	/**
	 * @author Lenovo
	 */
	 final int one = 1;
	/**
	 * @author Lenovo
	 */
      final int two = 2;
	/**
	 * @author Lenovo
	 */
	  final int five = 5;
	/**
	 * @author Lenovo
	 */
 final int seven = 7;
	/**
	 * @author Lenovo
	 */
	final int ts = 27;
	/**
	 * @author Lenovo
	 */
	  final int ten = 10;
	/**
	 * @author Lenovo
	 */
	  final int fifty = 50;
	/**
	 * @author Lenovo
	 */
   final int ff = 45;
	/**
	 * @author Lenovo
	 */
     final int seventy = 70;
	/**
	 * @author Lenovo
	 */
   final int sf = 75;
 	/**
	 * @author Lenovo
	 */
	 final int four = 4;
	/**
	 * @author Lenovo
	 */
	 final int eight = 8;
	/**
	 * @author Lenovo
	 */
 final int ote = 128;
	/**
	 * @author Lenovo
	 */
	final int ots = 127;
	/**
	 * @author Lenovo
	 */
	  final int sixty = 60;
	/**
	 * @author Lenovo
	 */
  final int otn = 129;
	/**
	 * @author Lenovo
	 */
	final int ot = 120;
	/**
	 * @author Lenovo
	 */
	 final int otsix = 126;
	/**
	 * @author Lenovo
	 */
 final int oth = 130;
	/**
	 * @author Lenovo
	 */
	  final int oss = 176;
	/**
	 * @author Lenovo
	 */
	  final int osv = 175;
	/**
	 * @author Lenovo
	 */
	  final int nsf = 165;
	/**
	 * @author Lenovo
	 */
 final int nss = 177;
	/**
	 * @author Lenovo
	 */
	  final int tsz = 360;
	/**
	 * @author Lenovo
	 */
	final int otwo = 12;
	/**
	 * @author Lenovo
	 */
	  final int ffzt = 5403;
	/**
	 * @author Lenovo
	 */
  final int eo = 81;
	/**
	 * @author Lenovo
	 */
	  final int nine = 9;
	/**
	 * @author Lenovo
	 */
 final int toost = 21162;
	/**
	 * @author Lenovo
	 */
	  final int otot = 1212;
	/**
	 * @author Lenovo
	 */
	  final int offno = 15491;
	/**
	 * @author Lenovo
	 */
		 final int six = 6;
	/**
	 * @author Lenovo
	 */
  final int fszs = 4704;
	/**
	 * @author Lenovo
	 */
	final int fe = 48;
	/**
	 * @author Lenovo
	 */
	  final int ttez = 3280;
	/**
	 * @author Lenovo
	 */
	  final int st = 62;
	/**
	 * @author Lenovo
	 */
	  final int so = 60;
	/**
	 * @author Lenovo
	 */
	  final int ttsix = 226;
	/**
	 * @author Lenovo
	 */
	  final int sone = 61;
	/**
	 * a given test.
	 */
	@Test
	public void testSolveAdd() {
		IPolynomialSolver instance = (IPolynomialSolver) poly;
		instance.setPolynomial('C', new int[][] {{three, seven},
			{ff, five}, {oss, three}, {ote, one}});
		instance.setPolynomial('B', new int[][] {{-ot, five},
			{-one, three}, {ts, two}, {one, one}, {-one, zero}});
		assertNull("Polynomial R is not set yet", instance.print('R'));
		int[][] result1 = instance.add('B', 'C');
		assertNotNull("Polynomial R must be "
				+ "set after evaluation", instance.print('R'));
		int[][] expected = new int[][] {{three, seven},
			{-sf, five}, {osv, three},
			{ts, two}, {otn, one}, {-one, zero}};
		assertArrayEquals(expected, result1);
	}
	/**
	 * a given test.
	 */
	@Test
	public void testSolveSubtract() {
		IPolynomialSolver instance = (IPolynomialSolver) poly;
		instance.setPolynomial('C', new int[][] {{three, seven},
			{ff, five}, {oss, three}, {ote, one}});
		instance.setPolynomial('B', new int[][] {{-ot, five},
			{-one, three}, {ts, two}, {one, one}, {-one, zero}});
		assertNull("Polynomial R is not set yet", instance.print('R'));
		int[][] result1 = instance.subtract('B', 'C');
		assertNotNull("Polynomial R must be "
				+ "set after evaluation", instance.print('R'));
		int[][] expected = new int[][] {{-three, seven},
			{-nsf, five}, {-nss, three},
			{ts, two}, {-ots, one}, {-one, zero}};
		assertArrayEquals(expected, result1);

	}
	/**
	 * a given test.
	 */
	@Test
	public void testSolveMultiply() {
		IPolynomialSolver instance = (IPolynomialSolver) poly;
		instance.setPolynomial('C', new int[][] {{three, seven},
			{ff, five}, {oss, three}, {ote, one}});
		instance.setPolynomial('B', new int[][] {{-ot, five},
			{-one, three}, {ts, two}, {one, one}, {-one, zero}});
		assertNull("Polynomial R is not set yet", instance.print('R'));
		int[][] result1 = instance.multiply('B', 'C');
		assertNotNull("Polynomial R must be "
				+ "set after evaluation", instance.print('R'));
		int[][] expected = new int[][] {{-tsz, otwo},
			{-ffzt, ten}, {eo, nine},
			{-toost, eight}, {otot, seven},
				{-offno, six}, {fszs, five}, {fe, four},
				{ttez, three}, {ote, two}, {-ote, one}};
		assertArrayEquals(expected, result1);

	}
	/**
	 * a given test.
	 */
	@Test
	public void testSolveAdd2() {
		IPolynomialSolver instance = (IPolynomialSolver) poly;
		instance.setPolynomial('C', new int[][] {{seventy, seven},
			{ff, five}, {oss, three}, {ote, one}, {sone, zero}});
		instance.setPolynomial('B', new int[][] {{-ot, five},
			{-fifty, three}, {ts, two}, {two, one},
			{-one, zero}, {zero, ten}});
		assertNull("Polynomial R is not set yet", instance.print('R'));
		int[][] result1 = instance.add('B', 'C');
		assertNotNull("Polynomial R must be "
				+ "set after evaluation", instance.print('R'));
		int[][] expected = new int[][] {{zero, ten}, {seventy, seven},
			{-sf, five}, {otsix, three}, {ts, two}, {oth, one},
				{so, zero}};
		assertArrayEquals(expected, result1);

	}
	/**
	 * a given test.
	 */
	@Test
	public void testSolveSubtract2() {
		IPolynomialSolver instance = (IPolynomialSolver) poly;
		instance.setPolynomial('C', new int[][] {{seventy, seven},
			{ff, five}, {oss, three}, {ote, one}, {sone, zero}});
		instance.setPolynomial('B', new int[][] {{-ot, five},
			{-fifty, three}, {ts, two}, {two, one},
			{-one, zero}, {zero, ten}});
		assertNull("Polynomial R is not set yet", instance.print('R'));
		int[][] result1 = instance.subtract('B', 'C');
		assertNotNull("Polynomial R must be "
				+ "set after evaluation", instance.print('R'));
		int[][] expected = new int[][] {{zero, ten},
			{-seventy, seven}, {-nsf, five},
			{-ttsix, three}, {ts, two}, {-otsix, one},
				{-st, zero}};
		assertArrayEquals(expected, result1);
	}
}

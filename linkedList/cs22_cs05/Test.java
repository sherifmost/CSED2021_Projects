package eg.edu.alexu.csd.datastructure.linkedList.cs22_cs05;
/**
 *
 * @author Ahmed Ali
 *
 */
public class Test {
	/**
	 * @author Ahmed Ali
	 */
	public static final int FIVE = 5;
	/**
	 * @author Ahmed Ali
	 */
	public static final int OSS = 176;
	/**
	 * @author Ahmed Ali
	 */
	public static final int OTE = 128;
	/**
	 * @author Ahmed Ali
	 */
	public static final int SO = 61;
	/**
	 * @author Ahmed Ali
	 */
	public static final int TS = 27;
	/**
	 *
	 * @param args
	 * array
	 */
	public static void main(final String[] args) {

		Polynomial poly = new Polynomial();
		int[][] terms = {{0, FIVE + 2}, {0, FIVE},
				{OSS, FIVE - 2}, {OTE, FIVE - 2}, {SO, 0}};
		int[][] terms2 = {{0, FIVE + 2},
				{(0 - 2) * FIVE * FIVE, FIVE - 2},
				{TS, 2}, {2, 1},
				{-1, 0}, {0, 2 * FIVE}};
		poly.setPolynomial('A', terms);
		poly.setPolynomial('B', terms2);
		poly.setPolynomial('R', poly.add('A', 'B'));
		System.out.println(poly.print('A'));
		for (int i = 0; i < poly.add('A', 'B').length; i++) {
			System.out.println(poly.add('A', 'B')[i][0]
					+ "  " + poly.add('A', 'B')[i][1]);

		}
		Integer[] arr = new Integer[2 * FIVE];
		arr[0] = FIVE - 2;
		arr[1] = 2;
		int n = 0;
		for (int i = 0; i < arr.length; i++) {
			if (arr[i] != null) {
				n++;
			}
		}
		System.out.println(n);
	}
}

package eg.edu.alexu.csd.datastructure.iceHockey.cs05;

import java.awt.Point;
import java.util.ArrayList;
import eg.edu.alexu.csd.datastructure.iceHockey.IPlayersFinder;

/**
 *
 * @author Ahmed Ali
 *
 */
public class Icehockey implements IPlayersFinder {
	/**
	 * @author Ahmed Ali
	 */
	int counter = 0;
	/**
	 * @author Ahmed Ali
	 */
	boolean[][] visited;
	/**
	 * @author Ahmed Ali
	 */
	char[][] photos;
	/**
	 * @author Ahmed Ali
	 */
	int minr, minc, maxr, maxc;
	/**
	 * @author Ahmed Ali
	 */
	int r, c;
	/**
	 * @author Ahmed Ali
	 */
	public static final int CHECKCHAR = 48;
	/**
	 * @author Ahmed Ali
	 */
	public static final int FOUR = 4;
	/**
	 *
	 * @param image some text
	 */
	private void matRix(final String[] image) {
		int row, col;
		photos = new char[image.length][image[0].length()];
		r = image.length;
		c = image[0].length();
		visited = new boolean[image.length][image[0].length()];
		for (row = 0; row < image.length; row++) {
			for (col = 0; col < image[0].length(); col++) {
				photos[row][col] = image[row].charAt(col);
			}
		}
	}

	/**
	 *
	 * @param number some text
	 * @param row some text
	 * @param col some text
	 */
	private void dfs(final int number, final int row, final int col) {
		if (photos[row][col] - CHECKCHAR == number
				&& !visited[row][col]) {
			visited[row][col] = true;
			counter += FOUR;
			if (row < minr) {
				minr = row;
			}
			if (col < minc) {
				minc = col;
			}
			if (row > maxr) {
				maxr = row;
			}
			if (col > maxc) {
				maxc = col;
			}
			if (row > 0) {
				dfs(number, row - 1, col);
			}
			if (row < r - 1) {
				dfs(number, row + 1, col);
			}
			if (col > 0) {
				dfs(number, row, col - 1);
			}
			if (col < c - 1) {
				dfs(number, row, col + 1);
			}
		}
	}

	/**
	 *
	 * @return some text
	 */
	private Point getCenter() {
		int x, y;
		y = minr + maxr + 1;
		x = minc + maxc + 1;
		return new Point(x, y);
	}

	@Override
	public Point[] findPlayers(final String[] photo,
			final int team, final int threshold) {
		// TODO Auto-generated method stub
		int i, j;
		if (photo == null) {
			return null;
		}
		ArrayList<Point> points = new ArrayList<Point>();
		matRix(photo);
		for (i = 0; i < r; i++) {
			for (j = 0; j < c; j++) {
				minr = i;
				maxr = i;
				minc = j;
				maxc = j;
				dfs(team, i, j);
				if (counter >= threshold) {
					points.add(getCenter());
				}
				counter = 0;
			}
		}
		Point[] center = new Point[points.size()];
		center = points.toArray(center);
		Point temp = new Point();
		for (i = 0; i < center.length - 1; i++) {
			for (j = 1; j < center.length - i; j++) {
				if (center[j - 1].x > center[j].x) {
					temp = center[j - 1];
					center[j - 1] = center[j];
					center[j] = temp;
				}
			}
		}
		return center;
	}
}

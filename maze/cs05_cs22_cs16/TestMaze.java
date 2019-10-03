package eg.edu.alexu.csd.datastructure.maze.cs05_cs22_cs16;


import java.io.File;

import java.io.IOException;

/**
 *
 * @author Lenovo
 *
 */
public class TestMaze {

	/**
	 *
	 * @param args args
	 * @throws IOException Exception
	 */
	public static void main(final String[] args)
			throws IOException {
		/**
		 * cajsb.
		 */
		MyMazeSolver x
		= new MyMazeSolver();
		File m = new File("formaze.txt");
		x.solveBFS(m);
		//x.solveDFS(m);
		/*int i = 0,n =0,m=0;char [][]game = null;
		int j =0;
		BufferedReader br
		 = new
		  BufferedReader(new FileReader("formaze.txt"));
		String a = "";
		while((a = br.readLine()) != null) {
			if (i == 0) {
				String [] b = a.split(" ");
				 n =Integer.parseInt(b[0]);
				 m =Integer.parseInt(b[1]);
				 System.out.println(n);
				 System.out.println(m);
				 game = new char [n][m];
			} else {
				for (int c =0;c<m;c++) {
					game[j][c] = a.charAt(c);
				}
				j++;
			}
			i++;
		}
		br.close();
		for(int g =0 ;g<game.length;g++ ) {
			for(int d =0; d<game[g].length;d++) {
				System.out.print(game[g][d]);
			}
			System.out.println();
		}*/
	}

}

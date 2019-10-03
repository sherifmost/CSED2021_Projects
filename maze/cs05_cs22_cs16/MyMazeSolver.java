package eg.edu.alexu.csd.datastructure.maze.cs05_cs22_cs16;

import java.awt.Point;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;


import eg.edu.alexu.csd.datastructure.linkedList.cs22_cs05.SinglyLinkedList;
import eg.edu.alexu.csd.datastructure.maze.IMazeSolver;
import eg.edu.alexu.csd.datastructure.queue.cs05_cs22_cs16.MyQueue;
import eg.edu.alexu.csd.datastructure.stack.cs05.MyStack;

/**
 * @author HP
 */
public class MyMazeSolver implements IMazeSolver {

	@Override
	public int[][] solveBFS(final File maze) {
		int n = 0, m = 0;
		int is = 0, js = 0;
		boolean hasstart = false;
		boolean hasend = false;
		boolean getend = false;
		SinglyLinkedList allpoints = new SinglyLinkedList();
		Point coo = new Point(0, 0);
		char[][] map = null;
		boolean[][] visited = null;
		MyQueue store = new MyQueue();
		try {
			int countlines = 0;
		   BufferedReader br
	  = new BufferedReader(new FileReader(maze));
			String read = "";
			int i = 0;
			while ((read = br.readLine()) != null) {
				if (countlines == 0) {
					String[] size = read.split(" ");
					n = Integer.parseInt(size[0]);
					m = Integer.parseInt(size[1]);
					map = new char[n][m];
					visited = new boolean[n][m];
				} else {
					char[] mapline = read.toCharArray();
					for (int j = 0; j < m; j++) {
						if (mapline[j] == 'S') {
							is = i;
							js = j;
							hasstart = true;
						}
						if (mapline[j] == 'E') {
							hasend = true;
						}
						map[i][j] = mapline[j];
					}
					i++;
				}
				countlines++;
			}
			br.close();

		} catch (IOException e) {
			e.printStackTrace();
		}
		if (!hasend || !hasstart) {
			throw new RuntimeException();
		}
		coo.x = is;
		coo.y = js;
		store.enqueue((Point) coo);
		visited[coo.x][coo.y] = true;
		while (!store.isEmpty()) {
			coo = (Point) store.dequeue();
			if (map[coo.x][coo.y] == 'E') {
				getend = true;
		Point temp
		= new Point(coo.x, coo.y);
				allpoints.add((Point) temp);
				break;
			}
			allpoints.add((Point) coo);
			if (coo.y - 1 >= 0) {
				if ((map[coo.x][coo.y - 1] == '.'
				|| map[coo.x][coo.y - 1] == 'E')
		 && (!visited[coo.x][coo.y - 1])) {
			Point neighbour
			= new Point(0, 0);
neighbour.x = coo.x; neighbour.y = coo.y - 1;
		visited[neighbour.x][neighbour.y] = true;
				store.enqueue(neighbour);
				}
			}
			 if (coo.x + 1 < map.length) {
					if ((map[coo.x + 1][coo.y] == '.'
						|| map[coo.x + 1][coo.y] == 'E')
				&& (!visited[coo.x + 1][coo.y])) {
				Point neighbour
				= new Point(0, 0);
	neighbour.x = coo.x + 1; neighbour.y = coo.y;
			visited[neighbour.x][neighbour.y] = true;
						store.enqueue(neighbour);
					}
				}
			 if (coo.x - 1 >= 0) {
					if ((map[coo.x - 1][coo.y] == '.'
		           || map[coo.x - 1][coo.y] == 'E')
			&& (!visited[coo.x - 1][coo.y])) {
				Point neighbour
				= new Point(0, 0);
		neighbour.x = coo.x - 1; neighbour.y = coo.y;
			visited[neighbour.x][neighbour.y] = true;
						store.enqueue(neighbour);
					}
		    }
		 if (coo.y + 1 < map[0].length) {
				if ((map[coo.x][coo.y + 1] == '.'
				  || map[coo.x][coo.y + 1] == 'E')
			 && (!visited[coo.x][coo.y + 1])) {
				Point neighbour
				= new Point(0, 0);
	neighbour.x = coo.x; neighbour.y = coo.y + 1;
		visited[neighbour.x][neighbour.y] = true;
					store.enqueue(neighbour);
				}
			}
		}
		if (!getend) {
			return null;
		}
	int [][] all = new int [allpoints.size()][2 + 1];
	for (int i = 0; i < allpoints.size(); i++) {
		Point temp = (Point) allpoints.get(i);
			all[i][0] = temp.x;
			all[i][1] = temp.y;
		}
	int j = all.length - 2;
	SinglyLinkedList end = new SinglyLinkedList();
	Point temp = new Point(0, 0);
	temp.x = all[all.length - 1][0];
	temp.y = all[all.length - 1][1];
	end.add(0, (Point) temp);
	for (int i = all.length - 1; i >= 0;) {
	if (((all[i][0] == all[j][0])
		&& (Math.abs(all[i][1] - all[j][1]) == 1))
		|| ((all[i][1] == all[j][1])
	&& (Math.abs(all[i][0] - all[j][0]) == 1))) {
		Point t = new Point(all[j][0], all[j][1]);
			end.add(0, (Point) t);
			i = j;
		} else {
			j--;
		}
		if (j < 0) {
			break;
		}
	}
	int [][] reallyend = new int [end.size()][2];
	for (int i = 0; i < end.size(); i++) {
		Point tempo = (Point) end.get(i);
		reallyend[i][0] = tempo.x;
		reallyend[i][1] = tempo.y;
	}
		return reallyend;
	}

	@Override
	public int[][] solveDFS(final File maze) {
		int n = 0, m = 0;
		int is = 0, js = 0;
		boolean hasstart = false;
		boolean hasend = false;
		boolean getend = false;
		SinglyLinkedList allpoints = new SinglyLinkedList();
		Point coo = new Point(0, 0);
		char[][] map = null;
		boolean[][] visited = null;
		MyStack store = new MyStack();
		try {
			int countlines = 0;
			BufferedReader br
	= new BufferedReader(new FileReader(maze));
			String read = "";
			int i = 0;
			while ((read = br.readLine()) != null) {
				if (countlines == 0) {
					String[] size = read.split(" ");
					n = Integer.parseInt(size[0]);
					m = Integer.parseInt(size[1]);
					map = new char[n][m];
					visited = new boolean[n][m];
				} else {
					char[] mapline = read.toCharArray();
					for (int j = 0; j < m; j++) {
						if (mapline[j] == 'S') {
							is = i;
							js = j;
							hasstart = true;
						}
						if (mapline[j] == 'E') {
							hasend = true;
						}
						map[i][j] = mapline[j];
					}
					i++;
				}
				countlines++;
			}
			br.close();

		} catch (IOException e) {
			e.printStackTrace();
		}
		if (!hasend || !hasstart) {
			throw new RuntimeException();
		}
		coo.x = is;
		coo.y = js;
		store.push(coo);
		while (!store.isEmpty()) {
			coo = (Point) store.pop();
			visited[coo.x][coo.y] = true;
			allpoints.add((Point) coo);
			if (map[coo.x][coo.y] == 'E') {
				getend = true;
				break;
			}
			 if (coo.y + 1 < map[0].length) {
				if ((map[coo.x][coo.y + 1] == '.'
					|| map[coo.x][coo.y + 1] == 'E')
					&& (!visited[coo.x][coo.y + 1])) {
					Point neighbour = new Point(0, 0);
					neighbour.x = coo.x;
					neighbour.y = coo.y + 1;
			visited[neighbour.x][neighbour.y] = true;
					store.push(neighbour);
				}
			}
			 if (coo.y - 1 >= 0) {
			if ((map[coo.x][coo.y - 1] == '.'
			|| map[coo.x][coo.y - 1] == 'E')
			&& (!visited[coo.x][coo.y - 1])) {
					Point neighbour = new Point(0, 0);
					neighbour.x = coo.x;
					neighbour.y = coo.y - 1;
		visited[neighbour.x][neighbour.y] = true;
					store.push(neighbour);
				}
			}
			 if (coo.x - 1 >= 0) {
					if ((map[coo.x - 1][coo.y] == '.'
					|| map[coo.x - 1][coo.y] == 'E')
				&& (!visited[coo.x - 1][coo.y])) {
				Point neighbour = new Point(0, 0);
						neighbour.x = coo.x - 1;
						neighbour.y = coo.y;
				visited[neighbour.x][neighbour.y] = true;
						store.push(neighbour);
					}
				}
			 if (coo.x + 1 < map.length) {
				if ((map[coo.x + 1][coo.y] == '.'
					|| map[coo.x + 1][coo.y] == 'E')
					&& (!visited[coo.x + 1][coo.y])) {
					Point neighbour = new Point(0, 0);
						neighbour.x = coo.x + 1;
						neighbour.y = coo.y;
			visited[neighbour.x][neighbour.y] = true;
						store.push(neighbour);
					}
				}
		}
		if (!getend) {
			return null;
		}
		int [][] all = new int [allpoints.size()][2];
		for (int i = 0; i < allpoints.size(); i++) {
			Point temp = (Point) allpoints.get(i);
			all[i][0] = temp.x;
			all[i][1] = temp.y;
		}
		return all;
	}

}

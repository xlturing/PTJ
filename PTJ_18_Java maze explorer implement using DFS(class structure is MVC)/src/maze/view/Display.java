package maze.view;

import maze.Maze;

public class Display {
	/**
	 * @description show current state of maze and robot
	 * @param maze
	 */
	public void showMaze(Maze maze) {
		for (int i = 0; i < maze.getMatrix_m(); i++) {
			for (int j = 0; j < maze.getMatrix_n(); j++) {
				System.out.print(maze.getMaze()[i][j]);
			}
			System.out.println();
		}

		System.out.println("---------------------------------------------");
	}

	/**
	 * @description flush the console output
	 */
	public void flush() {
		System.out.flush();
	}

	/**
	 * @description Print string to console
	 * @param s
	 */
	public void printStr(String s) {
		System.out.println(s);
	}

	public void printNum(int t) {
		System.out.println("move number -> " + t);
	}
}

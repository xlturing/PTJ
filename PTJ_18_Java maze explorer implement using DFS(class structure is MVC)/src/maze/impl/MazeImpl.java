package maze.impl;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import maze.Maze;

public class MazeImpl extends Maze {
	public MazeImpl() {}

	public MazeImpl(String mazePath) {
		try {
			initMaze(mazePath);
		}
		catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void initMaze(String mazePath) throws IOException {
		BufferedReader in = new BufferedReader(new FileReader(mazePath));
		String s;
		char[] temp;
		int size_m = 0, size_n = 0;
		while ((s = in.readLine()) != null) {
			temp = s.toCharArray();
			size_n = temp.length;
			for (int i = 0; i < size_n; i++)
				this.maze[size_m][i] = temp[i];
			++size_m;
		}
		in.close();

		// init maze
		this.matrix_m = size_m;
		this.matrix_n = size_n;
		// an m-by-n mazerequires a (2m + 1)-by-(2n + 1) matrix of characters
		this.m = (matrix_m - 1) / 2;
		this.n = (matrix_n - 1) / 2;
		// m-by-n maze, the entrance is at(0,0) which in matrix is (1,1)
		this.entry_pos.setX(1);
		this.entry_pos.setY(1);
		// and the exit is at(m-1,n-1).
		this.exit_pos.setX(matrix_m - 2);
		this.exit_pos.setY(matrix_n - 2);
	}
}

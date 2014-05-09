package maze;

import java.io.IOException;

public abstract class Maze {
	// The maze matrix
	protected char[][] maze = new char[Constant.MAX_M][Constant.MAX_N];
	// The maze matrix size
	protected int matrix_m; // width
	protected int matrix_n; // height
	// The maze size
	protected int m;
	protected int n;
	// The position of entrance
	protected Position entry_pos = new Position();
	// The position of exit
	protected Position exit_pos = new Position();

	/**
	 * @description init the maze from file
	 * @param mazePath
	 * @throws IOException
	 */
	public void initMaze(String mazePath) throws IOException {}

	public char[][] getMaze() {
		return maze;
	}

	public int getM() {
		return m;
	}

	public int getN() {
		return n;
	}

	public int getMatrix_m() {
		return matrix_m;
	}

	public int getMatrix_n() {
		return matrix_n;
	}

	public Position getEntry_pos() {
		return entry_pos;
	}

	public Position getExit_pos() {
		return exit_pos;
	}

}

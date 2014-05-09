package maze;

public abstract class Robot {
	// The previous positon of robot
	protected Position pre_pos = new Position();
	// The current position of robot
	protected Position cur_pos = new Position();
	// The next position of robot
	protected Position next_pos = new Position();
	// The four directions is space or wall
	// true: space, false: wall
	protected boolean[] dir;
	// The start face direction is south
	protected int faceDir = Constant.SOUTH;

	public Robot(Maze maze) {
		// init the start position
		this.pre_pos.setX(maze.getEntry_pos().getX());
		this.pre_pos.setY(maze.getEntry_pos().getY());
		this.cur_pos.setX(maze.getEntry_pos().getX());
		this.cur_pos.setY(maze.getEntry_pos().getY());
		this.next_pos.setX(maze.getEntry_pos().getX());
		this.next_pos.setY(maze.getEntry_pos().getY());

		// draw to maze
		changePos(maze);

		this.dir = new boolean[4];
	}

	/**
	 * @description Decide whether robot arrive at exit
	 * @param maze
	 * @return true: arrive exit, false: don't
	 */
	public boolean arriveExit(Maze maze) {
		if (this.cur_pos.equals(maze.exit_pos))
			return true;
		else
			return false;
	}

	/**
	 * @description move robot to next position
	 * @return if have route return true or return false
	 */
	public boolean nextMove(Maze maze) {
		return false;
	}

	/**
	 * @description change position of robot in maze martrix
	 * @param maze
	 */
	public void changePos(Maze maze) {
		maze.getMaze()[cur_pos.getX()][cur_pos.getY()] = ' ';
		maze.getMaze()[next_pos.getX()][next_pos.getY()] = 'R';

		pre_pos.copyPos(cur_pos);
		cur_pos.copyPos(next_pos);
	}

	public Position getCur_pos() {
		return cur_pos;
	}

	public Position getNext_pos() {
		return next_pos;
	}

	/**
	 * @description find which dirction is space or wall based on current
	 *              postion
	 * @param maze
	 */
	protected void findDir(Maze maze) {
		char[][] tem = maze.getMaze();
		// flush
		for (int i = 0; i < 4; i++) {
			dir[i] = false;
		}
		// NORTH
		if (cur_pos.getX() - 2 >= 0
				&& tem[cur_pos.getX() - 1][cur_pos.getY()] == ' '
				&& (cur_pos.getX() - 2) != pre_pos.getX())
			dir[Constant.NORTH] = true;
		// SOUTH
		if (cur_pos.getX() + 2 < maze.matrix_m
				&& tem[cur_pos.getX() + 1][cur_pos.getY()] == ' '
				&& (cur_pos.getX() + 2) != pre_pos.getX())
			dir[Constant.SOUTH] = true;
		// WEST
		if (cur_pos.getY() - 2 >= 0
				&& tem[cur_pos.getX()][cur_pos.getY() - 1] == ' '
				&& (cur_pos.getY() - 2) != pre_pos.getY())
			dir[Constant.WEST] = true;
		// EAST
		if (cur_pos.getY() + 2 < maze.matrix_n
				&& tem[cur_pos.getX()][cur_pos.getY() + 1] == ' '
				&& (cur_pos.getY() + 2) != pre_pos.getY())
			dir[Constant.EAST] = true;
	}
}

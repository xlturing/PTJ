package maze.impl;

import maze.Constant;
import maze.Maze;
import maze.Robot;

public class RightRobot extends Robot {
	public RightRobot(Maze maze) {
		super(maze);
	}

	@Override
	public boolean nextMove(Maze maze) {
		// Finding the four directions is wall or space
		this.findDir(maze);
		int i;
		for (i = 0; i < 4; i++) {
			if (this.dir[i])
				break;
		}
		// If there is no space in four directions, return false.
		if (i == 4) {
			return false;
		}

		// Turn Right according to the face direction
		if (faceDir == Constant.NORTH && dir[Constant.EAST])
			this.next_pos.addY();
		else if (faceDir == Constant.SOUTH && dir[Constant.WEST])
			this.next_pos.subY();
		else if (faceDir == Constant.EAST && dir[Constant.SOUTH])
			this.next_pos.addX();
		else if (faceDir == Constant.WEST && dir[Constant.NORTH])
			this.next_pos.subX();
		// choice a direction which is available
		else {
			if (dir[Constant.NORTH])
				this.next_pos.subX();
			else if (dir[Constant.SOUTH])
				this.next_pos.addX();
			else if (dir[Constant.WEST])
				this.next_pos.subY();
			else
				this.next_pos.addY();
		}

		return true;
	}
}

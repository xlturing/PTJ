package maze.impl;

import java.util.Date;
import java.util.Random;

import maze.Constant;
import maze.Maze;
import maze.Position;
import maze.Robot;

public class LeftRobot extends Robot {
	public LeftRobot(Maze maze) {
		super(maze);
	}

	private Random r = new Random((new Date()).getTime());

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
			next_pos.copyPos(pre_pos);
			// Turn round
			switch (faceDir) {
				case Constant.NORTH:
					faceDir = Constant.SOUTH;
					break;
				case Constant.SOUTH:
					faceDir = Constant.NORTH;
					break;
				case Constant.WEST:
					faceDir = Constant.EAST;
					break;
				case Constant.EAST:
					faceDir = Constant.WEST;
					break;
			}
			return true;
		}

		// Turn left according to the face direction
		if (faceDir == Constant.NORTH && dir[Constant.WEST]){
			this.next_pos.subY();
			faceDir = Constant.WEST;
		}
		else if (faceDir == Constant.SOUTH && dir[Constant.EAST]){
			this.next_pos.addY();
			faceDir = Constant.EAST;
		}
		else if (faceDir == Constant.EAST && dir[Constant.NORTH]){
			this.next_pos.subX();
			faceDir = Constant.NORTH;
		}
		else if (faceDir == Constant.WEST && dir[Constant.SOUTH]){
			this.next_pos.addX();
			faceDir = Constant.SOUTH;
		}
		// choice a direction which is available
		else {
			// Choice a random direction
			while (true) {
				int randDir = r.nextInt(4);
				if (this.dir[randDir]) {
					switch (randDir) {
						case Constant.NORTH:
							this.next_pos.subX();
							faceDir = Constant.NORTH;
							break;
						case Constant.SOUTH:
							this.next_pos.addX();
							faceDir = Constant.SOUTH;
							break;
						case Constant.WEST:
							this.next_pos.subY();
							faceDir = Constant.WEST;
							break;
						case Constant.EAST:
							this.next_pos.addY();
							faceDir = Constant.EAST;
							break;
					}
					break;
				}
			}
		}

		return true;
	}
}

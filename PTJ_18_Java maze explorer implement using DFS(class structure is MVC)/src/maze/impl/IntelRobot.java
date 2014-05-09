package maze.impl;

import java.util.Date;
import java.util.Random;

import maze.Constant;
import maze.Maze;
import maze.Position;
import maze.Robot;

public class IntelRobot extends Robot {
	// Remeber the robot trace
	private Stack<Position> memRoute = new Stack<Position>();
	private Random r = new Random((new Date()).getTime());

	public IntelRobot(Maze maze) {
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
			// If Stack is empty, then all route have tried, so no way to exit
			if (memRoute.empty())
				return false;

			// recall to the previous position
			Position p = memRoute.pop();
			this.pre_pos = this.cur_pos;
			this.cur_pos = p;
			return true;
		}

		// Push now state to stack
		Position p = new Position();
		p.setX(this.cur_pos.getX());
		p.setY(this.cur_pos.getY());
		memRoute.push(p);

		// Choice a random direction
		while (true) {
			int randDir = r.nextInt(4);
			if (this.dir[randDir]) {
				switch (randDir) {
					case Constant.NORTH:
						this.next_pos.subX();
						break;
					case Constant.SOUTH:
						this.next_pos.addX();
						break;
					case Constant.WEST:
						this.next_pos.subY();
						break;
					case Constant.EAST:
						this.next_pos.addY();
						break;
				}
				break;
			}
		}

		return true;
	}
}

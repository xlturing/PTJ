package maze.impl;

import maze.Position;

/**
 * @description: This class is using to record robot trace which may be used to
 *               recall
 */
public class State {
	// Current position
	public Position cur_pos = new Position();
	// Current direction
	public int dir;
}

package maze.control;

import java.util.Scanner;

import maze.Constant;
import maze.Maze;
import maze.Robot;
import maze.impl.IntelRobot;
import maze.impl.LeftRobot;
import maze.impl.MazeImpl;
import maze.impl.RandomRobot;
import maze.impl.RightRobot;
import maze.view.Display;

public class GameManager {
	private Maze maze = null;
	private Robot robot = null;
	private int stepNum = 1;
	private String robotType;
	private int choose;

	// View robot and maze state
	private Display display = new Display();

	public GameManager(int choose) {
		// Init maze
		try {
			maze = new MazeImpl();
			maze.initMaze(Constant.mazePath);
		}
		catch (Exception e) {
			System.out.println("Maze Can't init");
			e.printStackTrace();
		}

		// Regist robot to maze
		try {
			if (choose == 1) {
				robot = new LeftRobot(maze);
				this.robotType = "LeftHandRobot";
			}
			else if (choose == 2) {
				robot = new RightRobot(maze);
				this.robotType = "RightHandRobot";
			}
			else if (choose == 3) {
				robot = new RandomRobot(maze);
				this.robotType = "RandomRobot";
			}
			else {
				robot = new IntelRobot(maze);
				this.robotType = "RandomMemoryRobot";
			}
		}
		catch (Exception e) {
			System.out.println("Robot Can't init");
			e.printStackTrace();
		}
	}

	/**
	 * @description start to explore
	 */
	public void start() {
		// start to explore until arrive exit
		while (true) {
			// flush the console output
			display.flush();
			// show robot type
			display.printStr("Robot type -> " + this.robotType);
			// show move number
			display.printNum(stepNum++);
			// show now state
			display.showMaze(maze);
			// pause for a short time: 200 milisecond
			try {
				Thread.sleep(200);
			}
			catch (InterruptedException e) {
				e.printStackTrace();
			}
			if (robot.arriveExit(maze)) {
				display.printStr("Congratulation! Robot is smart enough to arrive to exit!");
				break;
			}
			// move robot
			if (robot.nextMove(maze)) {
				robot.changePos(maze);
			}
			else {
				display.printStr("Sorry robot is foolish, it can't find the route to exit!");
				break;
			}
		}
	}

	public static void main(String[] args) {
		System.out.println("Choose Robot Type:");
		System.out.println("1->LeftHand Robot");
		System.out.println("2->RightHand Robot");
		System.out.println("3->Random Robot");
		System.out.println("4->Intelligent Robot");
		System.out.println("Your choose:");
		Scanner in = new Scanner(System.in);
		int robotType = in.nextInt();
		GameManager gm = new GameManager(robotType);
		gm.start();
	}
}

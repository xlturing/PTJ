package hw2.main;

import hw2.model.DrawingModel;
import hw2.shape.Circle;
import hw2.shape.Diamond;
import hw2.shape.Plus;
import hw2.shape.Square;
import hw2.view.DrawingView;
import hw2.view.Viewer;

import java.awt.*;
import java.util.Date;
import java.util.Random;

import javax.swing.JFrame;

public class Main {
	private static int countShape = 50; // the number of shapes are drawed in
										// graphic
	private static Random r = new Random(new Date().getTime()); // create random
																// shape

	/**
	 * @description This class have a main method that creates the DrawingModel
	 *              and Viewer and sets them up and we will create some random
	 *              shapes which we have build before
	 * @param args
	 * 
	 */
	public static void main(String[] args) {
		// Create a graphics view and put it in a window
		JFrame frame = new JFrame("Random Shapes");
		Viewer view = new Viewer();
		frame.setBackground(Color.black);
		frame.getContentPane().add(view);
		frame.setSize(304, 325); // a bit of extra space for insets
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.validate();
		frame.setVisible(true);

		// Create the simulation model and populate it
		DrawingModel dm = new DrawingModel();
		for (int i = 0; i < countShape; i++) {
			// create a random shape
			switch (r.nextInt(4)) {
				case 0:
					dm.add(new Diamond(Color.red, r.nextInt(10) + 10, r
							.nextInt(305) + 1, r.nextInt(326) + 1));
					break;
				case 1:
					dm.add(new Plus(Color.green, r.nextInt(10) + 10, r
							.nextInt(305) + 1, r.nextInt(326) + 1));
					break;
				case 2:
					dm.add(new Circle(Color.blue, r.nextInt(10) + 10, r
							.nextInt(305) + 1, r.nextInt(326) + 1));
					break;
				case 3:
					dm.add(new Square(Color.orange, r.nextInt(10) + 10, r
							.nextInt(305) + 1, r.nextInt(326) + 1));
					break;
			}
		}
		// Connect the view to the simulation and let it run
		dm.addViewer(view);
		// notify all viewers
		dm.notifyAllViewer();
	}
}

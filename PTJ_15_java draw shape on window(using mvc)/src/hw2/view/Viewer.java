package hw2.view;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.Iterator;
import java.util.List;

import hw2.model.DrawingModel;
import hw2.shape.Shape;

import javax.swing.JPanel;

public class Viewer extends JPanel implements DrawingView {
	DrawingModel dm = null; // the model we are watching

	@Override
	public void notify(DrawingModel m) {
		this.dm = m;
		repaint();
	}

	/*
	 * repaint all shape in the model
	 */
	public void paintComponent(Graphics g) {
		// clear the view
		Rectangle bounds = getBounds();
		g.clearRect(0, 0, bounds.width, bounds.height);

		if (dm == null) {
			System.out.println("System Info: the model empty!"); // Console info
			return;
		}

		List<Shape> ls = dm.getAllShapes();
		Iterator<Shape> it = ls.iterator();

		// Repaint all shapes on graphic
		while (it.hasNext()) {
			Shape s = it.next();
			s.draw(g);
		}
	}
}

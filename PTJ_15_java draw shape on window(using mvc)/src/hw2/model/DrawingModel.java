package hw2.model;

import hw2.shape.Shape;
import hw2.view.DrawingView;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class DrawingModel {
	private List<Shape> ls; // The collection of the Shapes
	private List<DrawingView> ldv; // The collection of the viewers

	public DrawingModel() {
		this.ls = new ArrayList<Shape>();
		this.ldv = new ArrayList<DrawingView>();
	}

	/**
	 * @description A client should be able to add a Shape to the model
	 * @param s
	 * 
	 */
	public void add(Shape s) {
		this.ls.add(s);
		//notifyAllViewer(); // notify all viewers
	}

	/**
	 * @description A viewer should be able to register with the model
	 * @param dv
	 * 
	 */
	public void addViewer(DrawingView dv) {
		this.ldv.add(dv);
	}

	/**
	 * @description Get all shapes from model
	 * @return
	 * 
	 */
	public List<Shape> getAllShapes() {
		return this.ls;
	}

	/**
	 * @description Get all viewers from model
	 * @return
	 * 
	 */
	public List<DrawingView> getAllViewers() {
		return this.ldv;
	}

	/**
	 * @descriptionand notify all viewers
	 */
	public void notifyAllViewer() {
		// Notify all viewers
		Iterator<DrawingView> it = ldv.iterator();
		while (it.hasNext()) {
			DrawingView dv = it.next();
			dv.notify(this);
		}
	}
}

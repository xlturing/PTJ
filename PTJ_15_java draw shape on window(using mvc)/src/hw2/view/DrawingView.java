package hw2.view;

import hw2.model.DrawingModel;

public interface DrawingView {

	/**
	 * @description Notify this viewer when something in the model changes so it
	 *              can update its display in model
	 * @param m
	 * 
	 */
	public void notify(DrawingModel m);

}

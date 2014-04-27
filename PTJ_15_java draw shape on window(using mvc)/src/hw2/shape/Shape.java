package hw2.shape;

import java.awt.*;

public interface Shape {
	/**
	 * @description Draw the shape on a java Graphic object
	 * @param Graphics
	 */
	public void draw(Graphics g);
	
	/** 
	 * @description Deep copy the shape
	 * @return Shape
	 * 
	 */  
	public Shape copy();
}

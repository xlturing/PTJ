package just_try;

import java.awt.Color;
import java.lang.Math;
import javax.swing.JButton;

/**
 * This class extends from JButton which handle the box move issue. Include info£ºbox target location£¨final_x,final_y),
 * box current location (now_x,now_y) step number between box and target location (leamove).
 * 
 * @author Administrator
 * 
 */
public class MyButton extends JButton {

	private int final_x, final_y; // target location
	private int now_x, now_y; // cur location
	private int leamove;
	private int number;
	private boolean isnull;
	private Color color;
	public MyButton() {
		this.number = 0;
		this.final_x = 0;
		this.final_y = 0;
		this.now_x = 0;
		this.now_y = 0;
		this.leamove = 0;
		this.isnull = false;
		
		
	}
	
	public void setMyButton(int x, int y, int num) {
		number = num;
		final_x = now_x = x;
		final_y = now_y = y;
		setText(number + "");
		setBackground(color);
		setLeamove(0);
		setNumber(num);
		if(num > 0) {
			isnull = false;
		}
		if(num < 1) {
			isnull = true;
			setBackground(Color.white);
			setText("");
		} 
	}

	/**
	 * swap current box with myButton
	 * @param myButton
	 */
	public void swap(MyButton myButton) {
		if (!isnull && myButton.isIsnull()) {
			myButton.setBackground(this.getBackground());
			myButton.setFinal_x(this.final_x);
			myButton.setFinal_y(this.final_y);
			myButton.setLeamove(Math.abs(myButton.getFinal_x()
					- myButton.getNow_x())
					+ Math.abs(myButton.getFinal_y() - myButton.getNow_y()));
			myButton.setNumber(number);
			myButton.setText("" + myButton.getNumber());
			myButton.setIsnull(false);

			final_x = now_x;
			final_y = now_y;
			number = 0;
			setText("");
			isnull = true;
			setBackground(Color.white);
			setLeamove(0);
		}
	}

	public int getFinal_x() {
		return final_x;
	}

	public void setFinal_x(int final_x) {
		this.final_x = final_x;
	}

	public int getFinal_y() {
		return final_y;
	}

	public void setFinal_y(int final_y) {
		this.final_y = final_y;
	}

	public int getNow_x() {
		return now_x;
	}

	public int getNow_y() {
		return now_y;
	}

	public int getLeamove() {
		return leamove;
	}

	public void setLeamove(int leamove) {
		this.leamove = leamove;
	}

	public int getNumber() {
		return number;
	}

	public void setNumber(int number) {
		this.number = number;
	}

	public boolean isIsnull() {
		return isnull;
	}

	public void setIsnull(boolean isnull) {
		this.isnull = isnull;
	}

	public void setNow_x(int now_x) {
		this.now_x = now_x;
	}

	public void setNow_y(int now_y) {
		this.now_y = now_y;
	}
	public String toString(){
		String ss = "";
		ss +="ID:"+number+" location:(" + final_x + "," + final_y + ") current("
				+ now_x + "," + now_y + ") " + "move:"+ leamove + " space?:" + isnull; 
		return ss;
	}

}

package just_try;
/**
 * Handle box move
 * Include:attribute, heuristic method
 * 
 */
import java.lang.Math;
public class SimplePane {

	private int n = 9;		
	private int g;   
	private int h;
	private int totall = 0;
	private int sqrtn = 3;
	private SimplePane fathPane = null;
	private String action = "";
	SimpleButton []buttons;
	
	public SimplePane (SimplePane pane) {
		n = pane.buttons.length;
		sqrtn = (int)Math.sqrt(n);
		buttons = new SimpleButton[n];
		for (int i = 0; i < n; i++) {
			buttons [i] = new SimpleButton(pane.buttons[i]);
		}
		
		g = pane.g;
	}
	public SimplePane(MyButton myButton [], int temp) {
		int tempCount = myButton.length;
		buttons = new SimpleButton[tempCount];
		for (int i = 0; i < tempCount; i++) {
			buttons [i] = new SimpleButton(myButton[i]);
		}
		g = 0;
		h = getH(temp);
		totall = g + h;
		
	}
	
	/**
	 * Breath First search
	 * @return
	 */
	private int hCountNull() {
		h = 0;
		return hCountWay1();
	}
	
	/**
	 * heuristic search (method h represent box offset)
	 */
	private int hCountWay1() {
		int count = 0;
		for (SimpleButton hbutton : buttons) {
			if (hbutton.getLea_move() > 0 && !hbutton.isIsnull()) {
				count++;
			}
		}
		h = count;
		return count;
	}
	
	/**
	 * heuristic search (distance between box and target box)
	 */
	
	private int hCountWay2() {
		int count = 0;
		for(SimpleButton hButton : buttons) {
			if(!hButton.isIsnull()) {
				count += hButton.getLea_move();
			}
		}
		h = count;
		return count;
	}
	
	/**
	 * return heuristic method value
	 */
	public int getH(int i) {
		switch (i) {
		case 1: return hCountWay1();
		case 2: return hCountWay2();
		}
		return hCountNull();
	}
	
	public int getH() {
		return h;
	}
	
	/**
	 * space up
	 * @return
	 */
	public SimplePane moveup() {
		int locate;			//空白格子的位置
		for( locate = 0; locate < 9; locate++){
			if(buttons[locate].isIsnull()) {
				break;
			}
		}
		if(buttons[locate].getNow_y() > 1) {
			SimpleButton temp;
			SimplePane simplePane = new SimplePane(this);
			temp = simplePane.buttons[locate];
			simplePane.buttons[locate] = simplePane.buttons[locate -sqrtn];
			simplePane.buttons[locate -sqrtn] = temp;
			
			simplePane.buttons[locate].setNow_y(buttons[locate].getNow_y());
			simplePane.buttons[locate - sqrtn].setNow_y(buttons[locate - sqrtn].getNow_y());
			simplePane.g++;
			simplePane.action = "up";
			simplePane.fathPane = this; 
			return simplePane;
		}
		return null;
	}
	
	/**	 * 
	 * space down
	 * @param args
	 */
	public SimplePane movedown() {
		int locate;			//space location
		for( locate = 0; locate < n; locate++){
			if(buttons[locate].isIsnull()) {
				break;
			}
		}
		if(buttons[locate].getNow_y() < sqrtn) {
			SimpleButton temp;
			SimplePane simplePane = new SimplePane(this);
			
			temp = simplePane.buttons[locate];
			simplePane.buttons[locate] = simplePane.buttons[locate + sqrtn];
			simplePane.buttons[locate + sqrtn] = temp;
			
			simplePane.buttons[locate].setNow_y(buttons[locate].getNow_y());
			simplePane.buttons[locate + sqrtn].setNow_y(buttons[locate + sqrtn].getNow_y());
			
			simplePane.g++;
			simplePane.action = "down";
			simplePane.fathPane = this;
			return simplePane;
		}
		return null;
	}
	
	/**	 * 
	 * space left
	 * @param args
	 */
	public SimplePane moveleft() {
		int locate;			
		for( locate = 0; locate < n; locate++){
			if(buttons[locate].isIsnull()) {
				break;
			}
		}
		if(buttons[locate].getNow_x() > 1) {
			SimpleButton temp;
			SimplePane simplePane = new SimplePane(this);
			
			temp = simplePane.buttons[locate];
			simplePane.buttons[locate] = simplePane.buttons[locate - 1];
			simplePane.buttons[locate - 1] = temp;
			
			simplePane.buttons[locate].setNow_x(buttons[locate].getNow_x());
			simplePane.buttons[locate - 1].setNow_x(buttons[locate - 1].getNow_x());
			
			simplePane.g++;
			simplePane.action = "left";
			simplePane.fathPane = this;
			return simplePane;
		}
		return null;
	}
	
	/**	 * 
	 * space right
	 * @param args
	 */
	public SimplePane moveright() {
		int locate;			
		for( locate = 0; locate < n; locate++){
			if(buttons[locate].isIsnull()) {
				break;
			}
		}
		if(buttons[locate].getNow_x() < sqrtn) {
			SimpleButton temp;
			SimplePane simplePane = new SimplePane(this);
			
			temp = simplePane.buttons[locate];
			simplePane.buttons[locate] = simplePane.buttons[locate + 1];
			simplePane.buttons[locate + 1] = temp;
			
			simplePane.buttons[locate].setNow_x(buttons[locate].getNow_x());
			simplePane.buttons[locate + 1].setNow_x(buttons[locate + 1].getNow_x());
			
			simplePane.g++;
			simplePane.action = "right";
			simplePane.fathPane = this;
			return simplePane;
		}
		return null;
	}
	public String toString() {
		String s = "";
		for (int i = 0; i < 9; i++) {
			s += buttons[i] + "  ";
		}
		return s;
	}
	public static void main(String []args) {

	}
	public int getTotall() {
		totall = h + g;
		return totall;
	}
	public SimplePane getFathPane() {
		return fathPane;
	}
	public void setFathPane(SimplePane fathPane) {
		this.fathPane = fathPane;
	}
	public String getAction() {
		return action;
	}
	public void setAction(String action) {
		this.action = action;
	}
	
}

/**
 * simple box
 * 
 * @author Administrator
 *
 */
class SimpleButton{
	private int now_x, now_y;
	private int final_x, final_y;
	private int lea_move;
	private boolean isnull;
	private int number;
	public SimpleButton() {
		
		now_x = now_y = 0;
		final_y = final_x = 0;
		lea_move = 0;
		isnull = false;
	}
	public SimpleButton(int x, int y){
		final_x = now_x = x;
		final_y = now_y = y;
		lea_move = 0;
		isnull = false;
	}
	public SimpleButton(MyButton tempButton) {
		
		now_x = tempButton.getNow_x();
		now_y = tempButton.getNow_y();
		final_x = tempButton.getFinal_x();
		final_y = tempButton.getFinal_y();
		isnull = tempButton.isIsnull();
		lea_move = this.getLea_move();
		number = tempButton.getNumber();
	}
	public SimpleButton(int x, int y, boolean isnull) {
		this(x, y);
		this.isnull = isnull;
	}
	public SimpleButton(SimpleButton button){
		
		now_x = button.now_x;
		now_y = button.now_y;
		final_x = button.final_x;
		final_y = button.final_y;
		number = button.number;
		lea_move = button.lea_move;
		isnull = button.isnull;
	}
	public int getNumber() {
		return number;
	}
	public int getNow_x() {
		return now_x;
	}
	public void setNow_x(int now_x) {
		this.now_x = now_x;
	}
	public int getNow_y() {
		return now_y;
	}
	public void setNow_y(int now_y) {
		this.now_y = now_y;
	}
	public int getLea_move() {
		lea_move = Math.abs(final_x - now_x);
		lea_move += Math.abs(final_y - now_y);
		return lea_move;
	}
	public boolean isIsnull() {
		return isnull;
	}
	public void setIsnull(boolean isnull) {
		this.isnull = isnull;
	}

	public String toString() {
		return ""+number;
	}
	
}
package just_try;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

import java.util.LinkedHashMap;
import java.util.LinkedList;


/**
 * 
 * This class is the algorithm implementation
 * 
 */
public class MyManager {

	private LinkedList<SimplePane> open = null;
	private LinkedHashMap<String, SimplePane> closed;
	private int size;
	private SimplePane resultPane = null;
	private long startTime = 0;
	private long endTime = 0;

	public MyManager() {
		size = 0;
		open = new LinkedList<SimplePane>();
		closed = new LinkedHashMap<String, SimplePane>();
	}

	private void toSimplePane(MyButton myButton[], int i) {
		SimplePane pane = new SimplePane(myButton, i);
		size = myButton.length;
		open.add(pane);
	}

	/**
	 * decide whether a box can insert to open table
	 * 
	 * @param ispane
	 * @return
	 */
	private boolean isIntoOpen(SimplePane ispane) {
		if (!open.isEmpty()) {
			String str = ispane.toString();
			for (SimplePane temp : open) {
				if (temp.toString().equals(str)) {
					if (ispane.getTotall() < temp.getTotall()) {
						open.remove(temp);
						return true;
					} else {
						return false;
					}
				}
			}
		}
		return true;
	}

	/**
	 * decide whether a table can insert to closed table
	 * 
	 * @param args
	 */
	private boolean isInClosed(SimplePane ispane) {
		if (!closed.isEmpty()) {
			String str = ispane.toString();
			if (closed.containsValue(str)) {
				if (ispane.getTotall() < closed.get(str).getTotall()) {

					return true;
				} else {
					return false;
				}
			}
		}
		return true;
	}

	/**
	 * insert data to open table
	 * 
	 * @param intopane
	 */
	private void intoOpen(SimplePane intopane) {
		if (isInClosed(intopane) && isIntoOpen(intopane)) {
			int i = 0;
			int lenghs = open.size();
			int count = intopane.getTotall();
			for (; i < lenghs; i++) {
				if (count > open.get(i).getTotall()) {
					continue;
				} else {
					break;
				}
			}
			open.add(i, intopane);
		}
	}

	/**
	 * delete data from open table
	 * 
	 * @param args
	 */
	private void intoClosed(SimplePane intopane) {
		String clostr = intopane.toString();
		if (isInClosed(intopane)) {
			closed.remove(clostr);
			closed.put(clostr, intopane);
		}
	}

	/**
	 * print result
	 * @return
	 */
	private String theResult() {
		String result = "";
		
		while(resultPane != null) {
			result += "#" + resultPane.getAction();
			resultPane = resultPane.getFathPane();
		}
		
		String tt[]= result.split("#");
		String temp = "";
		// revert!
		int count = tt.length - 1;
		int subcount  = count/2;
		for (int i = 0; i <= subcount; i++) {
			temp = tt[i];
			tt[i] = tt[count - i];
			tt[count - i] = temp;
		}
		result = "Begin ";
		for(int i = 0; i < count; i++) {
			result +="-->" + tt[i];
			if ((i%6 == 0) && i != 0) result += "\n\t";
		}
		
		//开始写文本
		endTime = System.currentTimeMillis();
		endTime = endTime - startTime;
		try {
			BufferedWriter bw = new BufferedWriter( new FileWriter("input.txt",true));
			bw.append("\t perform time:" + (endTime ) + "  millisecond\n");
			bw.append("\tclosed table extend number:"  + closed.size() + "\n");
			bw.append("\t    useful extend number:" + count + "\n");
			bw.append("\t	open table extend number:" + open.size() + "\n");
			bw.append("path:" + result + "\n");
			bw.close();
		} catch (IOException e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		
		return result;
	}
	public String manager(MyButton buttons[], int temp) {
		startTime = System.currentTimeMillis();//now time
		this.toSimplePane(buttons, temp);
		SimplePane maPane = open.removeFirst();
		if (maPane.getH(temp) == 0) {
			
			System.out.println("You don't move any buttons!");
			return "You don't move any buttons!";
			
		} else {
			boolean isContinue = true;
			SimplePane submaPane = null;
			while (isContinue) {
				closed.put(maPane.toString(), maPane);
				// space up
				submaPane = maPane.moveup();
				if (submaPane != null) {
					if (submaPane.getH(temp) == 0) {
						// initial h
						closed.put(submaPane.toString(), submaPane);
						System.out.println("successful find the way!");
						resultPane = submaPane;
						return theResult();
					}
					intoOpen(submaPane);
				}

				// space move down
				submaPane = maPane.movedown();
				if (submaPane != null) {
					if (submaPane.getH(temp) == 0) {
						// initial h
						closed.put(submaPane.toString(), submaPane);
						System.out.println("successful find the way!");
						resultPane = submaPane;
						return theResult();
					}
					intoOpen(submaPane);
				}

				// space move left
				submaPane = maPane.moveleft();
				if (submaPane != null) {
					if (submaPane.getH(temp) == 0) {
						closed.put(submaPane.toString(), submaPane);
						System.out.println("successful find the way!");
						resultPane = submaPane;
						return theResult();
					}
					intoOpen(submaPane);
				}

				// space move right
				submaPane = maPane.moveright();
				if (submaPane != null) {
					if (submaPane.getH(temp) == 0) {
						closed.put(submaPane.toString(), submaPane);
						System.out.println("successful find the way!");
						resultPane = submaPane;
						return theResult();
					}
					intoOpen(submaPane);
				}
				maPane = open.removeFirst();
			}
			return null;
		}

	}

	public static void main(String[] args) {
		MyManager manager = new MyManager();
		MyButton[] mainButton = new MyButton[9];
		for (int i = 0; i < 9; i++) {
			mainButton[i] = new MyButton();
			mainButton[i].setMyButton((i % 3) + 1, i / 3 + 1, (i + 1) % 9);
		}
		mainButton[7].swap(mainButton[8]);
		mainButton[4].swap(mainButton[7]);
		mainButton[5].swap(mainButton[4]);
		manager.toSimplePane(mainButton, 1);
		// manager.caculate(1);
		SimplePane tt1, tt2, tt3;
		tt2 = manager.open.removeFirst();
		// System.out.println(tt1.getTotall());
		System.out.println("original: " + tt2);

		tt2 = tt2.moveleft();

		System.out.println("left: " + tt2);
		manager.intoOpen(tt2);
		// System.out.println(tt2.getH(1));
		// System.out.println(tt2.getTotall());
		tt2 = manager.open.removeFirst();
		System.out.println("up: " + tt2);

		tt2 = tt2.movedown();

		System.out.println("down:  " + tt2);
		manager.intoOpen(tt2);
		// System.out.println(tt2.getH(1));
		// System.out.println(tt2);
		// System.out.println(tt2.getTotall());
		tt2 = manager.open.removeFirst();

		tt2 = tt2.moveright();

		System.out.println("right:  " + tt2);
		manager.intoOpen(tt2);
		// System.out.println(tt2.getH(1));
		// System.out.println(tt2.getTotall());
		tt2 = manager.open.removeFirst();

		tt2 = tt2.moveup();

		System.out.println("up:  " + tt2 + "   " + tt2.getTotall()
				+ tt2.getH(1));
		manager.intoOpen(tt2);
		System.out.println("ccy1::::::");
		manager.intoOpen(tt2.moveup());
		System.out.println(tt2 + "  " + tt2.getH(1) + " " + tt2.getTotall());
		manager.intoOpen(tt2.movedown());
		System.out.println(tt2 + "  " + tt2.getH(1) + " " + tt2.getTotall());
		System.out.println(manager.open);

	}

}

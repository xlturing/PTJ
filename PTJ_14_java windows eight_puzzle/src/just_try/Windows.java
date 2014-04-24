package just_try;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;
import javax.swing.event.*;
import javax.swing.text.StyleConstants;

import java.io.*;

public class Windows {
	
	private JLabel label;
	private JButton exits, show, clear2, test1, result1;
	private JPanel panel, panel2, menuJpanle, txtpanle;
	private JMenuItem miNature, mieven, exit, none, way1, way2;
	private JMenuBar menuBar;
	private JMenu file, done;
	private JFrame window,txtArea;
	private JTextArea textArea;
	private Color color;
	private int count;
	private long start=0,end=0;
	static MyButton []button = new MyButton[9];
	public Windows() {
		window =new JFrame();
		done = new JMenu("search strategy");
		way1 = new JMenuItem("search strategy 1");
		way2 = new JMenuItem("search strategy 2");
		none = new JMenuItem("breadth-first search");
		file = new JMenu("file show");
		miNature = new JMenuItem("natural ordering");
		mieven = new JMenuItem("even ordering");		
		exit = new JMenuItem("exit");
		
		exits = new JButton("exit");
		show = new JButton("show result");
		clear2 = new JButton("clear records");
		
		test1 = new JButton("user try");
		result1 = new JButton("result");
		panel = new JPanel();
		panel2 = new JPanel();
		txtpanle = new JPanel(new FlowLayout());
		menuJpanle = new JPanel(new FlowLayout(FlowLayout.LEFT));

		menuBar = new JMenuBar();
		
		
		panel.setLayout(new GridLayout(3,3));
		for (int i = 0; i < 9; i++) {
			button[i] = new MyButton();
			panel.add(button[i]);
			button[i].addActionListener(swapListener);
		}
		test1.addActionListener(test);
		result1.addActionListener(result);
		panel2.add(test1);
		panel2.add(result1);
		panel2.add(show);
		panel2.add(exits);
		txtpanle.add(clear2);
		
		file.add(miNature);
		file.add(mieven);
		file.add(exit);
		
		done.add(none);
		done.add(way1);
		done.add(way2);
		
		menuBar.add(file);
		menuBar.add(done);
		

		miNature.addActionListener(natureLis);
		mieven.addActionListener(evenLis);
		exit.addActionListener(exitt);
		exits.addActionListener(exitt);
		way1.addActionListener(chaWay);
		way2.addActionListener(chaWay);
		none.addActionListener(chaWay);
		show.addActionListener(showAction); 
		
		menuJpanle.add(menuBar);
		
		window.setTitle("8-puzzles");
		window.add(menuJpanle, BorderLayout.NORTH);
		window.add(panel, BorderLayout.CENTER);
		window.add(panel2, BorderLayout.SOUTH);
		window.setSize(400,400);
		window.setLocationRelativeTo(null);		
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.setVisible(true);
		
		//set another window
		txtArea = new JFrame("result show");
		textArea = new JTextArea();
		txtArea.setTitle("result");
		textArea.setBackground(Color.CYAN);
		txtArea.add(new JScrollPane(textArea));
		
		clear2.addActionListener(acle);
		
		txtArea.add(txtpanle,BorderLayout.SOUTH);
		txtArea.setSize(window.getWidth(), window.getHeight());
	}
	/**
	 * test the mox can swap with near box or not£¬
	 * return the number:
	 * 0: cannot
	 * 1: swap with up box
	 * 2: swap with down box
	 * 3: swap with left box
	 * 4: swap with right box
	 * 
	 * @return
	 */
	public static int isswap(MyButton tembutton) {
		 int	x, y, dir = 0;
		 x = tembutton.getNow_x() - 1;
		 y = tembutton.getNow_y() - 1;
		 
		 switch (x) {
		case 1: 
		case 0:
		{   //right box empty?
			if (button[x + y * 3 + 1 ].isIsnull()) {
				return (4);
			}
			if (x < 1) break;
		}
		case 2:
		{	//left box empty?
			if ((button[x - 1 + y * 3].isIsnull())) {
				return (3);
			}
		}break;

		default: dir = 0;
			break;
		}
		 switch (y) {
		case 1:
		case 0:
		{   //down box empty?
			if (button[x + (y + 1) * 3].isIsnull()) {
				return (2);
			}
			if (y < 1) break;
		}
		case 2:
		{   //up box empty?
			if (button[x + (y - 1) * 3].isIsnull()) {
				return (1);
			}
		}break;
		default:dir = 0;
			break;
		}
		 
		return dir;
	}
	
	// clear txtarea
	private ActionListener acle = new ActionListener(){
		public void actionPerformed(ActionEvent e) {
			try {
				BufferedWriter bw = new BufferedWriter(new FileWriter("input.txt"));
				bw.append("");
				bw.close();
				textArea.setText("");
			} catch (IOException ie) {
				ie.printStackTrace();
			}
		}
	};
	
	//close window
	private ActionListener aex = new ActionListener() {
		public void actionPerformed(ActionEvent e) {
			
		}
	};
	//search strategy listener
	private ActionListener chaWay = new ActionListener() {

		public void actionPerformed(ActionEvent e) {
			
			String tt = "";
			MyManager manager = new MyManager();
			try {
				BufferedWriter bw = new BufferedWriter(new FileWriter("input.txt",true));
				bw.write("\n\n\t********************\n");
				bw.write(e.getActionCommand() + " :\n");
				bw.close();
				
			} catch (IOException ie) {
				// TODO: handle exception
				ie.printStackTrace();
			}
			if (e.getActionCommand().equals("search strategy 1")){
				tt = manager.manager(button, 1);
			} else if (e.getActionCommand().equals("search strategy 2")){
				tt = manager.manager(button, 2);
			} else if (e.getActionCommand().equals("breadth first search")){
				tt = manager.manager(button, 0);
			}
			
			System.out.println(tt);
			
						
		}
	
	};
	
	private ActionListener natureLis = new ActionListener(){
		public void actionPerformed(ActionEvent e) {
			for(int i = 0; i < 8; i++) {
				button[i].setText("" + (i + 1));
				button[i].setFinal_x(i % 3 + 1);
				button[i].setFinal_y(i / 3 + 1);
				button[i].setNumber(i + 1);
				button[i].setNow_x(i % 3 + 1);
				button[i].setNow_y(i / 3 + 1);
				button[i].setIsnull(false);
				button[i].setLeamove(0);
				button[i].setBackground(color);
			}
			button[8].setBackground(Color.white);
			button[8].setText("");
			button[8].setFinal_x(3);
			button[8].setFinal_y(3);
			button[8].setNumber(0);
			button[8].setNow_x(3);
			button[8].setNow_y(3);
			button[8].setIsnull(true);
			button[8].setLeamove(0);
		}
	};
	
	private ActionListener evenLis = new ActionListener(){
		public void actionPerformed(ActionEvent e) {
			button[0].setMyButton(1, 1, 1);
			button[1].setMyButton(2, 1, 2);
			button[2].setMyButton(3, 1, 3);
			button[3].setMyButton(1, 2, 8);
			button[4].setMyButton(2, 2, 0);
			button[5].setMyButton(3, 2, 4);
			button[6].setMyButton(1, 3, 7);
			button[7].setMyButton(2, 3, 6);
			button[8].setMyButton(3, 3, 5);
		}
	};
	
	//exit listener
	private ActionListener exitt = new ActionListener(){
		public void actionPerformed(ActionEvent e){
			System.exit(1);
		}
	};
	
	private ActionListener swapListener = new ActionListener(){
		public void actionPerformed(ActionEvent e) {
			MyButton temButt = (MyButton)e.getSource();
			int x = temButt.getNow_x() - 1;
			int y = temButt.getNow_y() - 1;
			int dir = x + y * 3;
			switch (isswap(temButt)) {
			case 1: dir = x + (y - 1) * 3; break;
			case 2: dir = x + (y + 1) * 3; break;
			case 3: dir = x - 1 + y * 3; break;
			case 4: dir = x + 1 + y * 3; break;
			default:
				break;
			}
			if(isswap(temButt) > 0) count++;
			try {
				temButt.swap(button[dir]);
			} catch (ArrayIndexOutOfBoundsException e1) {
				
			}
		}
	};
	
	//show operation hints
	private void theTips() {
		textArea.setText("\n\n\t****************************\n");
//		Color penColor = textArea.getForeground();
		textArea.setForeground(Color.red);
		textArea.append("please use right operation:\n");
//		textArea.setForeground(Color.blue);
		textArea.append("\t1.open file, click natural ordering or even ordering!\n");
		textArea.append("\t2.Disrupt number sequences£¡\n");
		textArea.append("\t3.then choice a search strategy\n");
		textArea.append("\t4.finally find the result\n");
	}
	
	// result listener
	private ActionListener showAction = new ActionListener() {
		public void actionPerformed(ActionEvent e) {

			txtArea.setLocation(window.getWidth() + window.getX(),window.getY());
			txtArea.setVisible(true);
			try {
				BufferedReader br = new BufferedReader(new FileReader("input.txt"));
				String brRead = br.readLine();
				if(brRead == null) theTips();
				while(brRead != null) {
					textArea.append(brRead + "\n");
					brRead = br.readLine();
				}
			} catch (IOException ie) {
				System.out.println("exception!");
				theTips();

			}
		}
	};


	private ActionListener test  =new ActionListener(){
		public void actionPerformed(ActionEvent e) {
			count = 0;
			start = System.currentTimeMillis();
		}
	};
	private ActionListener result  = new ActionListener() {
		public void actionPerformed(ActionEvent e){
			boolean isRight =true;
			for(int i = 0; i<button.length;i++) {
				if((button[i].getNow_x() == button[i].getFinal_x())
						&& button[i].getNow_y() == button[i].getFinal_y()) continue;
				else {
					isRight = false;
					textArea.append("\ncannot find right result");
					break;
				}
			}
			if(isRight) {
				end = System.currentTimeMillis();
				textArea.append("\n");
				textArea.append("step: " + count + "\n");
				textArea.append("time: " + (end - start) + "miliseconds\n");
			}
		}
	};
	public static void main(String[] args) {
		Windows windows = new Windows();
	}

}

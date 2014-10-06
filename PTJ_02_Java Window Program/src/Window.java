import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.text.DecimalFormat;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class Window extends JFrame
{
	JTextArea jta;
	JTextField jtfA;
	JTextField jtfB;
	
	public Window()
	{
		JPanel jp = new JPanel();// Define basic panel

		JPanel jp1 = new JPanel();// The above panel
		JPanel jp2 = new JPanel();// The below panel

		jtfA = new JTextField(5);// input a
		jtfB = new JTextField(5);// input b

		jta = new JTextArea("Result");// result show

		JButton button = new JButton("Calculate");// button

		JLabel jl = new JLabel("Please input: ");// label
		JLabel jlA = new JLabel("a ");// label a
		JLabel jlB = new JLabel("b ");// label b

		GridLayout gl = new GridLayout(2, 1);// Create grid layout

		// Set basic panel layout
		jp.setLayout(gl);

		// Set panel background and bind
		jp.setBackground(Color.WHITE);
		jp1.setBackground(Color.WHITE);
		jp2.setBackground(Color.WHITE);
		setContentPane(jp);

		// Set text filed
		jtfA.setText("0.0");
		jtfB.setText("0.0");

		// Set text area
		jta.setSize(100, 500);

		// Add componets to panel
		jp1.add(jl);
		jp1.add(jlA);
		jp1.add(jtfA);
		jp1.add(jlB);
		jp1.add(jtfB);
		jp1.add(button);
		
		jp2.add(jta);
		//jsp.add(jta);

		// Set window size
		setBounds(200, 200, 400, 400);

		//jsp.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS); 
		//jsp.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS); 
		
		// Add panel
		jp.add(jp1);
		jp.add(jp2);

		// Adjust window size according to component
		pack();

		// Window can't modify size
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setTitle("myWindow");// Window title

		// bind click event
		button.addActionListener(new ButtonHandler());
		
		setVisible(true);// Set window visible

	}

	private class ButtonHandler implements ActionListener
	{
		double a;
		double b;

		public void actionPerformed(ActionEvent e)
		{
			a = Double.parseDouble(jtfA.getText());
			b = Double.parseDouble(jtfB.getText());
			
			// judge your data whether reasonable
			if (a > b)
			{
				System.out.println("Sorry, your a is bigger than b");
				return;
			}

			// Create a new file
			File f = new File("result.txt");
			if (f.exists())
				f.delete();

			// Calculate result and print to file named 'result.txt'
			PrintWriter out;
			jta.append("\12");
			try
			{
				out = new PrintWriter(f);
				for (double i = a; i < b; i += 0.1)
				{
					DecimalFormat df = new DecimalFormat("######0.0");
					out.println(df.format(i) + ":\n");
					out.println("Square Root: " + Math.pow(i, 1.0 / 2.0));
					out.println("Cube Root: " + Math.pow(i, 1.0 / 3.0) + "\r\n");

					jta.append(df.format(i));
					jta.append("\12");
					jta.append("Square Root: " + Math.pow(i, 1.0 / 2.0));
					jta.append("\12");
					jta.append("Cube Root: " + Math.pow(i, 1.0 / 3.0));
					jta.append("\12");
				}
				out.close();
			} catch (FileNotFoundException e1)
			{
				e1.printStackTrace();
			}
		}
	}
}

package litaoxiao.ptj3;

import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class Window extends JFrame
{
	private JTextField textDate;
	private JLabel labelSuccess;
	private String url;

	public Window(String url)
	{
		this.url = url;

		JPanel jp = new JPanel();// Define basic panel

		JPanel jp1 = new JPanel();// The above panel
		JPanel jp2 = new JPanel();// The below panel

		textDate = new JTextField(5);// input date

		labelSuccess = new JLabel("Successful!");// successful show

		JButton button = new JButton("Grasp");// button

		JLabel labelShow = new JLabel("Please input date: ");// label

		GridLayout gl = new GridLayout(2, 1);// Create grid layout

		// Set basic panel layout
		jp.setLayout(gl);

		// Set panel background and bind
		jp.setBackground(Color.WHITE);
		jp1.setBackground(Color.WHITE);
		jp2.setBackground(Color.WHITE);
		setContentPane(jp);

		// Set text filed
		textDate.setText("0000年00月00日");

		// Set text area
		labelSuccess.setVisible(false);

		// Add componets to panel
		jp1.add(labelShow);
		jp1.add(textDate);
		jp1.add(button);

		jp2.add(labelSuccess);

		// Set window size
		setBounds(200, 200, 400, 400);

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
		String date;

		public void actionPerformed(ActionEvent e)
		{
			date = textDate.getText();
			Pattern p = Pattern
					.compile("[1-2][0-9][0-9][0-9]年[0-1][0-9]月[0-3][0-9]日");
			Matcher m = p.matcher(date);

			if (!m.find())
			{
				labelSuccess.setText("Failed! Wrong input!");
				labelSuccess.setVisible(true);
				return;
			}
			try
			{
				GetSpeData gsd = new GetSpeData(url);
				Print<ResultInfo> print = new Print<ResultInfo>();
				print.setLp(gsd.searchByDate(date));
				print.printToTxt(date);
				labelSuccess.setVisible(true);
			}
			catch (Exception e1)
			{
				e1.printStackTrace();
				labelSuccess.setText("Failed! Wrong input!");
				labelSuccess.setVisible(true);
				return;
			}
		}
	}
}

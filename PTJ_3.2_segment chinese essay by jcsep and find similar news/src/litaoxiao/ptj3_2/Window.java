package litaoxiao.ptj3_2;

import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class Window extends JFrame
{
	private JTextField textDate1;
	private JTextField textDate2;
	private JTextField textKeyword;
	private JLabel labelSuccess;
	private String url;

	public Window(String url)
	{
		this.url = url;

		JPanel jp = new JPanel();// Define basic panel

		JPanel jp1 = new JPanel();// The above panel
		JPanel jp2 = new JPanel();// The below panel
		JPanel jp3 = new JPanel();

		textDate1 = new JTextField(5);// input date1
		textDate2 = new JTextField(5);// input date2
		textKeyword = new JTextField(5);

		labelSuccess = new JLabel("Successful!");// successful show

		JButton button = new JButton("Similar5");// button

		JLabel labelShow = new JLabel("Please input date: ");// label
		JLabel labelKeyword = new JLabel("Please input keyword: ");

		GridLayout gl = new GridLayout(3, 1);// Create grid layout

		// Set basic panel layout
		jp.setLayout(gl);

		// Set panel background and bind
		jp.setBackground(Color.WHITE);
		jp1.setBackground(Color.WHITE);
		jp2.setBackground(Color.WHITE);
		jp3.setBackground(Color.WHITE);
		setContentPane(jp);

		// Set text filed
		textDate1.setText("0000年00月");
		textDate2.setText("0000年00月");
		textKeyword.setText("关键字");

		// Set text area
		labelSuccess.setVisible(false);

		// Add componets to panel
		jp1.add(labelShow);
		jp1.add(textDate1);
		jp1.add(textDate2);
		jp1.add(button);

		jp2.add(labelKeyword);
		jp2.add(textKeyword);

		jp3.add(labelSuccess);

		// Set window size
		setBounds(200, 200, 400, 400);

		// Add panel
		jp.add(jp1);
		jp.add(jp2);
		jp.add(jp3);

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
		String date1;
		String date2;
		String keyword;

		public void actionPerformed(ActionEvent e)
		{
			date1 = textDate1.getText();
			date2 = textDate2.getText();
			keyword = textKeyword.getText();

			Pattern p = Pattern.compile("[1-2][0-9][0-9][0-9]年[0-1][0-9]月");
			Matcher m1 = p.matcher(date1);
			Matcher m2 = p.matcher(date2);

			if (!m1.find() || !m2.find())
			{
				labelSuccess.setText("Failed! Wrong input!");
				labelSuccess.setVisible(true);
				return;
			}
			try
			{
				GetSpeData gsd = new GetSpeData(url);
				Similar sl = new Similar(gsd.searchByDate(date1, date2),
						keyword);
				List<ResultInfo> similar = sl.process();

				Print<ResultInfo> print = new Print<ResultInfo>();
				print.setLp(similar);
				print.printToTxt(date1 + "_" + date2, keyword);
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

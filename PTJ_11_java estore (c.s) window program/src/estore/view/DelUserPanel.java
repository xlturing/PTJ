package estore.view;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.border.TitledBorder;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;
import javax.swing.JTextField;
import javax.swing.JPasswordField;
import javax.swing.JButton;

import estore.dao.UserDao;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class DelUserPanel extends JFrame {
	private static final long serialVersionUID = -6832025926194594227L;
	private JPanel contentPane;
	private JTextField textField;

	private UserDao ud = new UserDao();

	/**
	 * Create the frame.
	 */
	public DelUserPanel() {
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JPanel panel = new JPanel();
		panel.setBorder(new TitledBorder(null, "Delete User",
				TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel.setBounds(10, 10, 414, 241);
		contentPane.add(panel);
		panel.setLayout(null);

		JLabel lblNewLabel = new JLabel("name: ");
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setBounds(32, 44, 109, 30);
		panel.add(lblNewLabel);

		textField = new JTextField();
		textField.setBounds(151, 49, 119, 21);
		panel.add(textField);
		textField.setColumns(10);

		JButton btnNewButton = new JButton("OK");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					String username = textField.getText();
					if (username == null || username.equals("")) {
						JOptionPane.showConfirmDialog(null,
								"name cannot empty", "Warning!",
								JOptionPane.OK_OPTION);
						return;
					}
					ud.delUserByName(username);
				}
				catch (Exception e1) {
					JOptionPane.showMessageDialog(null, "Delete fail!");
				}
				JOptionPane.showMessageDialog(null, "Delete success!");
			}
		});
		btnNewButton.setBounds(48, 153, 93, 23);
		panel.add(btnNewButton);

		JButton btnNewButton_1 = new JButton("Cancel");
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				DelUserPanel.this.dispose();
			}
		});
		btnNewButton_1.setBounds(200, 153, 93, 23);
		panel.add(btnNewButton_1);
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				DelUserPanel.this.dispose();
			}
		});
	}
}

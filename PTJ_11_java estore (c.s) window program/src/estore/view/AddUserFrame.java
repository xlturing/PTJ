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
import estore.entity.User;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class AddUserFrame extends JFrame {
	private static final long serialVersionUID = -6832025926194594227L;
	private JPanel contentPane;
	private JTextField nameField;
	private JPasswordField passwordField;
	private JTextField emailField;
	private JTextField addressField;
	private JTextField phoneField;
	private JTextField creditField;
	private JButton successButton = new JButton("OK");

	UserDao ud = new UserDao();

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					AddUserFrame frame = new AddUserFrame();
					frame.setVisible(true);
				}
				catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public AddUserFrame() {
		setBounds(100, 100, 380, 400);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JPanel panel = new JPanel();
		panel.setBorder(new TitledBorder(null, "Add User",
				TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel.setBounds(10, 10, 350, 350);
		contentPane.add(panel);
		panel.setLayout(null);

		JLabel lblNewLabel = new JLabel("user name:");
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setBounds(32, 44, 109, 30);
		panel.add(lblNewLabel);

		JLabel lblNewLabel_1 = new JLabel("password:");
		lblNewLabel_1.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_1.setBounds(32, 84, 109, 30);
		panel.add(lblNewLabel_1);

		JLabel lblEmail = new JLabel("email:");
		lblEmail.setHorizontalAlignment(SwingConstants.CENTER);
		lblEmail.setBounds(32, 124, 109, 30);
		panel.add(lblEmail);

		JLabel lblAddress = new JLabel("address:");
		lblAddress.setHorizontalAlignment(SwingConstants.CENTER);
		lblAddress.setBounds(32, 164, 109, 30);
		panel.add(lblAddress);

		JLabel lblPhone = new JLabel("phone:");
		lblPhone.setHorizontalAlignment(SwingConstants.CENTER);
		lblPhone.setBounds(32, 204, 109, 30);
		panel.add(lblPhone);

		JLabel lblCredit = new JLabel("credit card:");
		lblCredit.setHorizontalAlignment(SwingConstants.CENTER);
		lblCredit.setBounds(32, 244, 109, 30);
		panel.add(lblCredit);

		nameField = new JTextField();
		nameField.setBounds(151, 49, 119, 21);
		panel.add(nameField);
		nameField.setColumns(10);

		passwordField = new JPasswordField();
		passwordField.setBounds(151, 89, 119, 21);
		panel.add(passwordField);

		emailField = new JTextField();
		emailField.setBounds(151, 129, 119, 21);
		panel.add(emailField);

		addressField = new JTextField();
		addressField.setBounds(151, 169, 119, 21);
		panel.add(addressField);

		phoneField = new JTextField();
		phoneField.setBounds(151, 209, 119, 21);
		panel.add(phoneField);

		creditField = new JTextField();
		creditField.setBounds(151, 249, 119, 21);
		panel.add(creditField);

		JButton btnNewButton = new JButton("OK");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				User user = null;
				try {
					String username = nameField.getText().trim();
					if (username == null || username.equals("")) {
						JOptionPane.showConfirmDialog(null,
								"user name cannot be empty!", "Warning!",
								JOptionPane.OK_OPTION);
						return;
					}
					if (passwordField.getPassword().length == 0) {
						JOptionPane.showConfirmDialog(null,
								"password cannot be empty!", "Warning!",
								JOptionPane.OK_OPTION);
						return;
					}
					if (ud.checkUserName(username)) {
						JOptionPane.showConfirmDialog(null, "user name used!",
								"Warning!", JOptionPane.OK_OPTION);
						return;
					}
					String password = new String(passwordField.getPassword());
					String email = emailField.getText().trim();
					String address = addressField.getText().trim();
					String phone = phoneField.getText().trim();
					String credit = creditField.getText().trim();
					user = new User();
					user.setName(username);
					user.setPassword(password);
					user.setEmail(email);
					user.setAddress(address);
					user.setPhone(phone);
					user.setCredit(credit);
					ud.addUser(user);
				}
				catch (Exception e1) {
					JOptionPane.showConfirmDialog(null, "System error!",
							"Error!", JOptionPane.OK_OPTION);
				}
				JOptionPane.showConfirmDialog(null,
						"Add user \"" + user.getName() + "\" success",
						"System info", JOptionPane.OK_OPTION);
				AddUserFrame.this.dispose();
			}
		});
		btnNewButton.setBounds(48, 307, 93, 23);
		panel.add(btnNewButton);

		JButton btnNewButton_1 = new JButton("Cancel");
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				AddUserFrame.this.dispose();
			}
		});
		btnNewButton_1.setBounds(200, 307, 93, 23);
		panel.add(btnNewButton_1);
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				AddUserFrame.this.dispose();
			}
		});
	}
}

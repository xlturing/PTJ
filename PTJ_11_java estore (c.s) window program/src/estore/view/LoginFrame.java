package estore.view;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

import java.awt.Font;

import javax.swing.JTextField;
import javax.swing.JButton;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import javax.swing.JPasswordField;

import estore.dao.UserDao;
import estore.entity.User;
import estore.util.WindowUtil;

public class LoginFrame extends JFrame {
	private static final long serialVersionUID = -8487248434014396622L;
	private JPanel jp_password;
	private JTextField jtf_username;
	private JPasswordField passwordField;

	private UserDao ud = new UserDao();

	/**
	 * Create the frame.
	 */
	public LoginFrame() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 400, 270);
		jp_password = new JPanel();
		jp_password.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(jp_password);
		jp_password.setLayout(null);

		JLabel tileLabel = new JLabel("Welcome to E-Store!");
		tileLabel.setFont(new Font("SimSun", Font.BOLD, 14));
		tileLabel.setHorizontalAlignment(SwingConstants.CENTER);
		tileLabel.setBounds(98, 10, 231, 64);
		jp_password.add(tileLabel);

		JLabel lblNewLabel = new JLabel("user:");
		lblNewLabel.setBounds(91, 94, 60, 15);
		jp_password.add(lblNewLabel);

		JLabel lblNewLabel_1 = new JLabel("password:");
		lblNewLabel_1.setBounds(91, 119, 70, 15);
		jp_password.add(lblNewLabel_1);

		jtf_username = new JTextField();
		jtf_username.setBounds(198, 91, 115, 21);
		jp_password.add(jtf_username);
		jtf_username.setColumns(10);

		JButton btnNewButton_1 = new JButton("exit");
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});
		btnNewButton_1.setBounds(271, 177, 75, 23);
		jp_password.add(btnNewButton_1);

		passwordField = new JPasswordField();
		passwordField.setBounds(198, 116, 115, 21);
		jp_password.add(passwordField);

		JButton btnNewButton = new JButton("login");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				final String username = jtf_username.getText();
				String password = new String(passwordField.getPassword());
				if (ud.checkUser(username, password)) {
					LoginFrame.this.dispose();
					SwingUtilities.invokeLater(new Runnable() {
						@Override
						public void run() {
							User u = ud.getUserByName(username);
							MainFrame mainFrame = new MainFrame(u.getRole());
							MainFrame.user_id = u.getId();
							WindowUtil.centreWindow(mainFrame);
							mainFrame.setVisible(true);
						}
					});
				}
				else {
					JOptionPane.showConfirmDialog(LoginFrame.this,
							"Please input user name and password！", "Warning",
							JOptionPane.OK_OPTION);
				}
			}
		});
		btnNewButton.setBounds(91, 178, 75, 21);
		jp_password.add(btnNewButton);

		JButton btnLogin = new JButton("sign in");
		btnLogin.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				AddUserFrame auf = new AddUserFrame();
				WindowUtil.centreWindow(auf);
				auf.setVisible(true);
			}

		});
		btnLogin.setBounds(181, 178, 75, 21);
		jp_password.add(btnLogin);

		WindowUtil.centreWindow(this);
		setResizable(false);
	}

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					LoginFrame frame = new LoginFrame();
					frame.setVisible(true);
				}
				catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
}

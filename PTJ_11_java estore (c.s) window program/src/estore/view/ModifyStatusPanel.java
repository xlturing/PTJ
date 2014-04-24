package estore.view;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.border.TitledBorder;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;
import javax.swing.JTextField;
import javax.swing.JPasswordField;
import javax.swing.JButton;

import estore.dao.CopyDao;
import estore.dao.UserDao;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class ModifyStatusPanel extends JFrame {
	private static final long serialVersionUID = -6832025926194594227L;
	private JPanel contentPane;
	private JComboBox jcbStatus;

	private CopyDao cd = new CopyDao();

	private int id, status;
	String title;

	/**
	 * Create the frame.
	 */
	public ModifyStatusPanel(final int id, int status, String title) {
		this.id = id;
		this.status = status;
		this.title = title;

		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JPanel panel = new JPanel();
		panel.setBorder(new TitledBorder(null, "Modify Copy Status",
				TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel.setBounds(10, 10, 414, 241);
		contentPane.add(panel);
		panel.setLayout(null);

		JLabel lblNewLabel = new JLabel("Modiy copy: " + title
				+ "'s status (0/1: empty/on hold!)");
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setBounds(32, 44, 109, 30);
		panel.add(lblNewLabel);

		jcbStatus = new JComboBox();
		jcbStatus.setBounds(151, 49, 119, 21);
		panel.add(jcbStatus);
		jcbStatus.addItem(0);
		jcbStatus.addItem(1);
		jcbStatus.setSelectedItem(status);

		JButton btnNewButton = new JButton("OK");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					Integer item = (int) jcbStatus.getSelectedItem();
					cd.updateStatus(id, item);
				}
				catch (Exception e1) {
					JOptionPane.showMessageDialog(null, "Modify fail!");
				}
				JOptionPane.showMessageDialog(null, "Modify success!");
				ModifyStatusPanel.this.dispose();
			}
		});
		btnNewButton.setBounds(48, 153, 93, 23);
		panel.add(btnNewButton);

		JButton btnNewButton_1 = new JButton("Cancel");
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ModifyStatusPanel.this.dispose();
			}
		});
		btnNewButton_1.setBounds(200, 153, 93, 23);
		panel.add(btnNewButton_1);
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				ModifyStatusPanel.this.dispose();
			}
		});
	}
}

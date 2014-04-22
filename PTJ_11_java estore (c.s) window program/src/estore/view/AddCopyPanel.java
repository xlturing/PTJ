package estore.view;

import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.border.TitledBorder;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JTextArea;
import javax.swing.JButton;
import javax.swing.JSeparator;
import javax.swing.SwingConstants;
import java.awt.Color;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import javax.swing.UIManager;

import estore.dao.CopyDao;
import estore.entity.Copy;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class AddCopyPanel extends JPanel {
	private static final long serialVersionUID = 2898152244721695132L;
	private JTextField tfTitle;
	private JTextField tfFee;
	private JComboBox jcbStore;
	private JComboBox jcbType;

	private CopyDao cd = new CopyDao();

	/**
	 * Create the panel.
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public AddCopyPanel() {

		setBorder(new TitledBorder(null, "Input Copy Info",
				TitledBorder.LEADING, TitledBorder.TOP, null, null));
		setLayout(null);

		JLabel lblName = new JLabel("title:");
		lblName.setBounds(26, 34, 54, 15);
		add(lblName);

		tfTitle = new JTextField();
		tfTitle.setBounds(80, 31, 86, 21);
		add(tfTitle);
		tfTitle.setColumns(10);

		JLabel lblNewLabel = new JLabel("fee:");
		lblNewLabel.setBounds(26, 64, 54, 15);
		add(lblNewLabel);

		tfFee = new JTextField();
		tfFee.setBounds(80, 61, 86, 21);
		add(tfFee);
		tfFee.setColumns(10);

		JLabel lbStore = new JLabel("store:");
		lbStore.setBounds(26, 94, 54, 15);
		add(lbStore);

		jcbStore = new JComboBox();
		jcbStore.addItem("1");
		jcbStore.setBounds(80, 91, 40, 20);
		add(jcbStore);

		JLabel lbStore_1 = new JLabel("store id");
		lbStore_1.setBounds(125, 94, 80, 15);
		add(lbStore_1);
		
		JLabel lbType = new JLabel("type:");
		lbType.setBounds(26, 124, 54, 15);
		add(lbType);

		jcbType = new JComboBox();
		jcbType.addItem("0"); // 0 movie
		jcbType.addItem("1"); // 1 game
		jcbType.setBounds(80, 121, 40, 20);
		add(jcbType);
		
		JLabel lbType_1 = new JLabel("0/1 movie/game");
		lbType_1.setBounds(125, 121, 100, 15);
		add(lbType_1);

		JLabel lblNewLabel_1 = new JLabel("description:");
		lblNewLabel_1.setBounds(26, 154, 70, 15);
		add(lblNewLabel_1);

		final JTextArea textArea = new JTextArea();
		textArea.setBounds(30, 175, 192, 63);
		add(textArea);

		JSeparator separator = new JSeparator();
		separator.setForeground(new Color(0, 0, 0));
		separator.setBackground(new Color(0, 0, 0));
		separator.setOrientation(SwingConstants.VERTICAL);
		separator.setBounds(180, 190, 25, -145);
		add(separator);

		JButton btnNewButton = new JButton("save");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					String title = tfTitle.getText().trim();
					double fee = Double.parseDouble(tfFee.getText().trim());
					String description = textArea.getText().trim();
					int store_id = Integer.parseInt(jcbStore.getSelectedItem()
							.toString());
					int type = Integer.parseInt(jcbType.getSelectedItem()
							.toString());
					Copy c = new Copy();
					c.setTitle(title);
					c.setFee(fee);
					c.setStore_id(store_id);
					c.setType(type);
					c.setStatus(0);
					c.setDescription(description);
					cd.addCopy(c);
				}
				catch (Exception e1) {
					JOptionPane.showMessageDialog(null,
							"Please input right info!");
				}
				JOptionPane.showConfirmDialog(null, "Add copy success!",
						"System Info!", JOptionPane.OK_OPTION);
			}
		});
		btnNewButton.setBounds(158, 250, 93, 23);
		add(btnNewButton);
	}
}

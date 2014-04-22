package estore.view;

import javax.swing.JPanel;
import javax.swing.border.TitledBorder;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

import estore.dao.CopyDao;
import estore.entity.Copy;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.util.List;

public class ModifyPanel extends JPanel {
	private static final long serialVersionUID = 1751565596204642089L;
	private JTextField textField;
	private JTable table;

	private CopyDao cd = new CopyDao();

	/**
	 * Create the panel.
	 */
	public ModifyPanel() {
		setBorder(new TitledBorder(null, "Modify Copy Info",
				TitledBorder.LEADING, TitledBorder.TOP, null, null));
		setLayout(null);

		JPanel jp_topPanel = new JPanel();
		jp_topPanel.setLayout(null);
		jp_topPanel.setBorder(new TitledBorder(null, "input title",
				TitledBorder.LEADING, TitledBorder.TOP, null, null));
		jp_topPanel.setBounds(10, 21, 430, 63);
		add(jp_topPanel);

		JButton button = new JButton("find");
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				String title = textField.getText().trim();
				List<Copy> lc = cd.getCopy(title);
				if (lc != null && lc.size() > 0) {
					TableModel model = table.getModel();
					for (int i = 0, j = 0; i < model.getRowCount()
							&& j < lc.size(); i++, j++) {
						Copy c = lc.get(j);
						int k = 0;
						model.setValueAt(c.getId(), i, k++);
						model.setValueAt(c.getTitle(), i, k++);
						model.setValueAt(c.getFee(), i, k++);
						model.setValueAt(c.getDescription(), i, k++);
						model.setValueAt(c.getStatus() == 0 ? "can rent"
								: "on hold", i, k++);
						model.setValueAt(c.getType() == 0 ? "movie" : "game",
								i, k++);
						model.setValueAt(c.getStore_id(), i, k++);
					}
				}
				else {
					JOptionPane.showConfirmDialog(null,
							"sorry, cannot find this game/movie", "Warning！",
							JOptionPane.OK_OPTION);
				}

			}
		});
		button.setBounds(332, 22, 83, 23);
		jp_topPanel.add(button);

		JLabel label = new JLabel("Title:");
		label.setBounds(10, 26, 73, 15);
		jp_topPanel.add(label);

		textField = new JTextField();
		textField.setColumns(10);
		textField.setBounds(93, 23, 158, 21);
		jp_topPanel.add(textField);

		JPanel jp_bottomPanel = new JPanel();
		jp_bottomPanel.setLayout(null);
		jp_bottomPanel.setBounds(10, 94, 500, 172);
		add(jp_bottomPanel);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(0, 0, 500, 172);
		jp_bottomPanel.add(scrollPane);

		table = new JTable();
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		table.setModel(new DefaultTableModel(new Object[][] {
				{ null, null, null, null, null, null, null },
				{ null, null, null, null, null, null, null },
				{ null, null, null, null, null, null, null },
				{ null, null, null, null, null, null, null },
				{ null, null, null, null, null, null, null },
				{ null, null, null, null, null, null, null },
				{ null, null, null, null, null, null, null },
				{ null, null, null, null, null, null, null },
				{ null, null, null, null, null, null, null },
				{ null, null, null, null, null, null, null }, }, new String[] {
				"id", "title", "fee", "description", "status", "type",
				"store_id" }) {
			/**
			 * 
			 */
			private static final long serialVersionUID = -6265783904493401102L;
			boolean[] columnEditables = new boolean[] { false, false, false,
					false, false, false, false, false, false, false };

			public boolean isCellEditable(int row, int column) {
				return columnEditables[column];
			}
		});
		table.getColumnModel().getColumn(0).setPreferredWidth(59);
		scrollPane.setViewportView(table);

		JButton jbt_update = new JButton("update");
		jbt_update.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int row = table.getSelectedRow();
				if (row == -1) {
					return;
				}
				Integer in = (Integer) table.getValueAt(row, 0);
				if (in == null) {
					JOptionPane.showConfirmDialog(null,
							"please choice update row", "Warning!",
							JOptionPane.OK_OPTION);
					return;
				}
				JOptionPane.showConfirmDialog(null, "data updated！", "Info",
						JOptionPane.OK_OPTION);
			}
		});
		jbt_update.setBounds(238, 276, 93, 23);
		add(jbt_update);

	}
}

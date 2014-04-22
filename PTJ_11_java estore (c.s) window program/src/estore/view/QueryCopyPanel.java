package estore.view;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.TitledBorder;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;

import estore.dao.CopyDao;
import estore.entity.Copy;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.util.List;

public class QueryCopyPanel extends JPanel {
	private static final long serialVersionUID = -7305943467633220243L;
	private JTable table;
	private JTextField textField;
	private CopyDao cd = new CopyDao();

	/**
	 * Create the panel.
	 */
	public QueryCopyPanel() {
		setBorder(new TitledBorder(null, "Query Copy", TitledBorder.LEADING,
				TitledBorder.TOP, null, null));
		setLayout(null);

		JPanel jp_bottomPanel = new JPanel();
		jp_bottomPanel.setBounds(10, 98, 500, 176);
		add(jp_bottomPanel);
		jp_bottomPanel.setLayout(null);
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(0, 0, 500, 166);
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

		JPanel jp_topPanel = new JPanel();
		jp_topPanel.setLayout(null);
		jp_topPanel.setBorder(new TitledBorder(null,
				"Input Copy Info (if empty will return all",
				TitledBorder.LEADING, TitledBorder.TOP, null, null));
		jp_topPanel.setBounds(9, 25, 430, 63);
		add(jp_topPanel);

		JLabel label = new JLabel("Title:");
		label.setBounds(10, 26, 73, 15);
		jp_topPanel.add(label);

		textField = new JTextField();
		textField.setColumns(10);
		textField.setBounds(93, 23, 158, 21);
		jp_topPanel.add(textField);

		JButton button = new JButton("find");
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String title = textField.getText();
				List<Copy> list = cd.getCopy(title);
				if (list != null && list.size() > 0) {
					TableModel model = table.getModel();
					for (int i = 0, j = 0; i < model.getRowCount()
							&& j < list.size(); i++, j++) {
						Copy c = list.get(j);
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
	}
}

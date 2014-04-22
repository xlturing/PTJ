package estore.view;

import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;
import javax.swing.border.TitledBorder;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

import estore.dao.CopyDao;
import estore.entity.Copy;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.util.List;

public class DelCopyPanel extends JPanel {
	private static final long serialVersionUID = 2276201356704591501L;
	private JTextField textField;
	private JTable table;

	private CopyDao cd = new CopyDao();

	/**
	 * Create the panel.
	 */
	public DelCopyPanel() {
		setLayout(null);

		JPanel topPanel = new JPanel();
		topPanel.setBorder(new TitledBorder(null,
				"find copy by name (if empty will retun all)",
				TitledBorder.LEADING, TitledBorder.TOP, null, null));
		topPanel.setBounds(10, 10, 430, 63);
		add(topPanel);
		topPanel.setLayout(null);

		JButton jbt_find = new JButton("find");
		jbt_find.addActionListener(new ActionListener() {
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
		jbt_find.setBounds(332, 22, 83, 23);
		topPanel.add(jbt_find);

		JLabel jlb_goodsName = new JLabel("Title：");
		jlb_goodsName.setBounds(10, 26, 73, 15);
		topPanel.add(jlb_goodsName);

		textField = new JTextField();
		textField.setBounds(93, 23, 158, 21);
		topPanel.add(textField);
		textField.setColumns(10);

		JPanel jp_content = new JPanel();
		jp_content.setBounds(10, 84, 500, 172);
		add(jp_content);
		jp_content.setLayout(null);
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(0, 0, 500, 172);
		jp_content.add(scrollPane);

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

		JButton jbt_del = new JButton("delete");
		jbt_del.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				TableModel model = table.getModel();
				int row = table.getSelectedRow();
				if (row != -1) {
					Integer id = (Integer) model.getValueAt(row, 0);
					if (id != null)
						cd.delCopy(id);
				}
				JOptionPane.showMessageDialog(null, "Delete Success!");
			}
		});
		jbt_del.setBounds(240, 266, 105, 23);
		add(jbt_del);

	}
}

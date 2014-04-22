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
import estore.dao.LogDao;
import estore.dao.RentDao;
import estore.entity.Copy;
import estore.entity.Log;
import estore.entity.Rent;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.util.List;

public class AddRentPanel extends JPanel {
	private static final long serialVersionUID = 2276201356704591501L;
	private JTextField textField;
	private JTable table;

	private RentDao rd = new RentDao();
	private CopyDao cd = new CopyDao();
	private LogDao ld = new LogDao();

	/**
	 * Create the panel.
	 */
	public AddRentPanel() {
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

		JButton jbt_del = new JButton("rent");
		jbt_del.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					TableModel model = table.getModel();
					int row = table.getSelectedRow();
					if (row != -1) {
						Integer copy_id = (Integer) model.getValueAt(row, 0);
						if (!cd.checkStatus(copy_id))
							throw new NullPointerException();
						int user_id = MainFrame.user_id;
						if (copy_id != null) {
							Rent r = new Rent();
							r.setCopy_id(copy_id);
							r.setUser_id(user_id);
							rd.addCopy(r);
							cd.updateStatus(copy_id, 1);
							
							Log l = new Log();
							l.setStore_id(1);
							l.setUser_id(user_id);
							l.setCopy_id(copy_id);
							ld.addLog(l);
						}
					}
				}
				catch (NullPointerException e1) {
					JOptionPane
							.showMessageDialog(null, "This copy is on hold!");
					return;
				}
				catch (Exception e1) {
					JOptionPane.showMessageDialog(null, "Rent Fail!");
					return;
				}
				JOptionPane.showMessageDialog(null, "Rent Success!");
			}
		});
		jbt_del.setBounds(240, 266, 105, 23);
		add(jbt_del);

	}
}

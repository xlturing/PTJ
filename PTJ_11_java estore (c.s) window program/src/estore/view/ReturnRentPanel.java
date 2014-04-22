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

public class ReturnRentPanel extends JPanel {
	private static final long serialVersionUID = 2276201356704591501L;
	private JTable table;

	private RentDao rd = new RentDao();
	private CopyDao cd = new CopyDao();

	/**
	 * Create the panel.
	 */
	public ReturnRentPanel() {
		setLayout(null);

		JPanel topPanel = new JPanel();
		topPanel.setBorder(new TitledBorder(null, "find your rent record",
				TitledBorder.LEADING, TitledBorder.TOP, null, null));
		topPanel.setBounds(10, 10, 430, 63);
		add(topPanel);
		topPanel.setLayout(null);

		JButton jbt_find = new JButton("find");
		jbt_find.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				List<Rent> lr = rd.getRent(MainFrame.user_id);
				if (lr != null && lr.size() > 0) {
					TableModel model = table.getModel();
					for (int i = 0, j = 0; i < model.getRowCount()
							&& j < lr.size(); i++, j++) {
						Rent r = lr.get(j);
						int k = 0;
						model.setValueAt(r.getId(), i, k++);
						model.setValueAt(r.getUser_id(), i, k++);
						model.setValueAt(r.getCopy_id(), i, k++);
						model.setValueAt(r.getRent_date(), i, k++);
						model.setValueAt(r.getDue_date(), i, k++);
						model.setValueAt(r.getReturn_date(), i, k++);
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
				{ null, null, null, null, null, null },
				{ null, null, null, null, null, null },
				{ null, null, null, null, null, null },
				{ null, null, null, null, null, null },
				{ null, null, null, null, null, null },
				{ null, null, null, null, null, null },
				{ null, null, null, null, null, null },
				{ null, null, null, null, null, null },
				{ null, null, null, null, null, null },
				{ null, null, null, null, null, null }, }, new String[] {
				"rent_id", "user_id", "copy_id", "rent_date", "due_date",
				"return_date", }) {
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

		JButton jbt_del = new JButton("return");
		jbt_del.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					TableModel model = table.getModel();
					int row = table.getSelectedRow();
					if (row != -1) {
						Integer rent_id = (Integer) model.getValueAt(row, 0);
						Integer copy_id = (Integer) model.getValueAt(row, 2);
						if (rent_id != null && copy_id != null) {
							rd.returnRent(rent_id);
							cd.updateStatus(copy_id, 0);
						}
					}
				}
				catch (Exception e1) {
					JOptionPane.showMessageDialog(null, "Can't Return!");
					return;
				}
				JOptionPane.showMessageDialog(null, "Return Success!");
			}
		});
		jbt_del.setBounds(240, 266, 105, 23);
		add(jbt_del);

	}
}

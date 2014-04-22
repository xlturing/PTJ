package estore.view;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

/**
 * @author perfecking
 * @date Dec 20, 2013
 */
public class TablePanel extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1503417165622948738L;
	private JTable jtb_content;

	/**
	 * Create the panel.
	 */
	public TablePanel() {
		
		jtb_content = new JTable();
		jtb_content.setModel(new DefaultTableModel(
			new Object[][] {
				{null, null, null, null, null, null, null, null, null},
				{null, null, null, null, null, null, null, null, null},
				{null, null, null, null, null, null, null, null, null},
				{null, null, null, null, null, null, null, null, null},
				{null, null, null, null, null, null, null, null, null},
				{null, null, null, null, null, null, null, null, null},
				{null, null, null, null, null, null, null, null, null},
				{null, null, null, null, null, null, null, null, null},
				{null, null, null, null, null, null, null, null, null},
				{null, null, null, null, null, null, null, null, null},
			},
			new String[] {
				"\u540D\u79F0", "\u6570\u91CF", "\u5355\u4F4D", "\u751F\u4EA7\u5730", "\u751F\u4EA7\u65E5\u671F", "\u4FDD\u8D28\u671F", "\u8FDB\u4EF7", "\u9500\u552E\u4EF7", "\u5907\u6CE8"
			}
		) {
			/**
			 * 
			 */
			private static final long serialVersionUID = 76185700171274748L;
			boolean[] columnEditables = new boolean[] {
				false, false, false, false, false, false, false, false, false
			};
			public boolean isCellEditable(int row, int column) {
				return columnEditables[column];
			}
		});
		jtb_content.getColumnModel().getColumn(0).setPreferredWidth(80);
		jtb_content.getColumnModel().getColumn(1).setPreferredWidth(63);
		jtb_content.getColumnModel().getColumn(2).setPreferredWidth(63);
		setLayout(null);
		
		JScrollPane scrollPane = new JScrollPane(jtb_content);
		scrollPane.setBounds(0, 0, 548, 185);
		add(scrollPane);
	}

}

package estore.view;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.SwingUtilities;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JButton;

import estore.util.WindowUtil;

import java.awt.CardLayout;

public class MainFrame extends JFrame {
	private static final long serialVersionUID = 3375735874205441704L;
	private JPanel contentPane;
	private int role; // user role(customer: 0, manager: 1)
	public static int user_id;

	/**
	 * Create the frame.
	 */
	public MainFrame(int role) {
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		final JPanel jp_right = new JPanel();
		jp_right.setBounds(115, 0, 538, 304);
		final CardLayout cardLayout = new CardLayout();
		AddRentPanel addRentPanel = new AddRentPanel();
		cardLayout.addLayoutComponent(addRentPanel, "rent");
		ReturnRentPanel returnRentPanel = new ReturnRentPanel();
		cardLayout.addLayoutComponent(returnRentPanel, "return");
		AddCopyPanel addGoodsPanel = new AddCopyPanel();
		cardLayout.addLayoutComponent(addGoodsPanel, "add");
		DelCopyPanel delGoodsPanel = new DelCopyPanel();
		cardLayout.addLayoutComponent(delGoodsPanel, "del");
		ModifyPanel modifyPanel = new ModifyPanel();
		cardLayout.addLayoutComponent(modifyPanel, "modify");
		QueryCopyPanel queryPanel = new QueryCopyPanel();
		cardLayout.addLayoutComponent(queryPanel, "query");
		jp_right.setLayout(cardLayout);
		jp_right.add(addRentPanel, "rent");
		jp_right.add(returnRentPanel, "return");
		jp_right.add(addGoodsPanel, "add");
		jp_right.add(delGoodsPanel, "del");
		jp_right.add(queryPanel, "query");
		jp_right.add(modifyPanel, "modify");
		contentPane.add(jp_right);

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 672, 364);

		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);

		JMenu fileMenu = new JMenu("System");
		menuBar.add(fileMenu);

		JMenuItem mntmNewMenuItem_1 = new JMenuItem("Exit");
		mntmNewMenuItem_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});
		fileMenu.add(mntmNewMenuItem_1);
		if (role == 1) {
			JMenu mnNewMenu_1 = new JMenu("Manger");
			menuBar.add(mnNewMenu_1);

			JMenuItem mntmNewMenuItem = new JMenuItem("DelUser");
			mntmNewMenuItem.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					SwingUtilities.invokeLater(new Runnable() {

						@Override
						public void run() {
							DelUserPanel d = new DelUserPanel();
							WindowUtil.centreWindow(d);
							d.setVisible(true);
						}
					});
				}
			});
			mnNewMenu_1.add(mntmNewMenuItem);
		}

		JMenu mnNewMenu_2 = new JMenu("About");
		menuBar.add(mnNewMenu_2);

		JMenuItem mntmNewMenuItem_2 = new JMenuItem("About");
		mntmNewMenuItem_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				boolean b = SwingUtilities.isEventDispatchThread();
				System.out.println(b ? "当前为事件派发进程" : "当前不是");
				SwingUtilities.invokeLater(new Runnable() {

					@Override
					public void run() {
						AboutDialog a = new AboutDialog();
						WindowUtil.centreWindow(a);
						a.setVisible(true);
					}
				});
			}
		});
		mnNewMenu_2.add(mntmNewMenuItem_2);

		JPanel jp_left = new JPanel();
		jp_left.setBounds(0, 0, 114, 240);
		contentPane.add(jp_left);
		jp_left.setLayout(null);

		JButton jbt_rent = new JButton("Rent");
		jbt_rent.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				cardLayout.show(jp_right, "rent");
			}
		});
		jbt_rent.setBounds(10, 25, 93, 23);
		jp_left.add(jbt_rent);
		
		if(role == 0){
			JButton jbt_return = new JButton("Return");
			jbt_return.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					cardLayout.show(jp_right, "return");
				}
			});
			jbt_return.setBounds(10, 55, 93, 23);
			jp_left.add(jbt_return);
		}

		if (role == 1) {
			JButton jbt_add = new JButton("Add Goods");
			jbt_add.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					cardLayout.show(jp_right, "add");
				}
			});
			jbt_add.setBounds(10, 55, 93, 23);
			jp_left.add(jbt_add);

			JButton jbt_modify = new JButton("Modify");
			jbt_modify.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					cardLayout.show(jp_right, "modify");
				}
			});
			jbt_modify.setBounds(10, 85, 93, 23);
			jp_left.add(jbt_modify);

			JButton jbt_query = new JButton("Query");
			jbt_query.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					cardLayout.show(jp_right, "query");
				}
			});
			jbt_query.setBounds(10, 115, 93, 23);
			jp_left.add(jbt_query);

			JButton jbt_del = new JButton("Del Goods");
			jbt_del.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					cardLayout.show(jp_right, "del");
				}
			});
			jbt_del.setBounds(10, 145, 93, 23);
			jp_left.add(jbt_del);
		}
		setResizable(false);
	}

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MainFrame frame = new MainFrame(1);
					frame.setVisible(true);
				}
				catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
}

package client_server;

import java.awt.*;

import javax.swing.*;

import database.DataHandle;
import entity.Hotel;

import java.awt.event.*;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Vector;
import java.util.List;

public class Client implements Protocol {
	private JFrame jfm = new JFrame("");

	private JMenuBar jmb = new JMenuBar();
	private JMenu choose = new JMenu("choose server (press here)");
	private JMenuItem connect = new JMenuItem("connect to server");
	private JMenuItem disconnect = new JMenuItem("disconnect to server");
	private JMenuItem exit = new JMenuItem("exit");

	private JTextArea jta = new JTextArea(30, 40);
	private JScrollPane jsp = new JScrollPane(jta);
	private JTextField jtf_in = new JTextField("2014-04-04", 10);
	private JTextField jtf_out = new JTextField("2014-04-06", 10);
	private JPanel southJP = new JPanel();
	private JButton findBtn = new JButton("Find");

	private JPanel centerJP = new JPanel();
	private JLabel label = new JLabel("not connected to server");
	private JPanel rightPanel = new JPanel();
	private Vector<String> onlineUsers = new Vector<String>();
	private JList onlineUsersList = new JList(onlineUsers); // online user list
	private Vector<String> allOptions = new Vector<String>();
	private JComboBox cityComboBox = new JComboBox(allOptions);
	private JComboBox choiceComboBox = new JComboBox();
	private JButton bookBtn = new JButton("Booking");
	// private String targetUserName = "everyone";

	private String userName;
	private String serverIP;
	private int serverPort;

	private boolean isConnect = false;

	private Socket socket;
	private DataInputStream din;
	private DataOutputStream dout;

	private JDialog loginJDialog; // login window
	private JButton okButton = new JButton("OK");
	private JTextField userNameField = new JTextField("your name", 8);
	private JTextField serverIPField = new JTextField("127.0.0.1", 8);
	private JTextField serverPortField = new JTextField("8888", 8);
	private Container contentPanel;

	private DataHandle dh = new DataHandle();
	private List<Hotel> lh;

	private void init() {
		choose.add(connect);
		choose.add(disconnect);
		choose.add(exit);
		jmb.add(choose);
		jfm.setJMenuBar(jmb);
		Font f = new Font("AR PL ShanHeiSun Uni", Font.BOLD, 15);
		jta.setFont(f);
		jta.setEditable(false);

		rightPanel.setLayout(new BorderLayout());
		rightPanel.add(label, BorderLayout.NORTH);
		rightPanel.add(onlineUsersList, BorderLayout.CENTER);
		centerJP.setLayout(new BorderLayout());
		centerJP.add(jsp, BorderLayout.CENTER);
		centerJP.add(rightPanel, BorderLayout.EAST);
		jfm.add(centerJP, BorderLayout.CENTER);

		southJP.setLayout(new FlowLayout());
		JLabel tempLabel = new JLabel("please choose city:");
		southJP.add(tempLabel);
		// city options
		for (String city : dh.getCityList())
			allOptions.add(city);
		cityComboBox.setSelectedItem("everyone");
		southJP.add(cityComboBox);
		southJP.add(new JLabel("Check-in dates:"));
		southJP.add(jtf_in);
		southJP.add(new JLabel("Check-out dates:"));
		southJP.add(jtf_out);
		southJP.add(findBtn);
		southJP.add(choiceComboBox);
		southJP.add(bookBtn);
		jfm.add(southJP, BorderLayout.SOUTH);

		jfm.setVisible(true);
		jfm.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		jfm.pack();
		addListener(); // add listener
	}

	private void addListener() {
		jfm.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				closeAndExit();
			}
		});
		connect.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (isConnect) {
					JOptionPane.showMessageDialog(jfm,
							"you have logined server");
					return;
				}

				loginJDialogInit();

				try {
					socket = new Socket(serverIP, serverPort);
					din = new DataInputStream(socket.getInputStream());
					dout = new DataOutputStream(socket.getOutputStream());
					dout.writeUTF(userName + USERNAME); // user name to server

					isConnect = true; // socket connection

				}
				catch (UnknownHostException ee) {
					JOptionPane.showMessageDialog(jfm, "unknown server");
				}
				catch (IOException e1) {
					JOptionPane.showMessageDialog(jfm, "connect failure");
				}

			}
		});
		disconnect.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (!isConnect)
					return;
				send(CLIENT_EXIT); // send info to server: close connection
				closeConnect();
			}
		});
		exit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				closeAndExit();
			}
		});
		findBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String inDate;
				String outDate;
				inDate = jtf_in.getText().trim();
				outDate = jtf_out.getText().trim();
				Date in;
				Date out;
				// ensure user input right date
				// date format
				DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
				try {
					in = df.parse(inDate);
					out = df.parse(outDate);
					if (in.after(out))
						throw new SecurityException();
					if (inDate.equals("") || outDate.equals(""))
						throw new NullPointerException();
				}
				catch (NullPointerException e1) {
					JOptionPane.showMessageDialog(jfm, "please input date!");
					e1.printStackTrace();
					return;
				}
				catch (ParseException e1) {
					JOptionPane.showMessageDialog(jfm,
							"please input right date like '2014-04-11'!");
					e1.printStackTrace();
					return;
				}

				catch (SecurityException e1) {
					JOptionPane.showMessageDialog(jfm,
							"check_in date can't be after check_out date!");
					e1.printStackTrace();
					return;
				}
				String city;
				// ensure user choice a city
				try {
					city = cityComboBox.getSelectedItem().toString().trim();
				}
				catch (Exception e2) {
					JOptionPane.showMessageDialog(jfm, "please choice city!");
					e2.printStackTrace();
					return;
				}
				int count = 1;
				lh = dh.getHotelList(inDate, outDate, city);
				jta.setText("");
				choiceComboBox.removeAllItems();
				// add hotel list in window to user choice
				for (Hotel h : lh) {
					choiceComboBox.addItem(count);
					jta.append(count++ + ". Hotel name: " + h.getName()
							+ ", City: " + h.getCity() + ", room rate:"
							+ h.getRate() + "$, vacant rooms: " + h.getRooms()
							+ "\n");
				}
				// send(text); // mouse press
			}
		});
		cityComboBox.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				/*
				 * if (e.getStateChange() == ItemEvent.DESELECTED) return; //
				 * cancel operation // new choose operation targetUserName =
				 * (String) e.getItem();
				 */
			}
		});
		okButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.out.println("button listen begin");
				try {
					userName = userNameField.getText().trim();
					serverIP = serverIPField.getText().trim();
					serverPort = Integer.parseInt(serverPortField.getText());
					if (userName.equals("") || serverIP.equals(""))
						throw new Exception();
				}
				catch (Exception e1) {
					JOptionPane.showMessageDialog(jfm,
							"please input right info!");
					e1.printStackTrace();
					return;
				}
				finally {
					loginJDialog.dispose();
					loginJDialog.setVisible(false);
					System.out.println("button listen over");
				}
			}
		});
		bookBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (!isConnect) {
					JOptionPane.showMessageDialog(jfm,
							"not connected to server");
					return;
				}
				int index;
				// ensure user choice a hotel to book
				try {
					index = Integer.parseInt(choiceComboBox.getSelectedItem()
							.toString());
				}
				catch (Exception e1) {
					JOptionPane
							.showMessageDialog(jfm, "please choice a hotel!");
					e1.printStackTrace();
					return;
				}
				index--; // array begin at 0
				int id = lh.get(index).getId();
				send(userName + "," + jtf_in.getText().trim() + ","
						+ jtf_out.getText().trim() + "," + Integer.toString(id));
				jta.append("you choose hotel: " + lh.get(index).getName()
						+ " in city: " + lh.get(index).getCity()
						+ "\nplease wait...\n");
			}
		});
	} // listen over

	private void loginJDialogInit() {
		loginJDialog = new JDialog(jfm, "Welcome to Hotel Booking Broker!",
				true);
		contentPanel = new JPanel();

		contentPanel.setLayout(new GridLayout(4, 1));

		JPanel panel_1 = new JPanel();
		panel_1.add(new JLabel("user name: "));
		panel_1.add(userNameField);
		contentPanel.add(panel_1);

		JPanel panel_2 = new JPanel();
		panel_2.add(new JLabel("Server IP： "));
		panel_2.add(serverIPField);
		contentPanel.add(panel_2);

		JPanel panel_3 = new JPanel();
		panel_3.add(new JLabel("Server port: "));
		panel_3.add(serverPortField);
		contentPanel.add(panel_3);

		JPanel panel_4 = new JPanel();
		panel_4.add(okButton);
		contentPanel.add(panel_4);

		loginJDialog.add(contentPanel);
		loginJDialog.setSize(200, 200);
		loginJDialog.setVisible(true);
	}

	private void send(String text) { // send info to server, include protocol
		if (!isConnect) {
			JOptionPane.showMessageDialog(jfm, "not connected to server");
			return;
		}
		if (text.equals("") || text == null)
			return;
		try {
			// send info to the server
			dout.writeUTF(text + "," + TO_SERVER);
		}
		catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void receive() { // receive info from server
		String text = null;

		try {
			while (true) {
				if (!isConnect) {
					continue;
				}
				if ((text = din.readUTF()) != null) {
					if (text.equals(CONNECT_OK)) {
						JOptionPane.showMessageDialog(jfm, "connect success"); // connected
																				// all
						continue;
					}
					if (text.equals(USERNAME_EXIST)) {
						JOptionPane.showMessageDialog(jfm, "user name existed");
						isConnect = false;
						continue;
					}
					if (text.endsWith(USERS_LIST)) {
						getUsersList(text.substring(0, text.length()
								- USERS_LIST.length()));
						flush(); // update display
						continue;
					}
					if (text.equals(SERVER_EXIT))
						break;

					jta.append(text + "\n");
					jta.setCaretPosition(jta.getText().length() - 1);
				}
			}
		}
		catch (IOException e) {
			JOptionPane.showMessageDialog(jfm, "disconnect to server...");
			closeConnect();
		}
		finally {
			if (din != null)
				try {
					din.close();
				}
				catch (IOException e) {
					e.printStackTrace();
				}
		}
	}

	private void getUsersList(String str) { // analysize protocol to get user
											// list
		if (str.length() < 1)
			return;
		String[] temp = str.split(",");
		onlineUsers.clear();
		for (String tempName : temp) {
			onlineUsers.add(tempName);
		}
	}

	private void flush() {
		if (isConnect) {
			label.setText("current onlie people: " + onlineUsers.size());
			onlineUsersList.setListData(onlineUsers);
			allOptions.clear();
			for (String city : dh.getCityList())
				allOptions.add(city);
			jfm.setTitle(userName + ", Welcome to Hotel Booking Broker!");
		}
		else {
			label.setText("not connected to server");
			onlineUsersList.setListData(onlineUsers);
			jfm.setTitle("Hotel Booking Broker client");
			allOptions.clear();
		}
	}

	private void closeConnect() { // only close connection，but don't close
									// window
		if (socket != null)
			try {
				socket.close();
			}
			catch (IOException e1) {
				e1.printStackTrace();
			}
		isConnect = false;
		onlineUsers.clear();
		flush();
	}

	private void closeAndExit() { // close all
		if (isConnect) {
			send(CLIENT_EXIT); // close socket
		}
		closeConnect();
		jfm.dispose();
		jfm.setVisible(false);
		System.exit(0);
	}

	private void go() {
		init();
		receive();
	}

	public static void main(String[] args) {
		Client ct = new Client();
		ct.go();
	}

}

package client_server;

import java.awt.*;
import java.awt.event.*;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;

import javax.swing.*;

import database.DataHandle;
import entity.Book;
import entity.Hotel;

public class Server extends JFrame {
	private JPanel centerJP = new JPanel();
	private JTextArea jta = new JTextArea(30, 40);
	private JScrollPane jsp = new JScrollPane(jta);
	private JLabel label = new JLabel("Current online users");
	private JTextField jtf = new JTextField(20);

	private JPanel southJP = new JPanel();
	private JButton btn = new JButton("Send System Info");
	private JButton btn_hotel = new JButton("Add new hotel");

	private JList onlineUsersList = new JList(); // onlie user list
	private JPanel rightPanel = new JPanel();

	private ServerSocket ss;
	// all current valid connections
	private HashMap<String, Socket> allConnects = new HashMap<String, Socket>();

	// add a new hotel
	private JFrame jfm = new JFrame("");
	private JDialog hotelJDialog; // new hotel window
	private JButton okButton = new JButton("Add");
	private JButton successButton = new JButton("OK");
	private JTextField nameField = new JTextField("hotel name", 8);
	private JTextField cityField = new JTextField("city", 8);
	private JTextField rateField = new JTextField("100", 8);
	private JTextField roomsField = new JTextField("10", 8);
	private Container contentPanel;
	private Hotel h;

	// database handler
	private DataHandle dh = new DataHandle();

	public HashMap<String, Socket> getAllConnects() {
		return allConnects;
	}

	public JTextArea getJta() {
		return jta;
	}

	public JLabel getLabel() {
		return label;
	}

	public JList getOnlineUsersList() {
		return onlineUsersList;
	}

	// add a book record and decide whether this hotel has vacant rooms
	public boolean addBook(String userName, String inDate, String outDate,
			int id) {
		Hotel h = dh.getHotel(inDate, outDate, id);
		// if this hotel doesn't have vacant rooms return false
		if (h.getRooms() < 1)
			return false;
		// add a new book record
		Book b = new Book();
		b.setUser_name(userName);
		b.setCity(h.getCity());
		b.setHotel_name(h.getName());
		b.setIn_date(inDate);
		b.setOut_date(outDate);
		dh.addBooking(b);

		return true;
	}

	private void init() {
		setTitle("Hotel  Booking  Broker Server");
		try {
			ss = new ServerSocket(8888);

			rightPanel.setLayout(new BorderLayout());
			rightPanel.add(label, BorderLayout.NORTH);
			rightPanel.add(onlineUsersList, BorderLayout.CENTER);
			centerJP.setLayout(new BorderLayout());
			centerJP.add(jsp, BorderLayout.CENTER);
			centerJP.add(rightPanel, BorderLayout.EAST);

			Font f = new Font("AR PL ShanHeiSun Uni", Font.BOLD, 15);
			jta.setFont(f);
			jta.setEditable(false);
			add(centerJP, BorderLayout.CENTER);
			southJP.setLayout(new FlowLayout());
			southJP.add(jtf);
			southJP.add(btn);
			southJP.add(btn_hotel);
			add(southJP, BorderLayout.SOUTH);

			setVisible(true);
			setDefaultCloseOperation(EXIT_ON_CLOSE);

			addListener(); // add listener
		}
		catch (IOException e) {
			e.printStackTrace();
			System.exit(0);
		}
	}

	// add listen
	private void addListener() {
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				super.windowClosing(e);
				dispose();
				setVisible(false);
				System.exit(0);
			}
		});
		// Send some server information, such as some hotels were added
		btn.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				super.keyPressed(e);
				if (e.getKeyChar() == KeyEvent.VK_ENTER) {
					String text = "System info: " + jtf.getText().trim();
					send(text); // key press
					jtf.setText("");
					jta.append(text + "\n");
				}
			}
		});
		btn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String text = "System info: " + jtf.getText().trim();
				send(text); // mouse press
				jtf.setText("");
				jta.append(text + "\n");
			}
		});
		jtf.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String text = "System info: " + jtf.getText().trim();
				send(text); // Entry press
				jtf.setText("");
				jta.append(text + "\n");
			}
		});
		// add a new hotel
		btn_hotel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				hotelJDialogInit();
			}
		});
		okButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.out.println("Add a new hotel");
				h = new Hotel();
				try {
					h.setName(nameField.getText().trim());
					h.setCity(cityField.getText().trim());
					if (nameField.getText().trim().equals("")
							|| cityField.getText().trim().equals(""))
						throw new Exception();
					h.setRate(Double.parseDouble(rateField.getText()));
					h.setRooms(Integer.parseInt(roomsField.getText()));
					dh.addHotel(h);
				}
				catch (Exception e1) {
					JOptionPane.showMessageDialog(jfm,
							"please input right info!");
					System.out.println("Add a new hotel fail!");
					e1.printStackTrace();
					return;
				}
				finally {
					hotelJDialog.dispose();
					hotelJDialog.setVisible(false);
				}
				System.out.println("Add a new hotel success");
				JOptionPane.showMessageDialog(successButton,
						"Add hotel \"" + h.getName() + "\" success");
			}
		});
	} // add listen over

	// Add new hotel in database
	private void hotelJDialogInit() {
		hotelJDialog = new JDialog(jfm, "Add new hotel", true);
		contentPanel = new JPanel();

		contentPanel.setLayout(new GridLayout(4, 1));

		JPanel panel_1 = new JPanel();
		panel_1.add(new JLabel("Hotel name: "));
		panel_1.add(nameField);
		contentPanel.add(panel_1);

		JPanel panel_2 = new JPanel();
		panel_2.add(new JLabel("Hotel city: "));
		panel_2.add(cityField);
		contentPanel.add(panel_2);

		JPanel panel_3 = new JPanel();
		panel_3.add(new JLabel("Room rate: "));
		panel_3.add(rateField);
		contentPanel.add(panel_3);

		JPanel panel_4 = new JPanel();
		panel_4.add(new JLabel("Rooms: "));
		panel_4.add(roomsField);
		contentPanel.add(panel_4);

		JPanel panel_6 = new JPanel();
		contentPanel.add(panel_6);

		JPanel panel_5 = new JPanel();
		panel_5.add(okButton);
		contentPanel.add(panel_5);

		hotelJDialog.add(contentPanel);
		hotelJDialog.setSize(400, 200);
		hotelJDialog.setVisible(true);
	}

	private void send(String text) { // server put some information to all
										// online users
		for (Socket socket : allConnects.values()) {
			DataOutputStream dout;
			try {
				dout = new DataOutputStream(socket.getOutputStream());
				dout.writeUTF(text);
			}
			catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	private void go() {
		init();
		pack();
		receive();
	}

	private void receive() { // waiting for user connection
		try {
			while (true) {
				Socket s = ss.accept();
				Thread t = new HandleThread(s, this);
				t.start();
			}
		}
		catch (IOException e) {
			e.printStackTrace();
		}
	}

	// Server main method
	public static void main(String[] args) {
		Server myServer = new Server();
		myServer.go();
	}

}

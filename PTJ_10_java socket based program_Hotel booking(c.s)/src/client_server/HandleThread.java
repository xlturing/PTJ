package client_server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

//server handle a user request thread

public class HandleThread extends Thread implements Protocol {
	private Socket s;
	private String currentUserName;
	private Server server;

	public HandleThread(Socket s, Server server) {
		this.s = s;
		this.server = server;
	}

	public void run() { // only handle current thread and a user of a socket

		try {
			receive();
		}
		catch (IOException e) {}
		finally {
			server.getAllConnects().remove(currentUserName);
			send(currentUserName + " off line!!!");
			server.getJta().append(currentUserName + " off line!!!\n");
			flush();
			try {
				if (s != null) {
					s.close();
				} // close connection
			}
			catch (IOException e) {}

		}

	}

	private void receive() throws IOException {
		String text = null;
		DataInputStream din = new DataInputStream(s.getInputStream());
		DataOutputStream dout = new DataOutputStream(s.getOutputStream());

		while ((text = din.readUTF()) != null) { // try read client sent info

			if (text.endsWith(CLIENT_EXIT)) { // clien want to close connection
				dout.writeUTF(SERVER_EXIT); // closing connection
				break;
			}
			if (text.endsWith(USERNAME)) { // receive protocol
				currentUserName = text.substring(0,
						text.length() - USERNAME.length());
				if (server.getAllConnects().containsKey(currentUserName)) {
					dout.writeUTF(USERNAME_EXIST); // tell client user name
													// exist
					dout.writeUTF(SERVER_EXIT);
					s.close(); // teminate this connection
					currentUserName = null;
					break;
				}

				dout.writeUTF(CONNECT_OK); // tell client conenction success
				send(currentUserName + " online!!");
				server.getJta().append(currentUserName + " online!!\n");
				server.getAllConnects().put(currentUserName, s);
				flush();
				continue;
			}
			if (text.endsWith(TO_SERVER)) { // send to server
				String[] temp = text.split(",");
				if (server.addBook(temp[0], temp[1], temp[2],
						Integer.parseInt(temp[3]))) {
					dout.writeUTF("book success!");
					server.getJta().append(temp[0] + " book a room!\n");
				}
				else {
					dout.writeUTF("book failure! Sorry, hotel doesn't have vacant rooms!");
					server.getJta().append(temp[0] + " book failure!\n");
				}

			}
		}// while end
	}

	private void flush() { // flush when connections change
		server.getLabel().setText(
				"current onlie people: " + server.getAllConnects().size());
		server.getOnlineUsersList().setListData(
				server.getAllConnects().keySet().toArray());
		if (server.getAllConnects().size() > 0) {
			send(getUsersNames() + USERS_LIST);
		}
	}

	private String getUsersNames() { // 将用户列表转换为字符串,用逗号分隔每个用户
		StringBuffer buf = new StringBuffer();
		for (String temp : server.getAllConnects().keySet()) {
			buf.append(temp + ",");
		}
		if (buf.length() > 0) {
			int last = buf.length() - 1;
			buf.deleteCharAt(last);
		}
		return buf.toString();
	}

	private void send(String text) { // 当前进程向所有用户发消息,包括发送用户列表协议也是用这个方法
		for (Socket socket : server.getAllConnects().values()) {
			try {
				DataOutputStream dout = new DataOutputStream(
						socket.getOutputStream());
				dout.writeUTF(text);
			}
			catch (IOException e) {
				System.out.println("Exception!");
			}
		}
	}

	private void sendTo(String fromUser, String toUser, String text) {
		Socket toSocket = server.getAllConnects().get(toUser);
		try {
			DataOutputStream dout = new DataOutputStream(
					toSocket.getOutputStream());
			dout.writeUTF(fromUser + " say to you：" + text);
			// dout.writeUTF(fromUser+"对"+toUser+"说："+text);
		}
		catch (IOException e) {
			System.out.println("Exception!");
		}
	}

}

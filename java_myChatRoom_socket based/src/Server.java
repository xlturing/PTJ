import java.awt.*;
import java.awt.event.*;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;

import javax.swing.*;

public class Server extends JFrame{
	private JPanel centerJP = new JPanel();
	private JTextArea jta = new JTextArea(30,40);
	private JScrollPane jsp = new JScrollPane(jta);
	private JLabel label = new JLabel("当前有0人在线");
	private JTextField jtf = new JTextField(20);
	
	private JPanel southJP = new JPanel();
	private JButton btn = new JButton("群发系统消息");
	
	private JList onlineUsersList = new JList();			//在线用户列表
	private JPanel rightPanel = new JPanel();

	private ServerSocket ss ;
	private HashMap<String, Socket> allConnects = new HashMap<String, Socket>();			//所有当前的有效连接
	
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

	private void init(){
		setTitle("聊天室服务器端");
		try {
			ss = new ServerSocket(8888);

			rightPanel.setLayout(new BorderLayout());
			rightPanel.add(label,BorderLayout.NORTH);
			rightPanel.add(onlineUsersList,BorderLayout.CENTER);
			centerJP.setLayout(new BorderLayout());
			centerJP.add(jsp,BorderLayout.CENTER);
			centerJP.add(rightPanel,BorderLayout.EAST);
			
			Font f = new Font("AR PL ShanHeiSun Uni",Font.BOLD,15);
			jta.setFont(f);
			jta.setEditable(false);		
			add(centerJP,BorderLayout.CENTER);
			southJP.setLayout(new FlowLayout());
			southJP.add(jtf);
			southJP.add(btn);
			add(southJP,BorderLayout.SOUTH);
			
			setVisible(true);
			setDefaultCloseOperation(EXIT_ON_CLOSE);
			
			addListener();			//添加监听器
		}catch (IOException e) {
			e.printStackTrace();
			System.exit(0);
		}
	}
	
	//添加监听
	private void addListener(){
		addWindowListener(new WindowAdapter()	{
			@Override
			public void windowClosing(WindowEvent e) {
				super.windowClosing(e);
				dispose();
				setVisible(false);
				System.exit(0);
			}
		});
		btn.addKeyListener(new KeyAdapter(){
			@Override
			public void keyPressed(KeyEvent e) {
				super.keyPressed(e);
				if(e.getKeyChar()==KeyEvent.VK_ENTER){
					String text = "系统消息："+jtf.getText().trim();
				send(text);		//键盘按下发送键(Entry)时向所有客户端发送
				jtf.setText("");
				jta.append(text+"\n");	//服务器显示自己的消息
				}
			}
		});
		btn.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				String text = "系统消息："+jtf.getText().trim();
				send(text);		//鼠标按下发送键时向所有客户端发送
				jtf.setText("");
				jta.append(text+"\n");	//服务器显示自己的消息
			}
		});
		jtf.addActionListener(new ActionListener(){			//文本区的语义事件包括使用Enter键
			public void actionPerformed(ActionEvent e){
				String text = "系统消息："+jtf.getText().trim();
				send(text);		//在文本区编辑时按下Entry向所有客户端发送
				jtf.setText("");
				jta.append(text+"\n");	//服务器显示自己的消息
			}
		});
	}			//添加监听结束
	
	private void send(String text){			//服务器向所有用户群发系统消息的方法
		for(Socket socket:allConnects.values()){
			DataOutputStream dout;
			try {
				dout = new DataOutputStream(socket.getOutputStream());
				dout.writeUTF(text);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	private void go(){
		init();		//这三行的顺序不能换！！！
		pack();
		receive();
	}
	private void receive(){		//等待客户端连接
		try {
			while(true){
				Socket s = ss.accept();
				Thread t = new HandleThread(s,this);
				t.start();
			}
		}catch (IOException e) {
				e.printStackTrace();
		}
	}
	//服务器主函数
	public static void main(String[] args){
		Server myServer = new Server();
		myServer.go();
	}

	

}

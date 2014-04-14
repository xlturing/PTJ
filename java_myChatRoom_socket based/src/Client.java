import java.awt.*;
import javax.swing.*;
import java.awt.event.*;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Vector;

public class Client implements Protocol{
	private JFrame jfm = new JFrame("");

	private JMenuBar jmb = new JMenuBar();
	private JMenu	choose = new JMenu("选择服务器");
	private JMenuItem connect = new JMenuItem("连接服务器");
	private JMenuItem disconnect = new JMenuItem("断开服务器");
	private JMenuItem exit = new JMenuItem("退出");
	
	private JTextArea jta = new JTextArea(30,40);
	private JScrollPane jsp = new JScrollPane(jta);
	private JTextField jtf = new JTextField(20);
	private JPanel southJP = new JPanel();
	private JButton btn = new JButton("发送");
	
	private JPanel centerJP = new JPanel();
	private JLabel label = new JLabel("未连接服务器");
	private JPanel rightPanel = new JPanel();
	private Vector<String> onlineUsers = new Vector<String>();			
	private JList onlineUsersList = new JList(onlineUsers);			//在线用户列表
	private Vector<String> allOptions = new Vector<String>();
	private JComboBox userComboBox = new JComboBox(allOptions);			//聊天对象下拉列表
	private String targetUserName = "所有人";			//选中的聊天对象
	
	private String userName;
	private String serverIP;
	private int serverPort;
	
	private boolean isConnect = false;
	
	private Socket socket;
	private DataInputStream din;
	private DataOutputStream dout;
	
	
	private JDialog loginJDialog;			//登录窗口 
	private JButton okButton=new JButton("确定");
	private JTextField userNameField=new JTextField("ww",8);
	private JTextField serverIPField=new JTextField("127.0.0.1",8);
	private JTextField serverPortField=new JTextField("8888",8);
	private Container contentPanel;
	
	private void init(){
		choose.add(connect);
		choose.add(disconnect);
		choose.add(exit);
		jmb.add(choose);
		jfm.setJMenuBar(jmb);
		Font f = new Font("AR PL ShanHeiSun Uni",Font.BOLD,15);
		jta.setFont(f);
		jta.setEditable(false);		//设定文本显示区能否编辑
		
		rightPanel.setLayout(new BorderLayout());
		rightPanel.add(label,BorderLayout.NORTH);
		rightPanel.add(onlineUsersList,BorderLayout.CENTER);
		centerJP.setLayout(new BorderLayout()	);
		centerJP.add(jsp,BorderLayout.CENTER);
		centerJP.add(rightPanel,BorderLayout.EAST);
		jfm.add(centerJP,BorderLayout.CENTER);
		
		southJP.setLayout(new FlowLayout());
		JLabel tempLabel = new JLabel("请选择：");
		southJP.add(tempLabel);
		allOptions.add("所有人");
		userComboBox.setSelectedItem("所有人");
		southJP.add(userComboBox);
		southJP.add(jtf);
		southJP.add(btn);
		jfm.add(southJP,BorderLayout.SOUTH);
		
		jfm.setVisible(true);
		jfm.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		jfm.pack();
		addListener();			//添加监听器
	}
	private void addListener(){
		jfm.addWindowListener(new WindowAdapter()	{
			@Override
			public void windowClosing(WindowEvent e) {
				closeAndExit();
			}
		});
		connect.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				if(isConnect){
					JOptionPane.showMessageDialog(jfm, "您已经登录服务器");
					return;
				}
				
				loginJDialogInit();
				
				try {
					socket = new Socket(serverIP,serverPort);
					din = new DataInputStream(socket.getInputStream());
					dout = new DataOutputStream(socket.getOutputStream());
					dout.writeUTF(userName+USERNAME);	//将用户名传给服务器,用协议（暗号）

					isConnect = true;		//此时只是可以socket可以通信，并不是真正的连接上
					
				} catch(UnknownHostException ee){
					JOptionPane.showMessageDialog(jfm,"未知的主机名");
				}catch (IOException e1) {
					JOptionPane.showMessageDialog(jfm,"连接失败");
				}
				
			}
		});
		disconnect.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				if(!isConnect)return;
				send(CLIENT_EXIT);				//通知服务器客户端将关闭socket
				closeConnect();
			}
		});
		exit.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				closeAndExit();
			}
		});
		btn.addKeyListener(new KeyAdapter(){
			@Override
			public void keyPressed(KeyEvent e) {
				super.keyPressed(e);
				if(e.getKeyChar()==KeyEvent.VK_ENTER){
				String text = jtf.getText().trim();
				send(text);		//键盘按下发送键(Entry)时向服务器发送
				jtf.setText("");
				}
			}
		});
		btn.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				String text = jtf.getText().trim();
				send(text);		//鼠标按下发送键时向服务器发送
				jtf.setText("");
			}
		});
		jtf.addActionListener(new ActionListener(){			//文本区的语义事件包括使用Enter键
			public void actionPerformed(ActionEvent e){
				String text = jtf.getText().trim();
				send(text);		//在文本区编辑时按下Entry向服务器发送
				jtf.setText("");
			}
		});
		userComboBox.addItemListener(new ItemListener(){			//下拉列表监听
			public void itemStateChanged(ItemEvent e) {
				if(e.getStateChange()==ItemEvent.DESELECTED)return;		//选项被取消选择时没有操作
				//新选项被选择时发生操作
				targetUserName = (String)e.getItem();
			}
		});
		okButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				System.out.println("按钮监听开始");
				userName = userNameField.getText().trim();
				serverIP = serverIPField.getText().trim();
				serverPort = Integer.parseInt(serverPortField.getText());
				loginJDialog.dispose();
				loginJDialog.setVisible(false);
				System.out.println("按钮监听结束");
			}
		});
	}			//监听结束
	
	private void loginJDialogInit(){
		loginJDialog = new JDialog(jfm,"欢迎登录",true);
		contentPanel = new JPanel();
		
		contentPanel.setLayout(new GridLayout(4,1));

		JPanel panel_1 = new JPanel();
		panel_1.add(new JLabel("用    户    名:"));
		panel_1.add(userNameField);
		contentPanel.add(panel_1 );
		
		JPanel panel_2 = new JPanel();
		panel_2.add(new JLabel("服 务 器   IP："));
		panel_2.add(serverIPField);
		contentPanel.add(panel_2 );
		
		JPanel panel_3 = new JPanel();
		panel_3.add(new JLabel("服务器端口号:"));
		panel_3.add(serverPortField);
		contentPanel.add(panel_3);
		
		JPanel panel_4 = new JPanel();
		panel_4.add(okButton);
		contentPanel.add(panel_4);
		
		loginJDialog.add(contentPanel);
		loginJDialog.setSize(200, 200);
		loginJDialog.setVisible(true);
	}
	private void send(String text){		//向服务器发送消息,包括发送协议
		if(!isConnect){
			JOptionPane.showMessageDialog(jfm, "还没有连接服务器");
			return;
		}
		if(text.equals("")||text==null)return;
		try {
			if(targetUserName==null){
				JOptionPane.showMessageDialog(jfm, "请选择发送对象");
				targetUserName = "所有人";
				return;
			}
			if(targetUserName.equals("所有人")){		//发给所有用户
				dout.writeUTF(text);
			}else{		//发给指定用户
				dout.writeUTF(targetUserName+","+text+","+TO_SOMEBODY);
			}
		} catch (IOException e) {	
			
		}
	}
	private void receive(){		//接受从服务器发来的消息
		String text = null;

		try {
			while(true){
				if(!isConnect){
					continue;
				}
				 if((text = din.readUTF())!=null){
					 if(text.equals(CONNECT_OK)){
							JOptionPane.showMessageDialog(jfm,"连接成功");		//此时才算真正的连接成功，可以发送消息了
							continue;
					 }
					 if(text.equals(USERNAME_EXIST)){
							JOptionPane.showMessageDialog(jfm,"用户名已经存在");
							isConnect = false;
							continue;
					 }
					 if(text.endsWith(USERS_LIST)){				//如果收到用户名列表协议且用户列表非空
						 getUsersList(text.substring(0,text.length()-USERS_LIST.length()));
						 flush();				//刷新显示
						 continue;
					 }
					if(text.equals(SERVER_EXIT))break;		//如果监听到服务器要断开与客户端连接，跳转到finally
					
					//到达此处是正常通信时
					 jta.append(text+"\n");
					 jta.setCaretPosition(jta.getText().length()-1);
				 }
			}
		}catch (IOException e) {
			JOptionPane.showMessageDialog(jfm,"与服务器的连接被关闭");
			closeConnect();
		}finally{
			if(din!=null)try {	din.close();} catch (IOException e) {	e.printStackTrace();}
		}
	}
	private void getUsersList(String str){					//分析协议得到在线用户列表
		if(str.length()<1)return;
		String[] temp = str.split(",");
		onlineUsers.clear();
		for(String tempName:temp){
			onlineUsers.add(tempName);
		}
	}
	private void flush(){
		if(isConnect){
			label.setText("当前有"+onlineUsers.size()+"人在线");	
			onlineUsersList.setListData(onlineUsers);
			allOptions.clear();
			allOptions.add("所有人");
			allOptions.addAll(onlineUsers);
			allOptions.remove(userName);
			jfm.setTitle(userName+"，欢迎您来到聊天室");
		}else{
			label.setText("还没有连接服务器");	
			onlineUsersList.setListData(onlineUsers);
			jfm.setTitle("聊天室客户端");
			allOptions.clear();
		}
	}
	private void closeConnect(){				//仅仅关闭连接，并不退出客户端
		if(socket!=null)
			try {
				socket.close();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		isConnect = false;
		onlineUsers.clear();
		flush();
	}
	private void closeAndExit(){			//关闭连接退出客户端
		if(isConnect){
			targetUserName="所有人";
			send(CLIENT_EXIT);				//通知服务器客户端将关闭socket
		}
		closeConnect();
		jfm.dispose();
		jfm.setVisible(false);
		System.exit(0);
	}
	private void go(){
		init();		//顺序不能换！！！
		receive();
	}
	public static void main(String[] args) {
		Client ct = new Client();
		ct.go();
	}
	
}

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

//服务器中处理某个客户请求的线程

public class HandleThread extends Thread implements Protocol{		
	private Socket s;
	private String currentUserName;
	private Server server;
	
	public HandleThread(Socket s,Server server) {
		this.s = s;
		this.server = server;
	}
	public void run(){			//只处理当前线程与客户端的一个Socket连接
		
		try {
			receive();
		} catch (IOException e) {
		}finally{
			server.getAllConnects().remove(currentUserName);
			send(currentUserName+" 下线了！！！");
			server.getJta().append(currentUserName+" 下线了\n");
			flush();
			try{ 
				if(s!=null){	s.close(); }		//关闭连接
			}catch(IOException e){}
			
		}
		
	}
	private void receive()throws IOException{
		String text = null;
		DataInputStream din = new DataInputStream(s.getInputStream());		
		DataOutputStream dout = new DataOutputStream(s.getOutputStream());
		
		while((text=din.readUTF())!=null){			//尝试读取客户端发过来的信息
			
				if(text.endsWith(CLIENT_EXIT)){			//收到协议,客户端要关闭连接
					dout.writeUTF(SERVER_EXIT);			//发出协议，即将关闭连接					
					break;				//方法结束，
				}
				if(text.endsWith(USERNAME)){			//收到协议，这是名字
					currentUserName = text.substring(0, text.length()-USERNAME.length());
					if(server.getAllConnects().containsKey(currentUserName)){
						dout.writeUTF(USERNAME_EXIST);			//通知客户端用户名已经存在
						dout.writeUTF(SERVER_EXIT);
						s.close();		//终止本次连接请求
						currentUserName = null;
						break;			//方法结束，客户端连接失败
					}
					
					dout.writeUTF(CONNECT_OK);			//通知客户端连接成功
					send(currentUserName+" 上线了！！");
					server.getJta().append(currentUserName+" 上线了！！\n");
					server.getAllConnects().put(currentUserName, s);
					flush();
					continue;
				}
				if(text.endsWith(TO_SOMEBODY)){				//如果收到协议是私聊信息
					String[] temp = text.split(",");
					sendTo(currentUserName, temp[0], temp[1]);
					dout.writeUTF("您对 "+temp[0]+" 说："+temp[1]);		//私聊信息在自己的客户端上也要显示
					server.getJta().append(currentUserName+" 对 "+temp[0]+" 说："+temp[1]+"\n");	
					
				}else{						//不是私聊信息,是发给所有人的
					text = currentUserName+" 对大家说："+text;
					send(text);
					server.getJta().append(text+"\n");	
				}
				
		}//while循环结束
	}

	private void flush(){				//每次当连接数有变化时都要刷新
		server.getLabel().setText("当前有"+server.getAllConnects().size()+"人在线");	
		server.getOnlineUsersList().setListData(server.getAllConnects().keySet().toArray());
		if(server.getAllConnects().size()>0){
			send(getUsersNames()+USERS_LIST);
		}
	}
	private String getUsersNames(){					//将用户列表转换为字符串,用逗号分隔每个用户
		StringBuffer buf = new StringBuffer();
		for(String temp:server.getAllConnects().keySet()){
			buf.append(temp+",");
		}
		if(buf.length()>0){
			int last = buf.length()-1;
			buf.deleteCharAt(last);
		}
		return buf.toString();
	}
	private void send(String text) {		//当前进程向所有用户发消息,包括发送用户列表协议也是用这个方法
		for(Socket socket:server.getAllConnects().values()){
			try {
				DataOutputStream dout = new DataOutputStream(socket.getOutputStream());
				dout.writeUTF(text);
			} catch (IOException e) {
				System.out.println("服务器进程向所有客户写信息时发生异常");
			}
		}
	}
	private void sendTo(String fromUser,String toUser,String text){
		//对某个人发消息
		Socket toSocket = server.getAllConnects().get(toUser);
		try {
			DataOutputStream dout = new DataOutputStream(toSocket.getOutputStream());
			dout.writeUTF(fromUser+" 对您说："+text);
//			dout.writeUTF(fromUser+"对"+toUser+"说："+text);
		} catch (IOException e) {
			System.out.println("服务器进程向指定客户写信息时发生异常");
		}
	}
	
}			

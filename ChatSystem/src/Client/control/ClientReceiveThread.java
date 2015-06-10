package Client.control;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Date;
import java.util.Map;

import Client.ui.ClientChat;
import Client.ui.ClientMain;
import Exception.BusinessException;
import Exception.RemoteException;
import Network.NetworkCommand;
import model.Friend;
import model.Message;
import model.User;


public class ClientReceiveThread extends Thread {
	// 和本线程相关的Socket
	public static Map<String,ClientChat> currentchat=ClientMain.chatUI;
	Socket socket = null;

	public ClientReceiveThread(Socket socket) {
		this.socket = socket;
	}
	
	//线程执行的操作，响应客户端的请求
	public void run(){
		Message msg=new Message();
		BufferedReader br=null;
		OutputStream os=null;
		PrintWriter pw=null;
		try {
			//获取输入流，并读取客户端信息
			ObjectInputStream ois = new ObjectInputStream(  
                    new BufferedInputStream(socket.getInputStream())); //添加缓冲   
            Object obj = ois.readObject();  //读取缓冲对象
            msg =(Message)obj;
			System.out.println(msg.getFromUser());
			if(currentchat.get(msg.getFromUser())!=null){
				//如果窗口已经被创建，显示窗口并显示聊天信息
				System.out.println(msg.getFromUser()+"null");
				currentchat.get(msg.getFromUser()).setVisible(true);
				currentchat.get(msg.getFromUser()).showMessage(msg);
			}
			else{
				//如果窗口没有被创建，新建聊天窗口
				//System.out.println(msg.getFromUser());
				User currentfriend=NetworkCommand.getServer().SearchFriend(msg.getFromUser());
				//System.out.println(currentfriend.getUserId()+"结果");
				currentchat.put(msg.getFromUser(), new ClientChat(currentfriend));
				currentchat.get(msg.getFromUser()).setTitle(msg.getFromUser());
				currentchat.get(msg.getFromUser()).setVisible(true);
				currentchat.get(msg.getFromUser()).showMessage(msg);
			}
			socket.shutdownInput();//关闭输入流
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (BusinessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			//关闭资源
			try {
				if(pw!=null)
					pw.close();
				if(os!=null)
					os.close();
				if(br!=null)
					br.close();
				if(socket!=null)
					socket.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
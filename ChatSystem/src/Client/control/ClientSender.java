package Client.control;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

import javax.swing.JOptionPane;

import model.*;

public class ClientSender extends Thread{
	Socket socket = null;
	static User currentfriend = new User();
	static User currentuser=new User();
	static Message message = new Message();

	public ClientSender(User friend,User me, Message msg) {
		currentfriend = friend;
		currentuser=me;
		message = msg;
		Socket socket;
	}
	public void run(){
		try {
//			System.out.println(currentfriend.getRecentLoginIp());
//			System.out.println(currentfriend.getRecentLoginPort());
			socket = new Socket(currentfriend.getRecentLoginIp(),
					currentfriend.getRecentLoginPort());
			// 获取输出流，向服务器端发送信息
            ObjectOutputStream oos = new ObjectOutputStream(socket  
                    .getOutputStream());  
            message.setFromUser(currentuser.getUserId());
            message.setToUser(currentfriend.getUserId());
            //输入对象, 并刷新缓存  
            oos.writeObject(message);  
            oos.flush();  
			socket.shutdownOutput();// 关闭输出流
			//关闭资源
			socket.close();
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("无法连接");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}

package Client.control;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

import model.Message;
import model.User;
import Client.control.*;

public class ClientListner extends Thread{
	User currentuser=new User();
	Message message=new Message();
	
	public ClientListner(User me) {
		currentuser=me;
	}
	public void run(){
		try {
			//1.创建一个服务器端Socket，即ServerSocket，指定绑定的端口，并监听此端口
			ServerSocket serverSocket=new ServerSocket(currentuser.getRecentLoginPort());
			Socket socket=null;
			System.out.println(currentuser.getRecentLoginPort()+"端口监听已经启动");
			//循环监听等待客户端的连接
			while(true){
				//调用accept()方法开始监听，等待客户端的连接
				socket=serverSocket.accept();
				//创建一个新的接收线程
				ClientReceiveThread serverThread=new ClientReceiveThread(socket);
				//启动线程
				serverThread.start();
				
//				count++;//统计客户端的数量
//				System.out.println("客户端的数量："+count);
//				InetAddress address=socket.getInetAddress();
//				System.out.println("当前客户端的IP："+address.getHostAddress());
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}

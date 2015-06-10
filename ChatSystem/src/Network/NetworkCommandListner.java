package Network;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import Exception.BusinessException;
import NetworkDealer.NetworkCommandDealer;
import model.*;


/**
 * 
 * @服务端接收数据
 */
public class NetworkCommandListner extends Thread {
	private Socket sock = null;
	public NetworkCommandListner(Socket sc) {
		this.sock = sc;
	}
	public void run() {		
		ObjectInputStream in = null;
		ObjectOutputStream out = null;
		try {
			in = new ObjectInputStream(sock.getInputStream());
			out = new ObjectOutputStream(sock.getOutputStream());
			try {
				//从客户端读取网络请求信息
				NetworkPackage cmd = (NetworkPackage) in.readObject();
				//请求信息中包括处理该次请求的类名，通过该类名反射加载处理类，并建立该类的实例
				Class dealClass = Class.forName(cmd.getDealerClassName());
				//创建NetworkCommandDealer这个抽象类的一个实例
				NetworkCommandDealer dealer = (NetworkCommandDealer) dealClass.newInstance();
				//提取客户端ip地址
				String fromIp=sock.getInetAddress().getHostAddress();
				//处理该次请求
				Object result = dealer.dealCommand(fromIp,cmd.getParam1(), cmd
						.getParam2(), cmd.getParam3());
				//返回处理结果
				out.writeObject(result);
			} catch (Exception e) {
				if(!(e instanceof BusinessException ))
						e.printStackTrace();
				//如果发生异常，则将异常信息也输出到客户端
				out.writeObject(e);
			}
			out.close();

		} catch (IOException ex) {
		}
	}
	
}

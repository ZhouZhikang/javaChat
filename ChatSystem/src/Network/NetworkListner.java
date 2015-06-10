package Network;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import Exception.SystemException;



public class NetworkListner extends Thread{
	
	private boolean isActive = true;
    public static int currentPort=0;
    private ServerSocket server;

    public NetworkListner(int minPort,int maxPort)throws SystemException{
    	for (currentPort = minPort; currentPort<= maxPort; currentPort++) {
			try {
				server = new ServerSocket(currentPort);
				break;
			} catch (IOException ex) {				
			}
		}
		if(currentPort>maxPort){
			throw new SystemException("启动网络监听端口失败");
		}
    }
    
    public void stopThread(){
        this.isActive=false;
        try {
			server.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
	@Override
	public void run() {
		// TODO Auto-generated method stub
		while (isActive) {
			Socket sc = null;
            try {            	
                sc = server.accept();
            } catch (IOException ex1) {
            	
            }
            //当有客户程序连接该端口后,马上启动一个线程用于与客户通信,该线程可以继续监听
            if (sc != null) {
                (new NetworkCommandListner(sc)).start();
            }
        }
	}
}

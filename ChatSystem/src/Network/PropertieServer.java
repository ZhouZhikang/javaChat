package Network;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;


/**
 * 
 * @获取远程服务器的配置信息
 */
public class PropertieServer {
	static String serverIp = null;
	static int serverPort = 0;
	
	static
	{
		Properties serverProperties = new Properties();
		try {
			serverProperties.load(new FileInputStream("server.properties"));
			serverIp = serverProperties.getProperty("serverIp");
			serverPort = Integer.parseInt(serverProperties.getProperty("serverPort"));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static String getServerIp() {
		return PropertieServer.serverIp;
	}

	public static int getServerPort() {
		return PropertieServer.serverPort;
	}
	
}

package Network;

import java.io.File;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import Exception.BusinessException;
import Exception.RemoteException;
import model.Friend;
import model.Message;
import model.NetworkPackage;
import model.User;



public class NetworkCommand {

	private static NetworkCommand messageServer = null;

	private User currentUser = null;
	private User toUser = null;
	private String serverip = null;
	private int serverport = 0;
	private Socket sock = null;

	private NetworkCommand(String ip, int port) {
		serverip = ip;
		serverport = port;
	}

	/*
	 * 获得远程服务器
	 */
	public static synchronized NetworkCommand getServer() {
		if (messageServer == null) {
			messageServer = new NetworkCommand(PropertieServer.getServerIp(),
					PropertieServer.getServerPort());
		}
		return messageServer;
	}

	/*
	 * 获得好友所在服务端
	 */
	public static synchronized NetworkCommand getFriend(User friend) {
		return new NetworkCommand(friend.getRecentLoginIp(), friend
				.getRecentLoginPort());
	}

	/*
	 * 连接服务器
	 */
	private void connectToServer() throws RemoteException {
		try {
			sock = new Socket(serverip, serverport);
		} catch (Exception e) {
			throw new RemoteException("无法连接服务器" + serverip + ",端口："
					+ serverport);
		}
	}

	private void disconnectFromServer() {
		try {
			sock.close();
		} catch (IOException e) {
			// TODO 自动生成 catch 块
			e.printStackTrace();
		}
		sock = null;
	}

	/*
	 * docmd方法用于处理：发送一个命令，并获得返回
	 */
	private Object docmd(String dealerClassName, Object param1, Object param2,
			Object param3) throws RemoteException, BusinessException {
		this.connectToServer();
		ObjectInputStream in = null;
		ObjectOutputStream out = null;
		// 封装网络请求包
		NetworkPackage cmd = new NetworkPackage();
		cmd.setParam1(param1);
		cmd.setParam2(param2);
		cmd.setParam3(param3);
		cmd.setDealerClassName(dealerClassName);
		try {
			out = new ObjectOutputStream(sock.getOutputStream());
			out.writeObject(cmd);// 发送命令对象
			in = new ObjectInputStream(sock.getInputStream());
			Object result = (Object) in.readObject();// 获得返回对象
			// 服务器处理结束后可能传回结果信息或异常信息
			if (result != null) {
				if (result instanceof BusinessException)
					throw (BusinessException) result;
				else if (result instanceof Exception) {
					((Exception) result).printStackTrace();
					throw new RemoteException("服务器错误");
				} else
					return result;
			}
			return null;

		} catch (IOException ex) {
			throw new RemoteException("网络读写错误！");
		} catch (ClassNotFoundException e) {
			// TODO 自动生成 catch 块
			throw new RemoteException("服务器返回类型错误！");
		} finally {
			this.disconnectFromServer();
		}

	}

	private Object docmd(String dealerClassName, Object param1, Object param2)
			throws RemoteException, BusinessException {
		return this.docmd(dealerClassName, param1, param2, null);
	}

	private Object docmd(String dealerClassName, Object param)
			throws RemoteException, BusinessException {
		return this.docmd(dealerClassName, param, null, null);
	}

	public User getCurrentUser() {
		return currentUser;
	}

	public void setCurrentUser(User currentUser) {
		this.currentUser = currentUser;
	}

	public User getToUser() {
		return toUser;
	}

	public void setToUser(User toUser) {
		this.toUser = toUser;
	}

	public synchronized void checkLogin(User user) throws RemoteException,
			BusinessException {
		this.currentUser = (User) this
				.docmd(
						"NetworkDealer.LoginDealer",
						user);
		//System.out.println(user.getRecentLoginPort());
	}

	public synchronized List<Friend> LoadFriend(User user)throws RemoteException, BusinessException{
		List<Friend> result = (List<Friend>) this.docmd("NetworkDealer.LoadFriendDealer",user);
		return result;
	}

	public synchronized void logout() throws RemoteException, BusinessException {
		this.docmd("NetworkDealer.LogoutDealer",
				this.getCurrentUser());
	}

	public synchronized void sendOfflineMessage(Message msg) throws RemoteException,
			BusinessException {
		this.docmd("NetworkDealer.SendOfflineMessageDealer",	msg);
	}
	
	public synchronized List<Message> receiveOfflineMessage(String from,String to) throws RemoteException, BusinessException
	{
		List<Message> result =  (List<Message>) this.docmd("NetworkDealer.ReceiveOfflineMessageDealer",from,to);
		return result;
	}
	
	public synchronized void Register(User user)throws RemoteException, BusinessException
	{
		this.currentUser = (User)this.docmd("NetworkDealer.RegisterDealer",user);
	}
	
	public synchronized User SearchFriend(String id)throws RemoteException, BusinessException
	{
		User result = (User)this.docmd("NetworkDealer.SearchFriendDealer",id);
		//System.out.println(result.getUserId());
		return result;
	}
	
	public synchronized void addFriend(String uid1,String uid2)throws RemoteException, BusinessException
	{
		this.docmd("NetworkDealer.AddFriendDealer",	uid1,uid2);
		System.out.println(uid2+"加"+uid1+"为好友");
	}
	
	public synchronized void deleteFriend(User currentuser,User currentfriend)throws RemoteException, BusinessException
	{
		this.docmd("NetworkDealer.DeleteFriendDealer",currentuser,currentfriend);
		//System.out.println(currentuser+"删除好友"+currentfriend);
	}
	
	public synchronized void updateFriend(String uid1,String uid2)throws RemoteException, BusinessException
	{
		this.docmd("NetworkDealer.UpdateFriendDealer",uid1,uid2);
	}
	
	public synchronized void deleteMessage(Message msg)throws RemoteException, BusinessException
	{
		this.docmd("NetworkDealer.DeleteMessageDealer",msg);
		//System.out.println(currentuser+"删除好友"+currentfriend);
	}
	
}
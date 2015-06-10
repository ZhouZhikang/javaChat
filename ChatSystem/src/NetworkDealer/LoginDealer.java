package NetworkDealer;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import model.Friend;
import model.User;
import dao.FriendDAO;
import dao.UserDAO;
import Exception.*;
import Network.NetworkListner;

public class LoginDealer extends NetworkCommandDealer {

	@Override
	public Object dealCommand(String fromIp, Object param1, Object param2,
			Object param3) throws BusinessException, SystemException,
			DBOperatorException {
		// TODO Auto-generated method stub
		User user = (User) param1;
		User result = UserDAO.getInstance().loadbyuserid(user.getUserId());
		if (result == null)
			throw new BusinessException("帐号不正确");
		if (!user.getUserPwd().equals(result.getUserPwd()))
			throw new BusinessException("密码错误");
		// dao.loadUserFriends(result);
		// 保存用户的当前登录地址、端口信息

		result.setRecentLoginIp(fromIp);
		ServerSocket server=null;
		int port = 0;
		for (int i = 9528; i < 16000; i++) {
			try {
				server = new ServerSocket(i);
				port = i;
				break;
			} catch (IOException e) {
				// TODO Auto-generated catch block
				continue;
			}
		}
		try {
			server.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		result.setRecentLoginPort(port);
		result.setRecentLogoutDate(null);
		result.setIsOnline(true);
		List<Friend> list = FriendDAO.getInstance().loadByFriendId(
				user.getUserId());
		for (int i = 0; i < list.size(); i++) {
			list.get(i).setIsOnline(true);
			FriendDAO.getInstance().update(list.get(i));
		}
		UserDAO.getInstance().update(result);
		// 记录最近登录时间，ip，端口
		return result;

	}
}
package NetworkDealer;

import java.util.Date;
import java.util.List;

import model.Friend;
import model.User;
import dao.FriendDAO;
import dao.UserDAO;
import Exception.*;
import Network.NetworkListner;





public class LogoutDealer extends NetworkCommandDealer{

	@Override
	public Object dealCommand(String fromIp, Object param1, Object param2,
			Object param3) throws BusinessException, SystemException,
			DBOperatorException {
		// TODO Auto-generated method stub
		
		User user = (User) param1;
		User result =  UserDAO.getInstance().loadbyuserid(user.getUserId());
		result.setRecentLogoutDate(new Date());
		result.setIsOnline(false);
		List<Friend> list=FriendDAO.getInstance().loadByFriendId(user.getUserId());
		for(int i=0;i<list.size();i++){
			list.get(i).setIsOnline(false);
			FriendDAO.getInstance().update(list.get(i));
		}
		UserDAO.getInstance().update(result);
		// 记录最近登录时间，ip，端口
		return null;
	}
}
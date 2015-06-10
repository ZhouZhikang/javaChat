package NetworkDealer;

import dao.FriendDAO;
import dao.UserDAO;
import model.Friend;
import model.User;
import Exception.*;


public class AddFriendDealer extends NetworkCommandDealer{

	@Override
	public Object dealCommand(String fromIp, Object param1, Object param2,
			Object param3) throws BusinessException, SystemException,
			DBOperatorException {
		// TODO Auto-generated method stub
		Friend newfriend =new Friend();
		newfriend.setFriendId((String)param1);
		newfriend.setWhos((String)param2);
		User user=UserDAO.getInstance().loadbyuserid((String)param1);
		newfriend.setIsOnline(user.getIsOnline());
		FriendDAO.getInstance().add(newfriend);
		return null;
	}

}
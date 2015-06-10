package NetworkDealer;

import dao.FriendDAO;
import model.Friend;
import model.User;
import Exception.*;



public class DeleteFriendDealer extends NetworkCommandDealer{

	@Override
	public Object dealCommand(String fromIp, Object param1, Object param2,
			Object param3) throws BusinessException, SystemException,
			DBOperatorException {
		// TODO Auto-generated method stub
		User user1 = (User)param1;
		User user2 = (User)param2;
		
		Friend fr=FriendDAO.getInstance().findFriend(user2.getUserId(), user1.getUserId());
		FriendDAO.getInstance().remove(fr);
		return null;
	}

}

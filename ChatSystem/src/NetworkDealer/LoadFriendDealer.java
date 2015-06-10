package NetworkDealer;

import java.util.List;

import dao.FriendDAO;
import model.Friend;
import model.User;
import Exception.*;
import Network.*;



public class LoadFriendDealer extends NetworkCommandDealer{

	@Override
	public Object dealCommand(String fromIp, Object param1, Object param2,
			Object param3) throws BusinessException, SystemException,
			DBOperatorException {
		// TODO Auto-generated method stub
		User user = (User) param1;
		List<Friend> result = FriendDAO.getInstance().listAll(user.getUserId());
		return result;
	}

}

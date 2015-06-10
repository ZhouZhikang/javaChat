package NetworkDealer;

import dao.UserDAO;
import model.User;
import Exception.*;



public class SearchFriendDealer extends NetworkCommandDealer{

	@Override
	public Object dealCommand(String fromIp, Object param1, Object param2,
			Object param3) throws BusinessException, SystemException,
			DBOperatorException {
		// TODO Auto-generated method stub
		String userId = (String) param1;
		User result =  UserDAO.getInstance().loadbyuserid(userId);
		if (result == null)
			throw new BusinessException("帐号不存在");
		return result;
	}

}

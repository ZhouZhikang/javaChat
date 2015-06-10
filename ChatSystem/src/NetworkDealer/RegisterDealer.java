package NetworkDealer;

import dao.UserDAO;
import model.User;
import Exception.BusinessException;
import Exception.DBOperatorException;
import Exception.SystemException;



public class RegisterDealer extends NetworkCommandDealer{

	@Override
	public Object dealCommand(String fromIp, Object param1, Object param2,
			Object param3) throws BusinessException, SystemException,
			DBOperatorException {
		// TODO Auto-generated method stub
		User user = (User) param1;
		UserDAO.getInstance().add(user);
		return user;
	}

}

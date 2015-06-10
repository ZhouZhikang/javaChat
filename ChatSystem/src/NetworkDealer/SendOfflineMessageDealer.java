package NetworkDealer;

import dao.MessageDAO;
import model.Message;
import Exception.*;


public class SendOfflineMessageDealer  extends NetworkCommandDealer{

	@Override
	public Object dealCommand(String fromIp, Object param1, Object param2,
			Object param3) throws BusinessException, SystemException,
			DBOperatorException {
		// TODO Auto-generated method stub
		Message message=(Message)param1;
		MessageDAO.getInstance().add(message);
		return null;
	}	
}

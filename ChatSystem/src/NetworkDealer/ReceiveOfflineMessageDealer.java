package NetworkDealer;

import java.util.Date;
import java.util.List;

import dao.MessageDAO;
import model.Message;
import Exception.*;


public class ReceiveOfflineMessageDealer  extends NetworkCommandDealer{

	@Override
	public Object dealCommand(String fromIp, Object param1, Object param2,
			Object param3) throws BusinessException, SystemException,
			DBOperatorException {
		// TODO Auto-generated method stub
		String from = (String)param1;
		String to=(String)param2;
		List<Message> list=MessageDAO.getInstance().findMessage(from, to);
		return list;
	}

}

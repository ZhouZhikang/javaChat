package NetworkDealer;

import java.util.List;

import model.Friend;
import model.Message;
import model.User;
import Exception.BusinessException;
import Exception.DBOperatorException;
import Exception.SystemException;
import dao.FriendDAO;
import dao.MessageDAO;
import dao.UserDAO;

public class DeleteMessageDealer extends NetworkCommandDealer{

	@Override
	public Object dealCommand(String fromIp, Object param1, Object param2,
			Object param3) throws BusinessException, SystemException,
			DBOperatorException {
		// TODO Auto-generated method stub
		Message message=(Message)param1;
		List<Message> list=MessageDAO.getInstance().findMessage(message.getFromUser(), message.getToUser());
		for(int i=0;i<list.size();i++){
			MessageDAO.getInstance().remove(list.get(i));
		}
		return null;
	}	

}

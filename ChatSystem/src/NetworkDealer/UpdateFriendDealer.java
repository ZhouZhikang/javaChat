package NetworkDealer;

import java.util.List;

import model.Friend;
import model.Message;
import model.User;
import Exception.BusinessException;
import Exception.DBOperatorException;
import Exception.RemoteException;
import Exception.SystemException;
import Network.NetworkCommand;
import dao.FriendDAO;
import dao.UserDAO;

public class UpdateFriendDealer extends NetworkCommandDealer{

	@Override
	public Object dealCommand(String fromIp, Object param1, Object param2,
			Object param3) throws BusinessException, SystemException,
			DBOperatorException {
		// TODO Auto-generated method stub
		try {
			System.out.println((String)param1);
			System.out.println((String)param2);
		Friend fr=FriendDAO.getInstance().findFriend((String)param1, (String)param2);
		System.out.println(fr.getFid());
			List<Message> list=NetworkCommand.getServer().receiveOfflineMessage((String)param1, (String)param2);
		fr.setNewMessage(list.size());
		FriendDAO.getInstance().update(fr);
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
}

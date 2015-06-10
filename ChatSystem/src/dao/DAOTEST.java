package dao;
import java.util.List;

import Exception.BusinessException;
import Exception.RemoteException;
import Network.NetworkCommand;
import model.*;
import dao.*;

public class DAOTEST {

	public static void main(String[] args) throws BusinessException {
		Friend fr=FriendDAO.getInstance().findFriend("zzk", "ssc");
		try {
			List<Message> list=NetworkCommand.getServer().receiveOfflineMessage("zzk", "ssc");
		fr.setNewMessage(list.size());
		FriendDAO.getInstance().update(fr);
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
}
}

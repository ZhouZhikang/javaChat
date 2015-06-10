package Client.control;

import java.util.List;

import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import model.Friend;
import Client.ui.ClientMain;
import Exception.BusinessException;
import Exception.RemoteException;
import Network.NetworkCommand;

public class ClientFriendRefresh extends Thread {
	private boolean isActive = true;
	private Object tableTitle[] = ClientMain.tableTitle;
	private Object tableData[][] = ClientMain.tableData;
	private DefaultTableModel tableModel = ClientMain.tableModel;
	private JTable table = ClientMain.table;

	public void ClentFriendRefresh() {

	}

	public void run() {
		while (isActive) {
			// TODO Auto-generated method stub
			try {
				int i = 0;
				List<Friend> result = NetworkCommand.getServer().LoadFriend(
						NetworkCommand.getServer().getCurrentUser());
				// System.out.println(result.get(0).getFriendId());
				tableData = new Object[result.size()][3];
				for (i = 0; i < result.size(); i++) {
					String isonline = null;
					if (result.get(i).getIsOnline() == false)
						isonline = "离线";
					else
						isonline = "在线";
					tableData[i][0] = result.get(i).getFriendId();
					tableData[i][1] = isonline;
					tableData[i][2] = result.get(i).getNewMessage();
				}
				// System.out.println(i);
				i = 0;
				tableModel.setDataVector(tableData, tableTitle);
				this.table.validate();
				this.table.repaint();
				this.sleep(5000);
			} catch (RemoteException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (BusinessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}

package Client.ui;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JTree;
import javax.swing.AbstractListModel;
import javax.swing.ListSelectionModel;
import javax.swing.border.CompoundBorder;
import javax.swing.JButton;
import javax.swing.JTable;
import javax.swing.JTextArea;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.SystemColor;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JScrollPane;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableModel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import model.Friend;
import model.Message;
import model.User;
import Client.control.ClientFriendRefresh;
import Client.control.ClientListner;
import Exception.BusinessException;
import Exception.RemoteException;
import Network.NetworkCommand;

public class ClientMain extends JFrame implements ActionListener, MouseListener {
	public static Map<String,ClientChat> chatUI=new HashMap<String,ClientChat>();
	private JPanel contentPane;
	private JButton button;
	private JButton button_1;
	private JButton button_2;
	public static Object tableTitle[] = { "ID", "状态", "消息" };
	public static Object tableData[][];
	public static DefaultTableModel tableModel = new DefaultTableModel();
	public static JTable table = new JTable(tableModel) {
		public boolean isCellEditable(int row, int column) {
			return false;
		}
	};
	private User currentuser = NetworkCommand.getServer().getCurrentUser();
	private static User currentfriend = new User();
	

	public void LoadFriendList() {
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
				tableData[i][2] = "";
			}
			// System.out.println(i);
			i = 0;
			tableModel.setDataVector(tableData, tableTitle);
			this.table.validate();
			this.table.repaint();
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (BusinessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}


	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ClientMain frame = new ClientMain();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public ClientMain() {
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 284, 627);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout(0, 0));

		JPanel panel = new JPanel();
		panel.setForeground(Color.WHITE);
		panel.setBorder(new EmptyBorder(0, 0, 0, 0));
		panel.setBackground(SystemColor.window);
		contentPane.add(panel, BorderLayout.SOUTH);
		panel.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));

		button = new JButton("+");
		button.addActionListener(this);
		panel.add(button);

		button_1 = new JButton("-");
		button_1.addActionListener(this);
		panel.add(button_1);

		button_2 = new JButton("注销");
		button_2.addActionListener(this);
		panel.add(button_2);

		JScrollPane scrollPane = new JScrollPane();
		contentPane.add(scrollPane, BorderLayout.CENTER);

		scrollPane.setViewportView(table);

		JPanel panel_1 = new JPanel();
		panel_1.setBorder(new EmptyBorder(0, 0, 0, 0));
		contentPane.add(panel_1, BorderLayout.NORTH);

		JLabel label = new JLabel("好友");
		panel_1.add(label);

		table.addMouseListener(this);
		this.LoadFriendList();
		ClientFriendRefresh re = new ClientFriendRefresh();
		re.start();

		ClientListner listner = new ClientListner(currentuser);
		listner.start();
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		if (e.getSource() != table) {
			return;
		}
		if (e.getClickCount() == 1) {
			try {
				//获取选中的好友id
				currentfriend = NetworkCommand.getServer().SearchFriend(
						(String) tableData[table.getSelectedRow()][0]);
			} catch (RemoteException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (BusinessException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
		if (e.getClickCount() == 2) {
			try {
				currentfriend = NetworkCommand.getServer().SearchFriend(
						(String) tableData[table.getSelectedRow()][0]);
			} catch (RemoteException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (BusinessException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			System.out.println(currentfriend.getRecentLoginPort());
			if(chatUI.get(currentfriend.getUserId())!=null){
				chatUI.get(currentfriend.getUserId()).setVisible(true);
				
			}
			else{
				 chatUI.put(currentfriend.getUserId(), new ClientChat(currentfriend));
				 System.out.println("建立聊天："+currentfriend.getUserId());
				 chatUI.get(currentfriend.getUserId()).setTitle((String) tableData[table.getSelectedRow()][0]);
				 chatUI.get(currentfriend.getUserId()).setVisible(true);
			}
			// System.out.println(currentfriend.getUserId());
		}

	}

	public void dispose() {
		// TODO Auto-generated method stub
		//重写dispose()方法，改变用户的在线状态
		try {
			int result = JOptionPane.showConfirmDialog(null, "您确定要退出吗?", "请确认",
					JOptionPane.YES_NO_OPTION);
			if (result == JOptionPane.OK_OPTION) {
				NetworkCommand.getServer().logout();
				System.exit(0);
			}
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (BusinessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if (e.getSource() == button) {
			// System.out.println("1111");
			ClientMain currentMain=this;
			ClientAddFriend addfriend = new ClientAddFriend(this);
			addfriend.setVisible(true);
		} else if (e.getSource() == button_1) {
			System.out.println(currentuser.getUserId());
			System.out.println(currentfriend.getUserId());
			try {
				int result = JOptionPane.showConfirmDialog(null, "确定删除该好友吗?",
						"请确认", JOptionPane.YES_NO_OPTION);
				if (result == JOptionPane.OK_OPTION) {
					NetworkCommand.getServer().deleteFriend(currentuser,
							currentfriend);
				}
			} catch (RemoteException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (BusinessException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		} else if (e.getSource() == button_2) {
			try {
				int result = JOptionPane.showConfirmDialog(null, "您确定要退出吗?",
						"请确认", JOptionPane.YES_NO_OPTION);
				if (result == JOptionPane.OK_OPTION) {
					NetworkCommand.getServer().logout();
					System.exit(0);
				}
			} catch (RemoteException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (BusinessException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
	}
}

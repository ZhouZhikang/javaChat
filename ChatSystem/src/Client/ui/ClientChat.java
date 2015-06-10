package Client.ui;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import Exception.*;
import Network.NetworkCommand;
import model.*;
import Client.control.*;

public class ClientChat extends JFrame implements ActionListener {

	private JPanel contentPane;
	private JTextArea jtaAllMessage;
	private JTextArea jtaSendMessage;
	private JButton jbSubmit;
	private JPanel jp1;
	private JPanel jp2;
	private JPanel jp3;
	private static User friendUser;
	private static User currentUser = NetworkCommand.getServer()
			.getCurrentUser();
	public static String recivemessage;

	/**
	 * Launch the application.
	 */

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ClientChat frame = new ClientChat(friendUser);
					frame.setName(friendUser.getUserId());
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
	public ClientChat(User currentfriend) {
		friendUser = currentfriend;
		// System.out.println(friendUser.getUserId());

		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 500, 501);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout(0, 0));
		jtaAllMessage = new JTextArea();
		jtaSendMessage = new JTextArea();
		jtaAllMessage.setEditable(false);

		jbSubmit = new JButton("发送");
		jbSubmit.addActionListener(this);

		jp1 = new JPanel();
		jp2 = new JPanel();
		jp3 = new JPanel();

		jp1.setLayout(new BorderLayout());
		jp2.setLayout(new BorderLayout());
		FlowLayout flow = new FlowLayout();
		flow.setAlignment(FlowLayout.RIGHT);
		jp3.setLayout(flow);
		jtaSendMessage.setRows(5);

		jp2.add(new JScrollPane(jtaSendMessage), BorderLayout.CENTER);
		jp3.add(jbSubmit);

		jp1.add(jp2, BorderLayout.CENTER);
		jp1.add(jp3, BorderLayout.SOUTH);
		getContentPane().add(new JScrollPane(jtaAllMessage), "Center");
		getContentPane().add(jp1, "South");
		//读取离线信息并把数据库中的已读信息删除并刷新好友关系中的信息数量
		try {
			List<Message> listresult = NetworkCommand.getServer().receiveOfflineMessage(currentfriend.getUserId(), currentUser.getUserId());
			if(listresult.size()!=0){ 
			for(int i=0;i<listresult.size();i++){
				 showMessage(listresult.get(i));
			 NetworkCommand.getServer().deleteMessage(listresult.get(i));
			 NetworkCommand.getServer().updateFriend(listresult.get(i).getFromUser(), listresult.get(i).getToUser());
			 }
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
	public void actionPerformed(ActionEvent e) {

		// TODO Auto-generated method stub
		if (e.getSource() == jbSubmit) {
			if (jtaSendMessage.getText().trim().equals("")) {
				JOptionPane.showMessageDialog(this, "发送内容不能为空", "提示",
						JOptionPane.INFORMATION_MESSAGE);
				return;
			}
			Message msg = new Message();
			msg.setSendTime(new Date());
			msg.setFromUser(currentUser.getUserId());
			msg.setToUser(friendUser.getUserId());
			msg.setContent(jtaSendMessage.getText());
			boolean isonline = false;
			//每次点击发送的时候 判断对方是否在线 如果不在线转为发送离线消息
			try {
				isonline = NetworkCommand.getServer()
						.SearchFriend(friendUser.getUserId()).getIsOnline();
				if (isonline == false) {
					try {
						jtaAllMessage.append("该用户已经离线，消息改为离线发送！\r\n");
						//离线发送的消息存入数据库
						NetworkCommand.getServer().sendOfflineMessage(msg);
						System.out.println(currentUser.getUserId());
						System.out.println(friendUser.getUserId());
						NetworkCommand.getServer().updateFriend(currentUser.getUserId(),friendUser.getUserId());
					} catch (RemoteException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					} catch (BusinessException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				} else {

					ClientSender sender = new ClientSender(friendUser,
							currentUser, msg);
					sender.start();
				}
				System.out.println("信息已经发送");
				showMessage(msg);
				jtaSendMessage.setText("");

				// System.out.println(friendUser.getRecentLoginIp()+friendUser.getRecentLoginPort());

			} catch (RemoteException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (BusinessException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
	}

	public void showMessage(Message msg) {
		// TODO Auto-generated method stub
		jtaAllMessage.append(msg.getFromUser()
				+ new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(msg
						.getSendTime()) + "\r\n");
		jtaAllMessage.append(msg.getContent() + "\r\n");
	}
	
}

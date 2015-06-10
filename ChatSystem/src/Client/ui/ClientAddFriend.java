package Client.ui;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JScrollPane;
import javax.swing.JLabel;

import model.Friend;
import model.User;
import Exception.BusinessException;
import Exception.RemoteException;
import Network.NetworkCommand;

public class ClientAddFriend extends JFrame implements ActionListener {
	private static ClientMain currentMain=null;
	private JPanel contentPane;
	private JButton button = null;
	private JButton button_1 = null;
	private JTextField textField;
	private Object tableTitle[] = { "ID", "状态" };
	private Object tableData[][];
	private User result = null;
	DefaultTableModel tableModel = new DefaultTableModel();
	private JTable table = new JTable(tableModel) {
		public boolean isCellEditable(int row, int column) {
			return false;
		}
	};

	private void LoadResult(String id) {
		// TODO Auto-generated method stub
		try {
			result = NetworkCommand.getServer().SearchFriend(id);
			if (result != null) {
				// System.out.println(result.getUserId());
				tableData = new Object[1][2];
				String isonline = null;
				if (result.getIsOnline() == false)
					isonline = "离线";
				else
					isonline = "在线";
				tableData[0][0] = result.getUserId();
				tableData[0][1] = isonline;
				// System.out.println(i);
				tableModel.setDataVector(tableData, tableTitle);
				this.table.validate();
				this.table.repaint();
			}
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (BusinessException e) {
			// TODO Auto-generated catch block
			JOptionPane.showMessageDialog(this,e.getMessage(),"错误",JOptionPane.ERROR_MESSAGE);
		}

	}

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ClientAddFriend frame = new ClientAddFriend(currentMain);
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
	public ClientAddFriend(ClientMain cm) {
		currentMain=cm;
		setResizable(false);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 376, 320);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		textField = new JTextField();
		textField.setBounds(66, 57, 178, 28);
		contentPane.add(textField);
		textField.setColumns(10);

		button = new JButton("搜索");
		button.addActionListener(this);
		button.setBounds(259, 58, 75, 29);
		contentPane.add(button);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(34, 109, 311, 126);
		contentPane.add(scrollPane);

		scrollPane.setViewportView(table);

		button_1 = new JButton("加为好友");
		button_1.setBounds(228, 247, 117, 29);
		button_1.setEnabled(false);
		button_1.addActionListener(this);
		contentPane.add(button_1);

		JLabel label = new JLabel("账号");
		label.setBounds(34, 63, 31, 16);
		contentPane.add(label);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if (e.getSource() == button) {
			String id = textField.getText().trim();
			LoadResult(id);
			button_1.setEnabled(true);
		}
		if (e.getSource() == button_1) {
			try {
				User me = NetworkCommand.getServer().getCurrentUser();
				//添加好友关系到数据库中
				NetworkCommand.getServer().addFriend(result.getUserId(),
						me.getUserId());
				currentMain.LoadFriendList();
				JOptionPane.showMessageDialog(this, "添加成功!", "提示",
						JOptionPane.INFORMATION_MESSAGE);
				this.dispose();
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

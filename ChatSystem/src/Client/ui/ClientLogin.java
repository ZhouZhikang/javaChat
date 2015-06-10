package Client.ui;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import java.awt.Color;
import java.awt.SystemColor;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JButton;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import javax.swing.JPasswordField;

import model.User;
import Client.control.ClientListner;
import Client.ui.*;
import Network.NetworkCommand;
import Network.NetworkListner;

public class ClientLogin extends JFrame implements ActionListener{

	private JPanel contentPane;
	private JTextField textField;
	private JLabel label_1;
	private JButton button;
	private JButton btnNewButton;
	private JPasswordField passwordField;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ClientLogin frame = new ClientLogin();
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
	public ClientLogin() {
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 285, 286);
		contentPane = new JPanel();
		contentPane.setBackground(SystemColor.window);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel label = new JLabel("账号");
		label.setBounds(34, 69, 36, 16);
		contentPane.add(label);
		
		textField = new JTextField();
		textField.setBounds(66, 63, 169, 28);
		contentPane.add(textField);
		textField.setColumns(10);
		
		label_1 = new JLabel("密码");
		label_1.setBounds(34, 115, 36, 16);
		contentPane.add(label_1);
		
		button = new JButton("登陆");
		button.addActionListener(this);
		button.setBounds(156, 185, 75, 29);
		
		contentPane.add(button);
		
		btnNewButton = new JButton("注册");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ClientRegister dlg=new ClientRegister();
				dlg.setVisible(true);
			}
		});
		btnNewButton.setBounds(50, 185, 75, 29);
		contentPane.add(btnNewButton);
		
		passwordField = new JPasswordField();
		passwordField.setBounds(66, 109, 169, 28);
		contentPane.add(passwordField);
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if(e.getSource() == button)
		{
			String pwd = new String(passwordField.getPassword());
			String id = textField.getText().trim();
			//System.out.println("用户名:"+id+"\n密码:"+pwd);
			User user = new User();
			user.setUserId(id);
			user.setUserPwd(pwd);
			//ip在服务器端获取
			try {
				NetworkCommand.getServer().checkLogin(user);
				user = NetworkCommand.getServer().getCurrentUser();
				//NetworkCommand.getServer().refreshFriend(user);
				//JOptionPane.showMessageDialog(this,"登陆成功","提示",JOptionPane.INFORMATION_MESSAGE);
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				JOptionPane.showMessageDialog(this,e1.getMessage(),"错误",JOptionPane.ERROR_MESSAGE);
				return;
			}
			
			this.setVisible(false);
			ClientMain mainUI = new ClientMain();
			mainUI.setTitle("当前用户："+user.getUserId());
			mainUI.setVisible(true);
		}
		{
//			this.setVisible(true);
		}
	}
}

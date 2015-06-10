package Client.ui;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JPasswordField;
import javax.swing.JButton;

import Network.*;
import model.*;


public class ClientRegister extends JFrame implements ActionListener{

	private JPanel contentPane;
	private JTextField textField;
	private JPasswordField passwordField;
	private JLabel label_2;
	private JPasswordField passwordField_1;
	private JButton button;
	private JButton button_1;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ClientRegister frame = new ClientRegister();
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
	public ClientRegister() {
		setResizable(false);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 335, 220);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		textField = new JTextField();
		textField.setBounds(79, 25, 183, 28);
		contentPane.add(textField);
		textField.setColumns(10);
		
		JLabel label = new JLabel("账号");
		label.setBounds(46, 31, 31, 16);
		contentPane.add(label);
		
		JLabel label_1 = new JLabel("密码");
		label_1.setBounds(46, 59, 31, 16);
		contentPane.add(label_1);
		
		passwordField = new JPasswordField();
		passwordField.setBounds(79, 53, 183, 28);
		contentPane.add(passwordField);
		
		label_2 = new JLabel("确认密码");
		label_2.setBounds(25, 87, 52, 16);
		contentPane.add(label_2);
		
		passwordField_1 = new JPasswordField();
		passwordField_1.setBounds(79, 81, 183, 28);
		contentPane.add(passwordField_1);
		
		button = new JButton("注册");
		button.addActionListener(this);
		button.setBounds(92, 138, 61, 29);
		contentPane.add(button);
		
		button_1 = new JButton("取消");
		button_1.addActionListener(this);
		button_1.setBounds(187, 138, 61, 29);
		contentPane.add(button_1);
	}
	
	
	public void dispose() {
		// TODO Auto-generated method stub
		super.dispose();
		textField.setText("");
		passwordField.setText("");
		passwordField_1.setText("");
	}
	public boolean checkRegisterInfo()
	{
		if(textField.getText().equals("")||passwordField.getPassword().equals(""))
		{
			JOptionPane.showMessageDialog(this,"信息不全","提示",JOptionPane.ERROR_MESSAGE);
			return false;
		}
		return true;
	}
	
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if(e.getSource() == button_1)
		{
			this.dispose();
			
		}
		else if(e.getSource() == button)
		{
			if(!checkRegisterInfo())
			{
				return;
			}
			String name=new String(textField.getText().trim());
			String pwd =new String(passwordField.getPassword());
			String pwd2 =new String(passwordField_1.getPassword());
			if(!pwd.equals(pwd2)){
				JOptionPane.showMessageDialog(this,"密码不一致","提示",JOptionPane.ERROR_MESSAGE);
				return;
			}
			User user = new User();
			user.setUserId(name);
			user.setUserPwd(pwd);
			user.setIsOnline(false);
			try {
				NetworkCommand.getServer().Register(user);
				JOptionPane.showMessageDialog(this,"注册成功!","提示",JOptionPane.INFORMATION_MESSAGE);
				this.dispose();
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
				JOptionPane.showMessageDialog(this,e1.getMessage(),"提示",JOptionPane.ERROR_MESSAGE);
			}
		}
	}
}

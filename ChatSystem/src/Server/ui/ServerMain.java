package Server.ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;
import javax.swing.JTextField;
import javax.swing.JButton;

import Exception.SystemException;
import Network.NetworkListner;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.net.ServerSocket;

import javax.swing.JTextArea;


public class ServerMain extends JFrame {

	private JPanel contentPane;
	private boolean isActive = true;
    public static int currentPort=0;
    private NetworkListner server;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ServerMain frame = new ServerMain();
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
	public ServerMain() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		
		JPanel panel1 = new JPanel();
		contentPane.add(panel1, BorderLayout.CENTER);
		panel1.setLayout(new BorderLayout(0, 0));
		
		final JTextArea textArea = new JTextArea();
		textArea.setForeground(new Color(0, 0, 0));
		textArea.setFont(new Font("Monospaced", Font.BOLD, 15));
		textArea.setEnabled(false);
		JScrollPane jsp = new JScrollPane(textArea);
		panel1.add(jsp, BorderLayout.CENTER);
		
		JPanel panel = new JPanel();
		contentPane.add(panel, BorderLayout.SOUTH);
		
		
		
		JButton btnStart = new JButton("START");
		btnStart.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					server = new NetworkListner(9527, 16000);
					server.start();
					textArea.append("服务开启,正在监听9527端口\r\n");
				} catch (SystemException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
					textArea.append(e1.getMessage()+"\r\n");
				}
				
			}
		});
		panel.add(btnStart);

		
		JButton btnStop = new JButton("STOP\n");
		btnStop.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(server != null)
				{
					server.stopThread();
					server = null;
					textArea.append("服务关闭\r\n");
				}
				else
				{
					textArea.append("服务已关闭\r\n");
				}
			}
		});
		panel.add(btnStop);
		
		
	}
}

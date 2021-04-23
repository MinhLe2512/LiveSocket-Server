package frame;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.UIManager;

import model.JPanelWithBackground;
import window.AdminWindow;
import java.awt.Toolkit;

public class AdminLoginFrame extends JFrame  {

	private JPanel contentPane;
	
	private JTextField textUser;
	private  JPasswordField passwordField;
	
	private JButton btnLogin;
	private JButton btnHelp;

	private static JFrame errorMessage;
	private static volatile boolean exit;
	
	/**
	 * Create the frame.
	 */
	
	public AdminLoginFrame(AdminWindow defaultWindow) {
		setBackground(new Color(255, 255, 255));
		setIconImage(Toolkit.getDefaultToolkit().getImage(
				"C:\\Users\\DELL\\Downloads\\livescore_icon.png"));
		setResizable(false);
			try {
				contentPane = new JPanelWithBackground(
						"C:\\Users\\DELL\\Downloads\\livescore_login.png");
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			contentPane.setLayout(null);
			contentPane.setFont(UIManager.getFont("Panel.font"));
			contentPane.setForeground(new Color(255, 255, 255));
			//contentPane.setTitle("Edit match\r\n");
			contentPane.setBackground(new Color(0, 153, 255));
			contentPane.setSize(480, 320);
			setBounds(200, 200, 663 , 443);
			setContentPane(contentPane);
			setLocationRelativeTo(null);
			
			JPanel panel = new JPanel();
			panel.setBackground(new Color(0, 0, 0));
			panel.setBounds(129, 81, 425, 243);
			contentPane.add(panel);
			panel.setLayout(null);
			
			//Label main
			JLabel lblMain = new JLabel("Livescore\r\n");
			lblMain.setForeground(Color.WHITE);
			lblMain.setBounds(174, 22, 82, 25);
			panel.add(lblMain);
			lblMain.setHorizontalAlignment(SwingConstants.CENTER);
			lblMain.setFont(new Font("Tahoma", Font.PLAIN, 20));
			
			//Input user
			textUser = new JTextField();
			textUser.setBounds(10, 75, 400, 37);
			panel.add(textUser);
			textUser.setColumns(10);
			
			passwordField = new JPasswordField();
			passwordField.setBounds(10, 145, 400, 37);
			panel.add(passwordField);
			
			//Button Login
			btnLogin = new JButton("Login\r\n");
			btnLogin.setForeground(new Color(0, 0, 0));
			btnLogin.setBackground(new Color(240, 255, 255));
			btnLogin.setBounds(68, 192, 115, 37);
			panel.add(btnLogin);
			btnLogin.setFont(new Font("Tahoma", Font.PLAIN, 13));
			
			//Button help
			btnHelp = new JButton("Help?");
			btnHelp.setBackground(new Color(255, 255, 255));
			btnHelp.setBounds(231, 192, 115, 37);
			panel.add(btnHelp);
			btnHelp.setFont(new Font("Tahoma", Font.PLAIN, 13));
			
			//Label password
			JLabel lblPassword = new JLabel("Password\r\n");
			lblPassword.setForeground(Color.WHITE);
			lblPassword.setBounds(10, 122, 94, 13);
			panel.add(lblPassword);
			lblPassword.setFont(new Font("Tahoma", Font.BOLD, 15));
			
			//Label user
			JLabel lblUser = new JLabel("Username\r\n");
			lblUser.setForeground(Color.WHITE);
			lblUser.setBounds(20, 52, 94, 13);
			panel.add(lblUser);
			lblUser.setFont(new Font("Tahoma", Font.BOLD, 15));
			btnHelp.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					// TODO Auto-generated method stub
					JOptionPane.showMessageDialog(errorMessage, 
							"Please wait while we re looking for help", "Help", JOptionPane.DEFAULT_OPTION);		
				}
			});
			btnLogin.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					defaultWindow.loginStart();
				}
			});
	}
	
	public void clearText() {
		textUser.setText("");
		passwordField.setText("");
	}
	
	public String getUser() {
		return textUser.getText();
	}
	
	public String getPassword() {
		return passwordField.getText();
	}
	
	public boolean getBoolean() {
		return exit;
	}
}


package frame;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JPasswordField;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.SwingConstants;

import model.JPanelWithBackground;
import window.ClientWindow;

import java.awt.BorderLayout;
import java.awt.Color;

public class ClientLoginFrame extends JFrame{
	private JPanel contentPane;
	private JPanel contentPane_1;
	private JLabel userLabel, login_passwordLabel, login_titleLabel;
	private JTextField userText;
	private JPasswordField login_passwordText;
	private JButton loginButton, registerButton;
	private JCheckBox login_show_passwordCheckBox;
	private JPanel panel;

	/**
	 * Create the application.
	 */
	public ClientLoginFrame(ClientWindow defaultWindow) {
		contentPane = new JPanel();
		setBackground(new Color(255, 255, 255));
		setIconImage(Toolkit.getDefaultToolkit().getImage(
				"C:\\Users\\DELL\\Downloads\\livescore_icon.png"));
		setResizable(false);
			try {
				contentPane_1 = new JPanelWithBackground(
						"C:\\Users\\DELL\\Downloads\\livescore_login.png");
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		contentPane_1.setForeground(new Color(255, 255, 255));
		setBounds(0, 0, 684, 444);
		setLocationRelativeTo(null);
		contentPane_1.setSize(480,320);
		contentPane_1.setBackground(Color.WHITE);
		contentPane_1.setLayout(null);
		setContentPane(contentPane_1);
		
		panel = new JPanel();
		panel.setBackground(Color.BLACK);
		panel.setBounds(152, 77, 367, 242);
		contentPane_1.add(panel);
		panel.setLayout(null);
		
		login_titleLabel = new JLabel("LOGIN");
		login_titleLabel.setForeground(Color.WHITE);
		login_titleLabel.setBounds(158, 10, 62, 26);
		panel.add(login_titleLabel);
		login_titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
		login_titleLabel.setFont(new Font("Lucida Grande", Font.BOLD, 20));
		
		userText = new JTextField();
		userText.setBounds(115, 57, 235, 35);
		panel.add(userText);
		userText.setColumns(10);
		
		login_passwordText = new JPasswordField();
		login_passwordText.setBounds(115, 102, 235, 35);
		panel.add(login_passwordText);
		
		registerButton = new JButton("Register");
		registerButton.setForeground(Color.WHITE);
		registerButton.setBounds(197, 197, 117, 35);
		panel.add(registerButton);
		registerButton.setFont(new Font("Lucida Grande", Font.BOLD, 15));
		
		loginButton = new JButton("Login");
		loginButton.setForeground(Color.WHITE);
		loginButton.setBounds(47, 197, 117, 35);
		panel.add(loginButton);
		loginButton.setFont(new Font("Lucida Grande", Font.BOLD, 15));
		
		login_show_passwordCheckBox = new JCheckBox("Show password");
		login_show_passwordCheckBox.setForeground(Color.WHITE);
		login_show_passwordCheckBox.setBackground(Color.BLACK);
		login_show_passwordCheckBox.setBounds(115, 147, 148, 23);
		panel.add(login_show_passwordCheckBox);
		login_show_passwordCheckBox.setFont(new Font("Lucida Grande", Font.PLAIN, 15));
		
		login_passwordLabel = new JLabel("Password:");
		login_passwordLabel.setForeground(Color.WHITE);
		login_passwordLabel.setBounds(22, 108, 83, 16);
		panel.add(login_passwordLabel);
		login_passwordLabel.setFont(new Font("Lucida Grande", Font.PLAIN, 16));
		
		userLabel = new JLabel("User:");
		userLabel.setForeground(Color.WHITE);
		userLabel.setBounds(63, 62, 42, 16);
		panel.add(userLabel);
		userLabel.setFont(new Font("Lucida Grande", Font.PLAIN, 16));
		login_show_passwordCheckBox.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (login_show_passwordCheckBox.isSelected()) {
	                login_passwordText.setEchoChar((char) 0);
	            } else {
	                //login_passwordText.setEchoChar('');
	            }
			}
		});
		loginButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				defaultWindow.LoginStart();
			}
		});
		registerButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				defaultWindow.RegisterStart();
			}
		});
	}
	
	public String getUser() {
		return userText.getText();
	}
	public String getPass() {
		return login_passwordText.getText();
	}
	public void clearText() {
		userText.setText("");
		login_passwordText.setText("");
	}
}

package frame;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.UIManager;

import window.WindowAdminLogin;

public class LoginFrame extends JFrame  {

	private JPanel contentPane;
	
	private JTextField textUser;
	private  JPasswordField passwordField;
	
	private JButton btnLogin;
	private JButton btnHelp;

	private static JFrame errorMessage;
	private static volatile boolean exit;
	/**
	 * Launch the application.
	 */
//	public static void main(String[] args) {
//		EventQueue.invokeLater(new Runnable() {
//			public void run() {
//				try {
//					LoginFrame frame = new LoginFrame();
//					frame.setVisible(true);
//				} catch (Exception e) {
//					e.printStackTrace();
//				}
//			}
//		});
//	}

	/**
	 * Create the frame.
	 */
	public LoginFrame(WindowAdminLogin defaultWindow) {
			contentPane = new JPanel();
			contentPane.setLayout(null);
			contentPane.setFont(UIManager.getFont("Panel.font"));
			contentPane.setForeground(new Color(255, 255, 255));
			//contentPane.setTitle("Edit match\r\n");
			contentPane.setBackground(Color.WHITE);
			contentPane.setSize(480, 320);
			setBounds(200, 200, 480 , 320);
			setContentPane(contentPane);
			
			//Label main
			JLabel lblMain = new JLabel("Livescore\r\n");
			lblMain.setHorizontalAlignment(SwingConstants.CENTER);
			lblMain.setFont(new Font("Tahoma", Font.PLAIN, 20));
			lblMain.setBounds(0, 0, 460, 60);
			contentPane.add(lblMain);
			
			//Input user
			textUser = new JTextField();
			textUser.setBounds(40, 83, 400, 37);
			contentPane.add(textUser);
			textUser.setColumns(10);
			
			//Label user
			JLabel lblUser = new JLabel("Username\r\n");
			lblUser.setFont(new Font("Tahoma", Font.BOLD, 15));
			lblUser.setBounds(40, 60, 94, 13);
			contentPane.add(lblUser);
			
			//Label password
			JLabel lblPassword = new JLabel("Password\r\n");
			lblPassword.setFont(new Font("Tahoma", Font.BOLD, 15));
			lblPassword.setBounds(40, 130, 94, 13);
			contentPane.add(lblPassword);
			
			//Button Login
			btnLogin = new JButton("Login\r\n");
			btnLogin.setFont(new Font("Tahoma", Font.PLAIN, 13));
			btnLogin.setBounds(85, 219, 115, 37);
			btnLogin.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					defaultWindow.loginStart();
				}
			});
			contentPane.add(btnLogin);
			
			//Button help
			btnHelp = new JButton("Help?");
			btnHelp.setFont(new Font("Tahoma", Font.PLAIN, 13));
			btnHelp.setBounds(270, 219, 115, 37);
			btnHelp.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					// TODO Auto-generated method stub
					JOptionPane.showMessageDialog(errorMessage, 
							"Please wait while we re looking for help", "Help", JOptionPane.DEFAULT_OPTION);		
				}
			});
			contentPane.add(btnHelp);
			
			passwordField = new JPasswordField();
			passwordField.setBounds(40, 152, 400, 37);
			contentPane.add(passwordField);
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


package frame;

import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import com.toedter.calendar.JDateChooser;

import model.JPanelWithBackground;
import window.ClientWindow;

import java.awt.Color;
import java.awt.Component;
import javax.swing.JCheckBox;

public class RegisterFrame extends JFrame{
	private JPanel contentPane;
	private JLabel usernameLabel, firstnameLabel, lastnameLabel, register_passwordLabel, register_titleLabel, dobLabel, telLabel, addressLabel;
	private JTextField usernameText, firstnameText, lastnameText, telText, addressText;
	private JPasswordField register_passwordText;
	private JButton submitButton, backtologin_Button;
	private JCheckBox register_show_passwordCheckBox;
	private JDateChooser dobChooser;
	private JPanel panel;

	/**
	 * Create the application.
	 */
	public RegisterFrame(ClientWindow defaultWindow) {
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
		contentPane.setForeground(new Color(255, 255, 255));
		setBounds(0, 0, 400, 540);
		setLocationRelativeTo(null);
		contentPane.setSize(400,540);
		contentPane.setBackground(Color.WHITE);
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		usernameLabel = new JLabel("Username:");
		usernameLabel.setForeground(Color.WHITE);
		usernameLabel.setBackground(Color.WHITE);
		usernameLabel.setFont(new Font("Lucida Grande", Font.PLAIN, 15));
		usernameLabel.setBounds(35, 43, 101, 16);
		contentPane.add(usernameLabel);
		
		firstnameLabel = new JLabel("First Name:");
		firstnameLabel.setForeground(Color.WHITE);
		firstnameLabel.setFont(new Font("Lucida Grande", Font.PLAIN, 15));
		firstnameLabel.setBounds(35, 98, 89, 16);
		contentPane.add(firstnameLabel);
		
		lastnameLabel = new JLabel("Last Name:");
		lastnameLabel.setForeground(Color.WHITE);
		lastnameLabel.setFont(new Font("Lucida Grande", Font.PLAIN, 15));
		lastnameLabel.setBounds(35, 153, 89, 16);
		contentPane.add(lastnameLabel);
		
		register_passwordLabel = new JLabel("Password:");
		register_passwordLabel.setForeground(Color.WHITE);
		register_passwordLabel.setFont(new Font("Lucida Grande", Font.PLAIN, 15));
		register_passwordLabel.setBounds(35, 208, 215, 16);
		contentPane.add(register_passwordLabel);
		
		dobLabel = new JLabel("Date of birth:");
		dobLabel.setForeground(Color.WHITE);
		dobLabel.setFont(new Font("Lucida Grande", Font.PLAIN, 15));
		dobLabel.setBounds(35, 285, 106, 16);
		contentPane.add(dobLabel);
		
		telLabel = new JLabel("Telephone: ");
		telLabel.setForeground(Color.WHITE);
		telLabel.setFont(new Font("Lucida Grande", Font.PLAIN, 15));
		telLabel.setBounds(35, 345, 96, 16);
		contentPane.add(telLabel);
		
		addressLabel = new JLabel("Address:");
		addressLabel.setForeground(Color.WHITE);
		addressLabel.setFont(new Font("Lucida Grande", Font.PLAIN, 15));
		addressLabel.setBounds(35, 400, 89, 16);
		contentPane.add(addressLabel);
		
		usernameText = new JTextField();
		usernameText.setBounds(30, 58, 335, 35);
		contentPane.add(usernameText);
		usernameText.setColumns(10);
		
		firstnameText = new JTextField();
		firstnameText.setBounds(30, 113, 335, 35);
		contentPane.add(firstnameText);
		firstnameText.setColumns(10);
		
		lastnameText = new JTextField();
		lastnameText.setBounds(30, 168, 335, 35);
		lastnameText.setAlignmentY(Component.BOTTOM_ALIGNMENT);
		contentPane.add(lastnameText);
		lastnameText.setColumns(10);
		
		register_passwordText = new JPasswordField();
		register_passwordText.setBounds(30, 223, 335, 35);
		contentPane.add(register_passwordText);
		
		dobChooser = new JDateChooser();
		dobChooser.setBounds(30, 305, 180, 35);
		contentPane.add(dobChooser);
		
		telText = new JTextField();
		telText.setBounds(30, 360, 335, 35);
		contentPane.add(telText);
		telText.setColumns(10);
		
		addressText = new JTextField();
		addressText.setBounds(30, 415, 335, 35);
		contentPane.add(addressText);
		addressText.setColumns(10);

		submitButton = new JButton("Submit");
		submitButton.setBounds(80, 457, 89, 35);
		submitButton.setFont(new Font("Lucida Grande", Font.BOLD, 15));
		submitButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				defaultWindow.Submit();
			}
		});
		contentPane.add(submitButton);
		
		register_show_passwordCheckBox = new JCheckBox("Show password");
		register_show_passwordCheckBox.setBackground(Color.BLACK);
		register_show_passwordCheckBox.setForeground(Color.WHITE);
		register_show_passwordCheckBox.setBounds(233, 260, 132, 23);
		register_show_passwordCheckBox.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (register_show_passwordCheckBox.isSelected()) {
	                register_passwordText.setEchoChar((char) 0);
	            } else {
	                //register_passwordText.setEchoChar('•');
	            }
			}
		});
		contentPane.add(register_show_passwordCheckBox);
		
		backtologin_Button = new JButton("Back to login");
		backtologin_Button.setBounds(195, 457, 132, 35);
		backtologin_Button.setFont(new Font("Lucida Grande", Font.BOLD, 15));
		backtologin_Button.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				defaultWindow.logout();
			}
		});
		contentPane.add(backtologin_Button);
		
		panel = new JPanel();
		panel.setBackground(Color.BLACK);
		panel.setBounds(10, 16, 376, 486);
		contentPane.add(panel);
		
		register_titleLabel = new JLabel("REGISTER");
		register_titleLabel.setForeground(Color.WHITE);
		register_titleLabel.setBackground(Color.WHITE);
		panel.add(register_titleLabel);
		register_titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
		register_titleLabel.setFont(new Font("Lucida Grande", Font.BOLD, 20));
	}
	
	public String getUser() {
		return usernameText.getText();
	}
	public String getPass() {
		return register_passwordText.getText();
	}
	public String getLast() {
		return lastnameText.getText();
	}
	public String getFirst() {
		return firstnameText.getText();
	}
	public String getTel() {
		return telText.getText();
	}
	public String getAddr() {
		return addressText.getText();
	}
	public LocalDate getDOB() {
		Date date = dobChooser.getDate();
		LocalDate dob = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
		return dob;
	}
}

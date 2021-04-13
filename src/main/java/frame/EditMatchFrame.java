package frame;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.UIManager;


import com.github.lgooddatepicker.components.DateTimePicker;
import com.github.lgooddatepicker.components.TimePicker;

import model.Match;
import window.WindowAdminLogin;


public class EditMatchFrame extends JFrame {

	private JPanel contentPane;
	
	private JButton btnUpdate;
	private JButton btnLogout;
	private JButton btnNew;
	
	private JTextField txtID;
	private JTextField txtHome;
	private JTextField txtAway;
	
	private DateTimePicker dtp;
	private TimePicker tp;
	
	private static JFrame errorMessage;
	
	public EditMatchFrame(WindowAdminLogin defaultWindow) {

		contentPane = new JPanel();
		contentPane.setLayout(null);
		contentPane.setFont(UIManager.getFont("Panel.font"));
		contentPane.setForeground(new Color(255, 255, 255));
		//contentPane.setTitle("Edit match\r\n");
		contentPane.setBackground(Color.WHITE);
		contentPane.setSize(480, 550);
		setBounds(200, 200, 480 , 550);
		setContentPane(contentPane);

		//Label Edit match
		JLabel lblNewLabel = new JLabel("Edit Match");
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lblNewLabel.setBounds(0, 0, 450, 60);
		contentPane.add(lblNewLabel);
		
		//Text ID
		txtID = new JTextField();
		txtID.setFont(new Font("Tahoma", Font.PLAIN, 13));
		txtID.setBounds(30, 75, 400, 50);
		contentPane.add(txtID);
		txtID.setColumns(10);
		
		//Button update
		btnUpdate = new JButton("Update");
		btnUpdate.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				System.out.println("update");
				defaultWindow.backToNewMatch();
			}
		});
		btnUpdate.setFont(new Font("Tahoma", Font.PLAIN, 15));
		btnUpdate.setBounds(80, 380, 120, 40);
		contentPane.add(btnUpdate);
		
		//Button new match
		btnNew = new JButton("New match");
		btnNew.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				System.out.println("new");
				defaultWindow.newMatch();
			}
		});
		btnNew.setFont(new Font("Tahoma", Font.PLAIN, 15));
		btnNew.setBounds(257, 380, 120, 40);
		contentPane.add(btnNew);
		
		JLabel lblNewLabel_1 = new JLabel("Match ID");
		lblNewLabel_1.setFont(new Font("Tahoma", Font.BOLD, 13));
		lblNewLabel_1.setBounds(40, 60, 100, 13);
		contentPane.add(lblNewLabel_1);
		
		dtp = new DateTimePicker();
		dtp.setBounds(30, 150, 400, 50);
		contentPane.add(dtp);
		
		
		txtHome = new JTextField();
		txtHome.setFont(new Font("Tahoma", Font.PLAIN, 13));
		txtHome.setColumns(10);
		txtHome.setBounds(30, 300, 185, 50);
		contentPane.add(txtHome);
		
		JLabel lblNewLabel_1_1 = new JLabel("Time");
		lblNewLabel_1_1.setFont(new Font("Tahoma", Font.BOLD, 13));
		lblNewLabel_1_1.setBounds(40, 135, 100, 13);
		contentPane.add(lblNewLabel_1_1);
		
		JLabel lblNewLabel_1_3_2 = new JLabel("Home");
		lblNewLabel_1_3_2.setFont(new Font("Tahoma", Font.BOLD, 13));
		lblNewLabel_1_3_2.setBounds(40, 285, 100, 13);
		contentPane.add(lblNewLabel_1_3_2);
		
		btnLogout = new JButton("Logout");
		btnLogout.setFont(new Font("Tahoma", Font.PLAIN, 15));
		btnLogout.setBounds(310, 455, 120, 40);
		btnLogout.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				defaultWindow.Stop();
				JOptionPane.showMessageDialog(errorMessage, 
						"Logout successfully", "Logout", JOptionPane.DEFAULT_OPTION);
			}
		});
		contentPane.add(btnLogout);
		
		txtAway = new JTextField();
		txtAway.setFont(new Font("Tahoma", Font.PLAIN, 13));
		txtAway.setColumns(10);
		txtAway.setBounds(245, 300, 185, 50);
		contentPane.add(txtAway);
		
		JLabel lblNewLabel_1_3_2_1 = new JLabel("Away");
		lblNewLabel_1_3_2_1.setFont(new Font("Tahoma", Font.BOLD, 13));
		lblNewLabel_1_3_2_1.setBounds(250, 286, 100, 13);
		contentPane.add(lblNewLabel_1_3_2_1);
		
	}
	
	public String getTxtID() { return txtID.getText(); }
	public String getTxtHome() { return txtHome.getText(); }
	public String getTxtAway() { return txtAway.getText(); }
	
	public LocalDateTime getDate() { 
		LocalDate date = dtp.getDatePicker().getDate();
		LocalTime time1 = dtp.getTimePicker().getTime();
		LocalDateTime tgian = LocalDateTime.of(date, time1);
		return tgian;
	}
	
	
}

package frame;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Time;
import java.time.LocalDateTime;
import java.time.LocalTime;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.UIManager;

import com.github.lgooddatepicker.components.TimePicker;

import window.WindowAdminLogin;
import javax.swing.JButton;

public class UpdateMatchFrame extends JFrame {

	private JPanel contentPane;

	private JTextField txtID;
	private JTextField txtName;
	private JTextField txtEvent;
	
	private JSpinner spinnerHome;
	private JSpinner spinnerAway;
	
	private TimePicker tp;

	public UpdateMatchFrame(WindowAdminLogin defaultWindow) {
		contentPane = new JPanel();
		contentPane.setLayout(null);
		contentPane.setFont(UIManager.getFont("Panel.font"));
		contentPane.setForeground(new Color(255, 255, 255));
		//contentPane.setTitle("Edit match\r\n");
		contentPane.setBackground(Color.WHITE);
		contentPane.setSize(480, 500);
		setBounds(200, 200, 480 , 500);
		setContentPane(contentPane);
		
		txtID = new JTextField();
		txtID.setBounds(40, 80, 400, 37);
		contentPane.add(txtID);
		
		txtName = new JTextField();
		txtName.setBounds(40, 290, 400, 37);
		contentPane.add(txtName);

		txtEvent = new JTextField();
		txtEvent.setBounds(40, 150, 400, 37);
		contentPane.add(txtEvent);
	
		tp = new TimePicker();
		tp.setBounds(40, 360, 400, 37);
		contentPane.add(tp);
		 
		JLabel lblNewLabel = new JLabel("Match ID");
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 15));
		lblNewLabel.setBounds(40, 60, 94, 13);
		contentPane.add(lblNewLabel);
		
		JLabel lblNewLabel_1 = new JLabel("Event");
		lblNewLabel_1.setFont(new Font("Tahoma", Font.BOLD, 15));
		lblNewLabel_1.setBounds(40, 130, 94, 13);
		contentPane.add(lblNewLabel_1);
		
		JLabel lblNewLabel_2 = new JLabel("Name");
		lblNewLabel_2.setFont(new Font("Tahoma", Font.BOLD, 15));
		lblNewLabel_2.setBounds(40, 270, 94, 13);
		contentPane.add(lblNewLabel_2);
		
		JLabel lblNewLabel_2_1 = new JLabel("Time");
		lblNewLabel_2_1.setFont(new Font("Tahoma", Font.BOLD, 15));
		lblNewLabel_2_1.setBounds(40, 340, 94, 13);
		contentPane.add(lblNewLabel_2_1);
		
		JButton btnUpdate = new JButton("Update");
		btnUpdate.setFont(new Font("Tahoma", Font.PLAIN, 15));
		btnUpdate.setBounds(317, 410, 123, 37);
		btnUpdate.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				defaultWindow.updateMatch();
			}
		});
		contentPane.add(btnUpdate);
		
		JButton btnBack = new JButton("Back");
		btnBack.setFont(new Font("Tahoma", Font.PLAIN, 13));
		btnBack.setBounds(10, 10, 85, 21);
		btnBack.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				defaultWindow.backToNewMatch();
			}
		});
		contentPane.add(btnBack);
		
		spinnerHome = new JSpinner();
		spinnerHome.setFont(new Font("Tahoma", Font.PLAIN, 30));
		spinnerHome.setBounds(95, 220, 120, 60);
		contentPane.add(spinnerHome);
		
		spinnerAway = new JSpinner();
		spinnerAway.setFont(new Font("Tahoma", Font.PLAIN, 30));
		spinnerAway.setBounds(257, 220, 120, 60);
		contentPane.add(spinnerAway);
		
		JLabel lblNewLabel_1_2 = new JLabel("Score");
		lblNewLabel_1_2.setFont(new Font("Tahoma", Font.BOLD, 13));
		lblNewLabel_1_2.setBounds(40, 200, 100, 13);
		contentPane.add(lblNewLabel_1_2);
	}
	
	public String getTxtID() { return txtID.getText(); }
	public String getTxtEvent() { return txtEvent.getText(); }
	public String getTxtName() { return txtName.getText(); }
	public LocalTime getTpMomment() { return tp.getTime(); }
	public int getHomeScore() { return (int) spinnerHome.getValue(); }
	public int getAwayScore() { return (int) spinnerAway.getValue(); }
}

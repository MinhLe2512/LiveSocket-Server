package frame;

import java.awt.Color;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.UIManager;

import com.github.lgooddatepicker.components.DateTimePicker;
import com.github.lgooddatepicker.components.TimePicker;

import model.JPanelWithBackground;
import window.AdminWindow;
import javax.swing.JButton;

public class UpdateMatchFrame extends JFrame {

	private JPanel contentPane;

	private JTextField txtID, txtName, txtEvent, txtHome, txtAway, txtTime;
	
	private JSpinner spinnerHome, spinnerAway;
	
	private DateTimePicker dtp;
	private JPanel panel;

	public UpdateMatchFrame(AdminWindow defaultWindow) {
		setLocationRelativeTo(null);
		setBackground(new Color(255, 255, 255));
		setIconImage(Toolkit.getDefaultToolkit().getImage(
				"C:\\Users\\DELL\\Downloads\\livescore_icon.png"));
		setResizable(false);
			try {
				contentPane = new JPanelWithBackground(
						"C:\\Users\\DELL\\Downloads\\livescore_bg.png");
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		contentPane.setLayout(null);
		contentPane.setFont(UIManager.getFont("Panel.font"));
		contentPane.setForeground(new Color(255, 255, 255));
		//contentPane.setTitle("Edit match\r\n");
		contentPane.setBackground(Color.WHITE);
		contentPane.setSize(1080, 500);
		setBounds(200, 200, 1009 , 500);
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
	
		 
		JLabel lblNewLabel = new JLabel("Match ID");
		lblNewLabel.setForeground(Color.WHITE);
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 15));
		lblNewLabel.setBounds(40, 60, 94, 13);
		contentPane.add(lblNewLabel);
		
		JLabel lblNewLabel_1 = new JLabel("Event");
		lblNewLabel_1.setForeground(Color.WHITE);
		lblNewLabel_1.setFont(new Font("Tahoma", Font.BOLD, 15));
		lblNewLabel_1.setBounds(40, 130, 94, 13);
		contentPane.add(lblNewLabel_1);
		
		JLabel lblNewLabel_2 = new JLabel("Name");
		lblNewLabel_2.setForeground(Color.WHITE);
		lblNewLabel_2.setFont(new Font("Tahoma", Font.BOLD, 15));
		lblNewLabel_2.setBounds(40, 270, 94, 13);
		contentPane.add(lblNewLabel_2);
		
		JLabel lblNewLabel_2_1 = new JLabel("Time");
		lblNewLabel_2_1.setForeground(Color.WHITE);
		lblNewLabel_2_1.setFont(new Font("Tahoma", Font.BOLD, 15));
		lblNewLabel_2_1.setBounds(40, 340, 94, 13);
		contentPane.add(lblNewLabel_2_1);
		
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
		lblNewLabel_1_2.setForeground(Color.WHITE);
		lblNewLabel_1_2.setFont(new Font("Tahoma", Font.BOLD, 15));
		lblNewLabel_1_2.setBounds(40, 200, 100, 13);
		contentPane.add(lblNewLabel_1_2);
		
		dtp = new DateTimePicker();
		dtp.setBounds(480, 80, 500, 37);
		contentPane.add(dtp);
		
		txtHome = new JTextField();
		txtHome.setBounds(480, 150, 246, 37);
		contentPane.add(txtHome);
		txtHome.setColumns(10);
		
		txtAway = new JTextField();
		txtAway.setColumns(10);
		txtAway.setBounds(480, 219, 246, 37);
		contentPane.add(txtAway);
		
		JLabel lblNewLabel_3 = new JLabel("Time");
		lblNewLabel_3.setForeground(Color.WHITE);
		lblNewLabel_3.setFont(new Font("Tahoma", Font.BOLD, 15));
		lblNewLabel_3.setBounds(480, 60, 105, 13);
		contentPane.add(lblNewLabel_3);
		
		JLabel lblNewLabel_3_1 = new JLabel("Home");
		lblNewLabel_3_1.setForeground(Color.WHITE);
		lblNewLabel_3_1.setFont(new Font("Tahoma", Font.BOLD, 15));
		lblNewLabel_3_1.setBounds(480, 132, 105, 13);
		contentPane.add(lblNewLabel_3_1);
		
		JLabel lblNewLabel_3_1_1 = new JLabel("Away");
		lblNewLabel_3_1_1.setForeground(Color.WHITE);
		lblNewLabel_3_1_1.setFont(new Font("Tahoma", Font.BOLD, 15));
		lblNewLabel_3_1_1.setBounds(480, 202, 105, 13);
		contentPane.add(lblNewLabel_3_1_1);
		
		txtTime = new JTextField();
		txtTime.setBounds(40, 363, 400, 37);
		contentPane.add(txtTime);
		
		panel = new JPanel();
		panel.setBackground(Color.BLACK);
		panel.setBounds(20, 33, 982, 418);
		contentPane.add(panel);
		panel.setLayout(null);
		
		JButton btnUpdate = new JButton("Update");
		btnUpdate.setBounds(819, 371, 123, 37);
		panel.add(btnUpdate);
		btnUpdate.setFont(new Font("Tahoma", Font.PLAIN, 15));
		btnUpdate.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				defaultWindow.updateMatch();
			}
		});
	}
	
	public String getTxtID() { return txtID.getText(); }
	public String getTxtEvent() { return txtEvent.getText(); }
	public String getTxtName() { return txtName.getText(); }
	public String getTpMomment() { return txtTime.getText(); }
	public String getTxtHome() { return txtHome.getText(); }
	public String getTxtAway() { return txtAway.getText(); }
	public int getHomeScore() { return (int) spinnerHome.getValue(); }
	public int getAwayScore() { return (int) spinnerAway.getValue(); }
	
	public LocalDateTime getUpdateTime() { 
		LocalDate date = dtp.getDatePicker().getDate();
		LocalTime time1 = dtp.getTimePicker().getTime();
		if (date == null || time1 == null)
			return null;
		LocalDateTime tgian = LocalDateTime.of(date, time1);
		return tgian;
	}
	
	public void clearText() {
		txtID.setText("");
		txtName.setText("");
		txtEvent.setText("");
		txtHome.setText("");
		txtAway.setText("");
		txtTime.setText("");
		spinnerAway.setValue(0);
		spinnerHome.setValue(0);
	}
}

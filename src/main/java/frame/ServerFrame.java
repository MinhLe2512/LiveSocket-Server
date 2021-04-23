package frame;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import model.JPanelWithBackground;
import window.ServerWindow;

public class ServerFrame extends JFrame {

	private JPanel contentPane;
	private JTextArea txtAreaLogs;
	private JButton btnStart;
	private JLabel lblChatServer;

	/**
	 * Create the frame.
	 */
	public ServerFrame(ServerWindow defaultWindow) {
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
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 570, 400);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout(0, 0));
		setLocationRelativeTo(null);
		
		lblChatServer = new JLabel("LIVESCORE SERVER");
		lblChatServer.setBackground(Color.YELLOW);
		lblChatServer.setForeground(Color.WHITE);
		lblChatServer.setHorizontalAlignment(SwingConstants.CENTER);
		lblChatServer.setFont(new Font("Tahoma", Font.PLAIN, 40));
		contentPane.add(lblChatServer, BorderLayout.NORTH);

		btnStart = new JButton("START");
		btnStart.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				if(btnStart.getText().equals("START")) {
					ServerWindow.setExit(false);
					ServerWindow.start();
					btnStart.setText("STOP");
				}else {
					ServerWindow.addToLogs("Chat server stopped...");
					ServerWindow.setExit(true);
					btnStart.setText("START");
				}
				defaultWindow.refreshUIComponents(lblChatServer);
			}
		});
		btnStart.setFont(new Font("Tahoma", Font.PLAIN, 30));
		contentPane.add(btnStart, BorderLayout.SOUTH);

	}

}

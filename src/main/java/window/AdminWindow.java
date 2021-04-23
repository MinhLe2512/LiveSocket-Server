package window;


import java.awt.EventQueue;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.time.LocalDateTime;


import javax.swing.JFrame;
import javax.swing.JOptionPane;

import frame.EditMatchFrame;
import frame.AdminLoginFrame;
import frame.UpdateMatchFrame;
import model.Event;
import model.Login;
import model.Match;

public class AdminWindow {

	private static AdminLoginFrame frmAdminLogin;
	private EditMatchFrame frmEditMatch;
	private UpdateMatchFrame frmUpdateMatch;
	private JFrame errorMessage;
	
	
	private static Socket adminSocket;
	private static int port = 52177;
	private ObjectOutputStream objOut;
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					new AdminWindow();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	public AdminWindow() {
		// TODO Auto-generated constructor stub
		frmAdminLogin = new AdminLoginFrame(this);
		frmUpdateMatch = new UpdateMatchFrame(this);
		frmAdminLogin.setVisible(true);
	}
	
	
	public void loginStart() {
		try {
			adminSocket = new Socket("localhost", port);
			new Thread(new LoginListener()).start();
			objOut = new ObjectOutputStream(adminSocket.getOutputStream());
			//Send account
			objOut.writeUTF("ADMINLOGIN");
			objOut.flush();

		} catch (Exception err) {
			//addToLogs("[ERROR] "+err.getLocalizedMessage());
		}
	}
	
	public Socket getAdminSocket() {
		return adminSocket;
	}
	
	public void Stop(){
		if(!adminSocket.isClosed()) {
			try {
				adminSocket.close();
				frmAdminLogin.clearText();
				frmEditMatch.setVisible(false);
				frmAdminLogin.setVisible(true);
			} catch (IOException e1) {}
		}
	}

	private class LoginListener implements Runnable {
		private ObjectInputStream in;
		@Override
		public void run() {
			try {
				in = new ObjectInputStream(adminSocket.getInputStream());
				String read;
				while (true) {
					read = in.readUTF();
					if (read.equals("CONNECTED")) {
						frmAdminLogin.setVisible(false);
						
						//initFrameEditMatch();
						frmEditMatch = new EditMatchFrame(AdminWindow.this);
						frmEditMatch.setVisible(true);
					}
					else if (read.equals("FAILED")) {
						frmAdminLogin.clearText();
						JOptionPane.showMessageDialog(errorMessage, 
								"Invalid username or password", read, JOptionPane.WARNING_MESSAGE);
					}
					else if (read.equals("UPDATED")) {
						//updateMatch();
						frmEditMatch.clearText();
						JOptionPane.showMessageDialog(errorMessage,
								"Data has been updated", read, JOptionPane.DEFAULT_OPTION);
					}
					else if (read.equals("EVENT UPDATE")) {
						String event = frmUpdateMatch.getTxtEvent(),
								name = frmUpdateMatch.getTxtName(),
								evTime = frmUpdateMatch.getTpMomment(),
								id = frmUpdateMatch.getTxtID();
						Event ev = new Event(id, evTime, event, name);
						objOut.writeObject(ev);
					}
					else if (read.equals("DONE")) {
						frmUpdateMatch.clearText();
						JOptionPane.showMessageDialog(errorMessage, 
								"Date has been updated", read, JOptionPane.DEFAULT_OPTION);
					}
					else if (read.equals("ADMIN")) {
						String str1 = frmAdminLogin.getUser(),
								str2 = frmAdminLogin.getPassword();
						Login acc = new Login(str1, str2, "Admin");
						try {
							objOut.writeObject(acc);
							objOut.flush();
						} catch (IOException e1) {
							JOptionPane.showMessageDialog(errorMessage, 
									"Can't send object", "Error", JOptionPane.WARNING_MESSAGE);
							e1.printStackTrace();
						}
					}
				}
			}catch (IOException e) { return; }
		}
	}
	
	public void newMatch() {
		String id = frmEditMatch.getTxtID(), 
				home = frmEditMatch.getTxtHome(),
				away = frmEditMatch.getTxtAway();
		
		LocalDateTime tgian = frmEditMatch.getDate();
		
		Match match = new Match(id, home, away, 0, 0, tgian, "new match");
		try {
			objOut.writeObject(match);
		} catch (IOException e) {
			JOptionPane.showMessageDialog(errorMessage, 
					"Can't new match", "Error", JOptionPane.WARNING_MESSAGE);
			e.printStackTrace();
		}
		
	}
	
	public void updateMatch() {
//		initUpdateFrame();
		String id = frmUpdateMatch.getTxtID(),
				home = frmUpdateMatch.getTxtHome(),
				away = frmUpdateMatch.getTxtAway();
		
		LocalDateTime time = frmUpdateMatch.getUpdateTime();
		
		int scoreHome = frmUpdateMatch.getHomeScore(),
				scoreAway = frmUpdateMatch.getAwayScore();

		Match match = new Match(id, home, away, scoreHome, scoreAway, time, "update");
		
		try {
			objOut.writeObject(match);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			JOptionPane.showMessageDialog(errorMessage, "Can't update match", "Error", JOptionPane.WARNING_MESSAGE);
			e.printStackTrace();
		}
	}
	
	public void backToNewMatch() {
		if (frmEditMatch.isVisible()) {
			frmEditMatch.setVisible(false);
			frmUpdateMatch.setVisible(true);
		}
		else {
			frmEditMatch.setVisible(true);
			frmUpdateMatch.setVisible(false);
		}
	}
}

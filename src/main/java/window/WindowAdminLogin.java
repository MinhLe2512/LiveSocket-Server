package window;


import java.awt.EventQueue;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.function.ObjIntConsumer;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import frame.EditMatchFrame;
import frame.LoginFrame;
import frame.UpdateMatchFrame;
import model.Event;
import model.Login;
import model.Match;

public class WindowAdminLogin {

	private static LoginFrame frmAdminLogin;
	//private static JFrame frmEditMatch;
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
					new WindowAdminLogin();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	public WindowAdminLogin() {
		// TODO Auto-generated constructor stub
		frmAdminLogin = new LoginFrame(this);
		frmUpdateMatch = new UpdateMatchFrame(this);
		frmAdminLogin.setVisible(true);
	}
	
	
	public void loginStart() {
		try {
			adminSocket = new Socket("localhost", port);
			new Thread(new LoginListener()).start();
			//Send account
			String str1 = frmAdminLogin.getUser(),
					str2 = frmAdminLogin.getPassword();
			Login acc = new Login(str1, str2, "Admin");
			try {
				objOut = new ObjectOutputStream(adminSocket.getOutputStream());
				objOut.writeObject(acc);
			} catch (IOException e1) {
				JOptionPane.showMessageDialog(errorMessage, "Can't send object", "Error", JOptionPane.WARNING_MESSAGE);
				e1.printStackTrace();
			}

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
		private BufferedReader in;
		@Override
		public void run() {
			try {
				in = new BufferedReader(new InputStreamReader(adminSocket.getInputStream()));
				String read;
				while (true) {
					read = in.readLine();
					if (read.equals("CONNECTED")) {
						frmAdminLogin.setVisible(false);
						
						//initFrameEditMatch();
						frmEditMatch = new EditMatchFrame(WindowAdminLogin.this);
						frmEditMatch.setVisible(true);
					}
					else if (read.equals("FAILED")) {
						frmAdminLogin.clearText();
						JOptionPane.showMessageDialog(errorMessage, 
								"Invalid username or password", read, JOptionPane.WARNING_MESSAGE);
					}
					else if (read.equals("UPDATED")) {
						//updateMatch();
						JOptionPane.showMessageDialog(errorMessage,
								"Data has been updated", read, JOptionPane.DEFAULT_OPTION);
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
		
		Match match = new Match(id, home, away, 0, 0, tgian, "new", null);
		try {
			objOut.writeObject(match);
		} catch (IOException e) {
			JOptionPane.showMessageDialog(errorMessage, "Can't new match", "Error", JOptionPane.WARNING_MESSAGE);
			e.printStackTrace();
		}
		
	}
	
	public void updateMatch() {
//		initUpdateFrame();
		String id = frmUpdateMatch.getTxtID(),
				event = frmUpdateMatch.getTxtEvent(),
				name = frmUpdateMatch.getName(),
				home = frmEditMatch.getTxtHome(),
				away = frmEditMatch.getTxtAway();
		
		LocalDateTime time = frmEditMatch.getDate();
		
		int scoreHome = frmUpdateMatch.getHomeScore(),
				scoreAway = frmUpdateMatch.getAwayScore();
		
		LocalTime evTime = frmUpdateMatch.getTpMomment();
		Event ev = new Event(evTime, event, name);

		Match match = new Match(id, home, away, scoreHome, scoreAway, time, "update", ev);
		
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

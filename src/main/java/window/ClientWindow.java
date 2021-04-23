package window;

import java.awt.Color;
import java.awt.EventQueue;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.time.LocalDate;
import java.util.List;

import javax.swing.JOptionPane;
import javax.swing.UIManager;
import javax.swing.plaf.ColorUIResource;

import frame.DetailFrame;
import frame.LivescoreFrame;
import frame.ClientLoginFrame;
import frame.RegisterFrame;

import model.Event;
import model.Login;
import model.Match;
import model.Register;

public class ClientWindow {
	//frame
	private static ClientLoginFrame frmLogin;
	private static RegisterFrame frmRegister;
	private static LivescoreFrame frmLivescore;
	private static DetailFrame frmDetail;
	
	//Socket
	private static Socket clientSocket;
	private static ObjectOutputStream objOut;
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		//table grid
		UIManager.put("Table.gridColor", new ColorUIResource(Color.gray));
		
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					new ClientWindow();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	public ClientWindow() {
		frmLogin = new ClientLoginFrame(this);
		frmRegister = new RegisterFrame(this);
		frmLivescore = new LivescoreFrame(this);
		frmDetail = new DetailFrame(this);
		frmLogin.setVisible(true);
	}
	private class LoginListener implements Runnable {
		private ObjectInputStream in;
		@Override
		public void run() {
			try {
				in = new ObjectInputStream(clientSocket.getInputStream());
				String read;
				while (true) {
					read = in.readUTF();
					if (read.equals("CLIENT")) {
						String user = frmLogin.getUser();
						String pass = frmLogin.getPass();
						Login log = new Login(user, pass, "Client");
						objOut.writeObject(log);
					}
					else if (read.equals("FAILED")) {
						JOptionPane.showMessageDialog(null, "Login failed");
					}
					else if (read.equals("CLIENT CONNECTED")) {
						frmLogin.setVisible(false);
						
						objOut.writeUTF("ALL MATCH");
						objOut.flush();
						
						try {
							List<Match> matchList = (List<Match>) in.readObject();
							frmLivescore.setVisible(true);
							frmLivescore.showMatch(matchList);
						} catch (ClassNotFoundException e) {};
					}
					else if (read.equals("LISTEVENT")) {
						frmLivescore.setVisible(false);
						
						objOut.writeUTF(frmLivescore.getSelectedID());
						objOut.flush();
						
						try {
							List<Event> eventList = (List<Event>) in.readObject();
							frmDetail.setVisible(true);
							frmDetail.showDetail(eventList);
						} catch (ClassNotFoundException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
					else if (read.equals("SEARCHMATCH")) {
						objOut.writeUTF(frmLivescore.getFindObj());
						objOut.flush();
						
						try {
							List<Match> matchList = (List<Match>) in.readObject();
							frmLivescore.showMatch(matchList);
						} catch (ClassNotFoundException e) {
							e.printStackTrace();
						};
					}
					else if (read.equals("REGISTERED")) {
						JOptionPane.showMessageDialog(null, "Register successfully!");
						logout();
						frmRegister.setVisible(false);
						frmLogin.setVisible(true);
					}
					else if (read.equals("Begin registering")) {
						frmLogin.setVisible(false);
						frmRegister.setVisible(true);
					}
				}
			}catch (IOException e) { return; }
		}
	}

	public void LoginStart() {
		try {
			clientSocket = new Socket("localhost", 52177);
			new Thread(new LoginListener()).start();
			objOut = new ObjectOutputStream(clientSocket.getOutputStream());
			objOut.writeUTF("CLIENTLOGIN");
			objOut.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public void RegisterStart() {
		try {
			clientSocket = new Socket("localhost", 52177);
			new Thread(new LoginListener()).start();
			objOut = new ObjectOutputStream(clientSocket.getOutputStream());
			objOut.writeUTF("Register");
			objOut.flush();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void Submit() {
		String user = frmRegister.getUser();
		String firstname = frmRegister.getFirst();
		String lastname = frmRegister.getLast();
		String pass = frmRegister.getPass();
		LocalDate dob = frmRegister.getDOB();
		String tel = frmRegister.getTel();
		String address = frmRegister.getAddr();
		
		if(user.length() == 0)
			JOptionPane.showMessageDialog(null, "Username is required");
		else if(firstname.length() == 0)
			JOptionPane.showMessageDialog(null, "First name is required");
		else if (pass.length() < 8)
			JOptionPane.showMessageDialog(null, "Password must be at least 8 characters");
		else if(tel.length() > 0 && (!tel.matches("^[0-9]+$") || tel.length() > 10))
			JOptionPane.showMessageDialog(null, "Invalid telephone number");
		else {
			try {
				Register reg = new Register(user, pass, lastname,
						firstname, dob, address, tel, "Submit");
				objOut.writeObject(reg);
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
	}
	public void Detail() {
		try {
			objOut.writeUTF("DETAILS");
			objOut.flush();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	}
	public void logout() {
		if (!clientSocket.isClosed()) {
			try {
//				objOut.writeUTF("LOGOUT");
				clientSocket.close();
				frmLivescore.setVisible(false);
				frmRegister.setVisible(false);
				frmLogin.setVisible(true);
				frmLogin.clearText();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	public void search() {
		try {
			objOut.writeUTF("SEARCH");
			objOut.flush();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	}
	public void Back() {
		frmDetail.setVisible(false);
		frmLivescore.setVisible(true);
	}
}


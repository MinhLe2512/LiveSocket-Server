package window;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;


import frame.ServerFrame;
import model.Event;
import model.Login;
import model.Match;
import model.Register;


public class ServerWindow {

	public static SimpleDateFormat formatter = new SimpleDateFormat("[hh:mm a]");
	private static HashMap<String, PrintWriter> connectedClients = new HashMap<>();
	private static final int MAX_CONNECTED = 50;
	private static int PORT = 52177;
	
	private static ServerSocket server;
	private static volatile boolean exit = false;
	

	private static Connection connection;
	
	private static String url = "jdbc:sqlserver://desktop-68p0vho\\sqlexpress;databaseName=DB_LivescoreServer",
			user = "sa",
			password = "25122001Abc",
			sqlInsertClient = "insert into CLIENT values(?, ?, ?, ?, ?, ?, ?)",
			sqlInsertMatch = "insert into MATCH values(?, ?, ?, ?, ?, ?)",
			sqlSelectMatch = "select * from MATCH where MATCHID = ";
	
	private static ServerFrame serverFrame;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ServerWindow window = new ServerWindow();
					serverFrame = new ServerFrame(window);
					serverFrame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	public void refreshUIComponents(JLabel lblChatServer) {
		lblChatServer.setText("LIVESCORE" + (!exit ? ": "+PORT:""));
	}

	public static void start() {
		new Thread(new ServerHandler()).start();
	}

	public static void stop() throws IOException {
		if (!server.isClosed()) server.close();
	}
	
	public static void setExit(boolean val) {
		exit = val;
	}
	public static void addToLogs(String message) {
		System.out.printf("%s %s\n", formatter.format(new Date()), message);
	}

	private static class ServerHandler implements Runnable{
		@Override
		public void run() {
			try {
				InetAddress addr = InetAddress.getByName("0.0.0.0");
				
				server = new ServerSocket(PORT, 50, addr);
				
				addToLogs("Server started on port: " + PORT);
				addToLogs("Now listening for connections...");
				while(!exit) {
					if (connectedClients.size() <= MAX_CONNECTED){
						new Thread(new ClientHandler(server.accept())).start();
					}
				}
			}
			catch (Exception e) {
				addToLogs("\nError occured: \n");
				addToLogs(Arrays.toString(e.getStackTrace()));
				addToLogs("\nExiting...");
			}
		}
	}
	
	// Start of Client Handler
	private static class ClientHandler implements Runnable {
		private Socket socket;
		private PrintWriter out;
		//private Reader
		private ObjectInputStream objIn;
		private ObjectOutputStream objOut;
		private Login name;
		private String message;
		
		
		public ClientHandler(Socket socket) {
			this.socket = socket;
		}
		
		@Override
		public void run(){
			//connectedClients.size() +
			addToLogs(connectedClients.size() + 1 + " connected: " + socket.getInetAddress());
			try {
				//out = new PrintWriter(socket.getOutputStream(), true);
				objOut = new ObjectOutputStream(socket.getOutputStream());
				objIn = new ObjectInputStream(socket.getInputStream());
				
				boolean loginAdmin = false;
				boolean loginClient = false;
				boolean clientRegister = false;
				
				for(;;) {
					message = objIn.readUTF();
					
					if (message.equals("ADMINLOGIN")) {
						objOut.writeUTF("ADMIN");
						objOut.flush();
						name = (Login)objIn.readObject();
						if (name.getU().equals("HIMIKE") &&
								name.getPW().equals("12345678") &&
								name.getObjID().equals("Admin") ) {
							addToLogs("Admin login successfully");
							objOut.writeUTF("CONNECTED");
							objOut.flush();
							loginAdmin = true;
							break;
						}
						else {
							addToLogs("Login failed");
							objOut.writeUTF("FAILED");
							objOut.flush();
						}
					}
					//Login Client
					else if (message.equals("CLIENTLOGIN")) {
						objOut.writeUTF("CLIENT");
						objOut.flush();
						name = (Login)objIn.readObject();
						
						connection = DriverManager.getConnection(url, user, password);
						java.sql.Statement stm = connection.createStatement();
						ResultSet rs = stm.executeQuery("select * from CLIENT where username = '" 
						+ name.getU() +"' and pw = '" +  name.getPW() + "'");
						
						if (rs.next()) {
							addToLogs("Client login successfully");
							objOut.writeUTF("CLIENT CONNECTED");
							objOut.flush();
							loginClient = true;
							break;
						}
						else {
							addToLogs("Login failed");
							objOut.writeUTF("FAILED");
							objOut.flush();
						}
					}
					else if (message.equals("Register")) {

						Login guest = new Login("guest "+ connectedClients.size()
						, "guest", "client");
						name = guest;
						addToLogs("Registering");
						objOut.writeUTF("Begin registering");
						objOut.flush();
						clientRegister = true;
						break;
					}
				}
				synchronized (connectedClients) {
					if (!connectedClients.keySet().contains(name.getU()))
						connectedClients.put(name.getU(), out);
					else { 
						connectedClients.remove(name.getU());
						objOut.writeUTF("This account has already login");
						objOut.flush();
					}
				}
				while (loginAdmin && !exit) {
					Match match = (Match) objIn.readObject();
					if (match.getObjID().equals("new match")) {
						connection = DriverManager.getConnection(url, user, password);
						PreparedStatement pst = connection.prepareStatement(sqlInsertMatch);
						pst.setString(1, match.getID());
						pst.setString(2, match.getHome());
						pst.setString(3, match.getAway());
						pst.setInt(4, match.getScoreHome());
						pst.setInt(5, match.getScoreAway());
						pst.setObject(6, match.getTime());
						pst.execute();
						objOut.writeUTF("UPDATED");
						objOut.flush();
					}
					else if (match.getObjID().equals("update")) {
						//Event ev = match.getEvent();
						connection = DriverManager.getConnection(url, user, password);
						java.sql.Statement stm = connection.createStatement();
						ResultSet rs = stm.executeQuery(sqlSelectMatch + match.getID());
						
						while (rs.next()) {
							if (match.getTime() != null) {
								PreparedStatement pst = connection.prepareStatement(
										"update MATCH set TGIAN = ? where MATCHID = ?");
								pst.setObject(1, match.getTime());
								pst.setString(2, match.getID());
								pst.execute();
							}
							
							if (!match.getHome().equals("")) {
								PreparedStatement pst = connection.prepareStatement(
										"update MATCH set HOME = ? where MATCHID = ?");
								pst.setString(1, match.getHome());
								pst.setString(2, match.getID());
								pst.execute();
							}
							
							if (!match.getAway().equals("")) {
								PreparedStatement pst = connection.prepareStatement(
										"update MATCH set AWAY = ? where MATCHID = ?");
								pst.setString(1, match.getAway());
								pst.setString(2, match.getID());
								pst.execute();
							}
							
							if (match.getScoreHome() != rs.getInt("SCOREHOME")) {
								PreparedStatement pst = connection.prepareStatement(
										"update MATCH set SCOREHOME = ? where MATCHID = ?");
								pst.setInt(1, match.getScoreHome());
								pst.setString(2, match.getID());
								pst.execute();
							}
							
							if (match.getScoreHome() != rs.getInt("SCOREAWAY")) {
								PreparedStatement pst = connection.prepareStatement(
										"update MATCH set SCOREAWAY = ? where MATCHID = ?");
								pst.setInt(1, match.getScoreAway());
								pst.setString(2, match.getID());
								pst.execute();
							}
						}
						objOut.writeUTF("EVENT UPDATE");
						objOut.flush();
						Event ev = (Event) objIn.readObject();
						if (ev.getEvent() != null &&
								ev.getName() != null
								&& ev.getID() != null
								&& ev.getMomment() != null) {
							PreparedStatement pst = connection.prepareStatement(
									"insert into EVENT values(?, ?, ?, ?)");
							pst.setString(1, ev.getID());
							pst.setString(2, ev.getMomment());
							pst.setString(3, ev.getEvent());
							pst.setString(4, ev.getName());
							pst.execute();
						}
						objOut.writeUTF("DONE");
						objOut.flush();
					}
				}
	
				while (clientRegister && !exit) {
					Register reg = (Register)objIn.readObject();
					connection = DriverManager.getConnection(url, user, password);
					PreparedStatement pst = connection.prepareStatement(sqlInsertClient);
					pst.setString(1, reg.getUsername());
					pst.setString(2, reg.getPassword());
					pst.setString(3, reg.getLastname());
					pst.setString(4, reg.getFirstname());
					pst.setObject(5, reg.getDob());
					pst.setString(6, reg.getAddress());
					pst.setString(7, reg.getTelNo());
					pst.execute();
					objOut.writeUTF("REGISTERED");
					objOut.flush();
					clientRegister = false;
				}
				
				while (loginClient && !exit) {
//					objOut.writeUTF("LIST MATCH");
//					objOut.flush();
					if (objIn.readUTF().equals("ALL MATCH"))
					{
						List<Match> listMatch = new ArrayList<>();
						connection = DriverManager.getConnection(url, user, password);
						java.sql.Statement stm = connection.createStatement();
						ResultSet rs = stm.executeQuery("select * from MATCH");
						while (rs.next()) {
							java.sql.Timestamp dt = (java.sql.Timestamp) rs.getObject("TGIAN");
							listMatch.add(new Match(rs.getString("MATCHID"),
									rs.getString("HOME"),
									rs.getString("AWAY"),
									rs.getInt("SCOREHOME"),
									rs.getInt("SCOREAWAY"),
									dt.toLocalDateTime() ,""));
						}
						objOut.writeObject(listMatch);
						objOut.flush();
					}
					else if (objIn.readUTF().equals("DETAILS")) {
						objOut.writeUTF("LISTEVENT");
						objOut.flush();
						String id = objIn.readUTF();
						List<Event> listEvent = new ArrayList<Event>();
						connection = DriverManager.getConnection(url, user, password);
						java.sql.Statement stm = connection.createStatement();
						
						ResultSet rs = stm.executeQuery("select * from EVENT where MATCHID = " + id);
						while (rs.next()) {
							listEvent.add(new Event(rs.getString("MATCHID"),
									rs.getString("PHUT"),
									rs.getString("SUKIEN"),
									rs.getString("TEN")));
						}
						objOut.writeObject(listEvent);
						objOut.flush();
					}
					else if (objIn.readUTF().equals("SEARCH")) {
						objOut.writeUTF("SEARCHMATCH");
						objOut.flush();
						
						String matchName = objIn.readUTF();
						List<Match> listMatch = new ArrayList<>();
						
						connection = DriverManager.getConnection(url, user, password);
						java.sql.Statement stm = connection.createStatement();
						
						ResultSet rs = stm.executeQuery("select * from MATCH where HOME LIKE N'" 
						+ matchName + "%' or AWAY LIKE N'" + matchName + "%'");
						
						while (rs.next()) {
							java.sql.Timestamp dt = (java.sql.Timestamp) rs.getObject("TGIAN");
							listMatch.add(new Match(rs.getString("MATCHID"),
									rs.getString("HOME"),
									rs.getString("AWAY"),
									rs.getInt("SCOREHOME"),
									rs.getInt("SCOREAWAY"),
									dt.toLocalDateTime(), ""));
						}
						
						objOut.writeObject(listMatch);
						objOut.flush();
					}
				}
			} catch (Exception e) {
				addToLogs(e.getMessage());
				connectedClients.remove(name.getU());
			} finally {
				if (message.equals("LOGOUT")) {
					addToLogs(name.getU() + " is leaving");
					connectedClients.remove(name.getU());
				}
				//broadcastMessage(name.getU() + " has left");
			}
		}
	}
}

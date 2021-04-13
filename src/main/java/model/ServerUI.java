package model;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.font.NumericShaper.Range;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketAddress;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import java.awt.Font;
import javax.swing.JButton;
import javax.swing.JTextArea;
import javax.swing.JScrollPane;
import java.awt.Color;

public class ServerUI extends JFrame implements ActionListener {	

	// Socket Related
	public static SimpleDateFormat formatter = new SimpleDateFormat("[hh:mm a]");
	private static HashMap<String, PrintWriter> connectedClients = new HashMap<>();
	private static final int MAX_CONNECTED = 50;
	private static int PORT = 52177;
	
	private static ServerSocket server;
	private static volatile boolean exit = false;
	private static boolean loginAdmin = false;
	private static boolean clientRegister = false;
	// JFrame related
	private JPanel contentPane;
	private JTextArea txtAreaLogs;
	private JButton btnStart;
	private JLabel lblChatServer;

	private static Connection connection;
	
	private static String url = "jdbc:sqlserver://desktop-68p0vho\\sqlexpress;databaseName=DB_LivescoreServer";
	private static String user = "sa";
	private static String password = "25122001Abc";
	private static String sqlInsertClient = "insert into CLIENT values(?, ?, ?, ?, ?, ?, ?)";
	private static String sqlInsertMatch = "insert into MATCH values(?, ?, ?, ?, ?, ?)";
	private static String sqlUpdateMatch = "insert into EVENT values(?, ?, ? ,?)";
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ServerUI frame = new ServerUI();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public ServerUI() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 570, 400);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout(0, 0));

		lblChatServer = new JLabel("LIVESCORE SERVER");
		lblChatServer.setHorizontalAlignment(SwingConstants.CENTER);
		lblChatServer.setFont(new Font("Tahoma", Font.PLAIN, 40));
		contentPane.add(lblChatServer, BorderLayout.NORTH);

		btnStart = new JButton("START");
		btnStart.addActionListener(this);
		btnStart.setFont(new Font("Tahoma", Font.PLAIN, 30));
		contentPane.add(btnStart, BorderLayout.SOUTH);

		JScrollPane scrollPane = new JScrollPane();
		contentPane.add(scrollPane, BorderLayout.CENTER);

		txtAreaLogs = new JTextArea();
		txtAreaLogs.setBackground(Color.BLACK);
		txtAreaLogs.setForeground(Color.WHITE);
		txtAreaLogs.setLineWrap(true);
		scrollPane.setViewportView(txtAreaLogs);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == btnStart) {
			if(btnStart.getText().equals("START")) {
				exit = false;
				start();
				btnStart.setText("STOP");
			}else {
				addToLogs("Chat server stopped...");
				exit = true;
				btnStart.setText("START");
			}
		}
		
		//Refresh UI
		refreshUIComponents();
	}
	
	public void refreshUIComponents() {
		lblChatServer.setText("LIVESCORE" + (!exit ? ": "+PORT:""));
	}

	public static void start() {
		new Thread(new ServerHandler()).start();
	}

	public static void stop() throws IOException {
		if (!server.isClosed()) server.close();
	}

	private static void broadcastMessage(String message) {
		for (PrintWriter p: connectedClients.values()) {
			p.println(message);
		}
	}
	
	public static void addToLogs(String message) {
		System.out.printf("%s %s\n", formatter.format(new Date()), message);
	}

	private static class ServerHandler implements Runnable{
		@Override
		public void run() {
			try {
				InetAddress addr = InetAddress.getByName("0.0.0.0");
				//InetAddress addr = InetAddress.getByName(" 127.0.0.1");
				
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
		private ObjectInputStream objIn;
		private Login name;
		
		public ClientHandler(Socket socket) {
			this.socket = socket;
		}
		
		@Override
		public void run(){
			//connectedClients.size() +
			addToLogs(connectedClients.size() + " connected: " + socket.getInetAddress());
			try {
				out = new PrintWriter(socket.getOutputStream(), true);
		
				objIn = new ObjectInputStream(socket.getInputStream());
				for(;;) {
					//Login Administrator
					name = (Login)objIn.readObject();
					if (name.getU().equals("HIMIKE") && name.getPW().equals("gokugod123") && name.getObjID().equals("Admin")) {
						addToLogs("Admin login successfully");
						out.println("CONNECTED");
						loginAdmin = true;
						break;
					}
					//Login Client
					else if (name.getObjID().equals("Client")) {
						addToLogs("Client login successfully");
						out.println("CONNECTED");
						break;
					}
					else if (name.getObjID().equals("Register")) {
						addToLogs("Registering");
						out.println("Begin registering");
						clientRegister = true;
						break;
					}
					else {
						//Login failed
						addToLogs("Login failed");
						out.println("FAILED");
					}
					synchronized (connectedClients) {
						if (!name.getU().isEmpty() && !connectedClients.keySet().contains(name.getU())) break;
						else out.println("This account has already login");
					}
				}
				connectedClients.put(name.getU(), out);
				while (loginAdmin && !exit) {
					Match match = (Match) objIn.readObject();
					if (match.getObjID().equals("new")) {
						connection = DriverManager.getConnection(url, user, password);
						PreparedStatement pst = connection.prepareStatement(sqlInsertMatch);
						pst.setString(1, match.getID());
						pst.setString(2, match.getHome());
						pst.setString(3, match.getAway());
						pst.setInt(4, match.getScoreHome());
						pst.setInt(5,  match.getScoreAway());
						pst.setObject(6, match.getTime());
						pst.execute();
						out.println("NEW MATCH");
					}
					else if (match.getObjID().equals("update")) {
						Event ev = match.getEvent();
						connection = DriverManager.getConnection(url, user, password);
						PreparedStatement pst = connection.prepareStatement(sqlUpdateMatch);
						pst.setString(1, match.getID());
						pst.setString(2, ev.getEvent());
						pst.setString(3, ev.getName());
						pst.setObject(4, ev.getMomment());
						pst.execute();
						out.println("UPDATED");
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
					pst.setObject(5,  reg.getDob());
					pst.setString(6, reg.getAddress());
					pst.setString(7, reg.getTelNo());
					pst.execute();
					out.println("REGISTERED");
				}
			} catch (Exception e) {
				addToLogs(e.getMessage());
			} finally {
				if (name.getU() != null) {
					addToLogs(name.getU() + " is leaving");
					connectedClients.remove(name.getU());
					//broadcastMessage(name.getU() + " has left");
				}
			}
		}
	}

}

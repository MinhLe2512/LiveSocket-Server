package frame;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

import window.ClientWindow;
import model.JPanelWithBackground;
import model.Match;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JScrollPane;

import java.awt.Color;
import java.awt.Font;
import java.awt.Toolkit;

import javax.swing.JTextField;
import javax.swing.JButton;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;

public class LivescoreFrame extends JFrame{
	private JPanel contentPane;
	private JTable livescore_table;
	private JLabel livescore_titleLabel;
	private JScrollPane livescore_scrollPane;
	private JTextField findText;
	private JButton findButton, logoutButton;
	private DefaultTableModel model;
	private List<Match> match = new ArrayList<Match>();
	/**
	 * Create the application.
	 */
	public LivescoreFrame(ClientWindow defaultWindow) {
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
		contentPane.setLayout(null);
		contentPane.setForeground(new Color(255, 255, 255));
		setBounds(0, 0, 640, 500);
		setLocationRelativeTo(null);
		contentPane.setSize(620, 500);
		contentPane.setBackground(Color.WHITE);
		setContentPane(contentPane);
		
		livescore_titleLabel = new JLabel("LIVESCORE");
		livescore_titleLabel.setForeground(Color.WHITE);
		livescore_titleLabel.setFont(new Font("Dialog", Font.BOLD, 30));
		livescore_titleLabel.setBounds(210, 6, 205, 24);
		contentPane.add(livescore_titleLabel);
		
		livescore_scrollPane = new JScrollPane();
		livescore_scrollPane.setBounds(10, 96, 600, 370);
		contentPane.add(livescore_scrollPane);
		
		livescore_table = new JTable();
		livescore_table.addMouseListener(new MouseAdapter() {
	         public void mouseClicked(MouseEvent me) {
	        	 if (me.getClickCount() == 2)
	            	defaultWindow.Detail();
	         }
	      });
		livescore_scrollPane.setViewportView(livescore_table);
		
		findText = new JTextField();
		findText.setBounds(20, 46, 175, 35);
		contentPane.add(findText);
		findText.setColumns(10);
		
		findButton = new JButton("Find match");
		findButton.setBounds(222, 50, 117, 29);
		findButton.setFont(new Font("Lucida Grande", Font.BOLD, 15));
		findButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				defaultWindow.search();
			}
		});
		contentPane.add(findButton);
		
		logoutButton = new JButton("Log out");
		logoutButton.setFont(new Font("Tahoma", Font.PLAIN, 13));
		logoutButton.setBounds(509, 6, 101, 24);
		logoutButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				defaultWindow.logout();
			}
		});
		contentPane.add(logoutButton);
	}
	
	public void showMatch(List<Match> matchList) {
		match = matchList;
		
		model = new DefaultTableModel(){
		    //not editable
			@Override
		    public boolean isCellEditable(int row, int column) {
		       return false;
		    }
		};
		
		model.addColumn("Time");
		model.addColumn("Home");
		model.addColumn("Score");
		model.addColumn("Away");
		
		//time format
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
		
		for (int i = 0; i < matchList.size(); i++) {
			//time
			String time;
			if (matchList.get(i).getScoreHome() > 0 ||
				matchList.get(i).getScoreAway() > 0)
					time = "FT";
			else time = matchList.get(i).getTime().format(formatter);
			
			//score
			String score;
			if (matchList.get(i).getScoreHome() == -1)
				score = "? - ?";
			else score = (matchList.get(i).getScoreHome() + " - " + matchList.get(i).getScoreAway());
			
			model.addRow(new Object[] {
				time,
				matchList.get(i).getHome() + " ",
				score,
				" " + matchList.get(i).getAway(),
			});
		}
		
		livescore_table.getTableHeader().setFont(new Font("Lucida Grande", Font.PLAIN, 15));
		livescore_table.setFont(new Font("Lucida Grande", Font.PLAIN, 15));
		livescore_table.setRowHeight(30);
		livescore_table.setShowVerticalLines(false);
		
		//set width
		livescore_table.setModel(model);
		livescore_table.setAutoResizeMode(0);
		livescore_table.getColumnModel().getColumn(0).setPreferredWidth(170);
		livescore_table.getColumnModel().getColumn(1).setPreferredWidth(180);
		livescore_table.getColumnModel().getColumn(2).setPreferredWidth(60);
		livescore_table.getColumnModel().getColumn(3).setPreferredWidth(180);
		//fixed width
		livescore_table.getTableHeader().setResizingAllowed(false);
		//disabled to move cols
		livescore_table.getTableHeader().setReorderingAllowed(false);
		
		//align home col
		DefaultTableCellRenderer rightRenderer = new DefaultTableCellRenderer();
		rightRenderer.setHorizontalAlignment(JLabel.RIGHT);
		livescore_table.getColumnModel().getColumn(1).setCellRenderer(rightRenderer);
		//align score, time col
		DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
		centerRenderer.setHorizontalAlignment(JLabel.CENTER);
		livescore_table.getColumnModel().getColumn(0).setCellRenderer(centerRenderer);
		livescore_table.getColumnModel().getColumn(2).setCellRenderer(centerRenderer);
		//align header
		((DefaultTableCellRenderer)livescore_table.getTableHeader().getDefaultRenderer()).setHorizontalAlignment(JLabel.CENTER);
	}
	public String getFindObj() {
		return findText.getText();
	}
	public String getSelectedID() {
		Match selectedMatch = match.get(livescore_table.convertRowIndexToModel(livescore_table.getSelectedRow()));
		return selectedMatch.getID();
	}
}
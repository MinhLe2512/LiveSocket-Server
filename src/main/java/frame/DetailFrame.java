package frame;


import javax.swing.JFrame;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

import window.ClientWindow;
import model.Event;
import model.JPanelWithBackground;

import javax.swing.JLabel;
import javax.swing.JPanel;

import java.awt.Color;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.List;

import javax.swing.JScrollPane;
import javax.swing.JButton;

public class DetailFrame extends JFrame{
	private JPanel contentPane;
	private JTable detail_table;
	private JScrollPane detail_scrollPane;
	private JLabel detail_titleLabel;
	private JButton backButton;

	/**
	 * Create the application.
	 */
	public DetailFrame(ClientWindow defaultWindow) {
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
		contentPane.setForeground(new Color(255, 255, 255));
		setBounds(0, 0, 520, 400);
		setLocationRelativeTo(null);
		contentPane.setSize(520, 400);
		contentPane.setBackground(Color.WHITE);
		setContentPane(contentPane);
		
		detail_scrollPane = new JScrollPane();
		detail_scrollPane.setBounds(10, 50, 500, 316);
		contentPane.add(detail_scrollPane);
		
		detail_table = new JTable();
		detail_scrollPane.setViewportView(detail_table);
		
		backButton = new JButton("Back");
		backButton.setBounds(455, 6, 58, 29);
		backButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				defaultWindow.Back();
			}
		});
		contentPane.add(backButton);
		
		detail_titleLabel = new JLabel("DETAIL");
		detail_titleLabel.setForeground(Color.WHITE);
		detail_titleLabel.setFont(new Font("Lucida Grande", Font.BOLD, 20));
		detail_titleLabel.setBounds(222, 20, 76, 16);
		contentPane.add(detail_titleLabel);
	}

	public void showDetail(List<Event> event) {
		DefaultTableModel model = new DefaultTableModel(){
		    //not editable
			@Override
		    public boolean isCellEditable(int row, int column) {
		       return false;
		    }
		};
		
		model.addColumn("Time");
		model.addColumn("Event");
		model.addColumn("Player");
		
		for (int i = 0; i < event.size(); i++) {
			String time = event.get(i).getMomment();
			int timing = Integer.parseInt(event.get(i).getMomment());
			if (timing > 90)
				time = timing + "+" + timing%10;
			time += "'";
			
			model.addRow(new Object[] {
				time,
				event.get(i).getEvent(),
				event.get(i).getName(),
			});
		}
		
		detail_table.getTableHeader().setFont(new Font("Lucida Grande", Font.PLAIN, 15));
		detail_table.setFont(new Font("Lucida Grande", Font.PLAIN, 15));
		detail_table.setRowHeight(30);
		detail_table.setShowVerticalLines(false);
		
		//set width
		detail_table.setModel(model);
		detail_table.setAutoResizeMode(0);
		detail_table.getColumnModel().getColumn(0).setPreferredWidth(95);
		detail_table.getColumnModel().getColumn(1).setPreferredWidth(170);
		detail_table.getColumnModel().getColumn(2).setPreferredWidth(230);
		//fixed width
		detail_table.getTableHeader().setResizingAllowed(false);
		//disabled to move cols
		detail_table.getTableHeader().setReorderingAllowed(false);
		
		//align cols
		DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
		centerRenderer.setHorizontalAlignment(JLabel.CENTER);
		detail_table.getColumnModel().getColumn(0).setCellRenderer(centerRenderer);
		detail_table.getColumnModel().getColumn(1).setCellRenderer(centerRenderer);
		detail_table.getColumnModel().getColumn(2).setCellRenderer(centerRenderer);
		//header align
		((DefaultTableCellRenderer)detail_table.getTableHeader().getDefaultRenderer()).setHorizontalAlignment(JLabel.CENTER);
	}
}

package VIEW;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.Toolkit;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JButton;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.WindowEvent;
import javax.swing.JScrollPane;
import java.awt.GridLayout;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;

import CONTROLLER.Controller;

import java.awt.FlowLayout;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class FreigabeDialog extends JFrame {

	private JPanel contentPane;
	private ArrayList<String> userlist=new ArrayList<String>();
	private ArrayList<JCheckBox> toShare=new ArrayList<JCheckBox>();
	private ArrayList<String> userToShare=new ArrayList<String>();
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					FreigabeDialog frame = new FreigabeDialog();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	public void close(){
		WindowEvent winClosingEvent = new WindowEvent(this,WindowEvent.WINDOW_CLOSING);
		Toolkit.getDefaultToolkit().getSystemEventQueue().postEvent(winClosingEvent);		
	}
	
	public void updateUserlist(){
		userlist = new ArrayList<String>();
		ResultSet result = Controller.shsdb.select(Controller.shsconfig.usertb, "username");
		try {
			while(result.next()){
				userlist.add(result.getString("username"));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
		}
	}

	/**
	 * Create the frame.
	 */
	public FreigabeDialog() {
		setTitle("Datei Freigabe");
		updateUserlist();
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		
		JButton btnOk = new JButton("OK");
		btnOk.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				//get all checked checkboxes
				for(int j=0;j<toShare.size();j++){
					JCheckBox box = (JCheckBox) toShare.get(j);
					if(box.isSelected()){
						userToShare.add(box.getText());
					}
				}
				System.out.println(GUI.getFileId());
				Controller.shsconfig.createticket(GUI.getFileId(), (String[])userToShare.toArray());
				close();
			}
		});
		contentPane.add(btnOk, BorderLayout.SOUTH);
		
		JScrollPane scrollPane = new JScrollPane();
		contentPane.add(scrollPane, BorderLayout.CENTER);
		
		JPanel panel = new JPanel();
		scrollPane.setViewportView(panel);
		panel.setLayout(new GridLayout(0, 1, 0, 0));
		
				
		for(int i=0;i<userlist.size();i++){
			JCheckBox cb = new JCheckBox(userlist.get(i));
			panel.add(cb);
			panel.revalidate();
			panel.repaint();
			toShare.add(cb);
		}
		
		JCheckBox chckbxNewCheckBox = new JCheckBox("New check box");
		chckbxNewCheckBox.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			    
			    }
			
		});
		panel.add(chckbxNewCheckBox);
		
	}

}
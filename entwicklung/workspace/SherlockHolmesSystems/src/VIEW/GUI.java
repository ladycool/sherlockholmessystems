package VIEW;

import java.awt.Color;
import java.awt.Window;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import CONTROLLER.Controller;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JTable;
import javax.swing.JInternalFrame;
import javax.swing.JTextPane;
import javax.swing.JTextArea;

public class GUI extends JFrame {
	
	private JPanel contentPane;
	private JTextField passwort;
	private JTextField pfad;
	private JTextField benutzername;
	private File file;
	private JTable internalTable;
	private HashMap<String, String> externalViewData = new HashMap<String, String>();
	private HashMap<String, String> internalViewData = new HashMap<String, String>();
	private String[][] internalRowData;
	private String[][] externalRowData;
	private DefaultTableModel internalTableModel;
	private DefaultTableModel externalTableModel;
	private ArrayList<String> internalKeys = new ArrayList<String>();
	private ArrayList<String> externalKeys = new ArrayList<String>();
	private JTable externalTable;
	private JDialog verwalteZugriff;
	
	private void updateView(){
		Controller.shsconfig.loadexternalview();
		Controller.shsconfig.loadinternalview();
		internalViewData = Controller.shsconfig.getinternalviewdata();
		externalViewData = Controller.shsconfig.getexternalviewdata();
		
		int i = 0;
		String key, val;
		Iterator it = internalViewData.entrySet().iterator();
	    while (it.hasNext()) {
	        Map.Entry pairs = (Map.Entry)it.next();
	        key = pairs.getKey().toString();
	        val = pairs.getValue().toString();
	        System.out.println(key);
	        System.out.println(val);
	        internalKeys.add(key);
	        String[] row = {val};
	        internalTableModel.insertRow(i, row);
	        i++;
	        it.remove(); // avoids a ConcurrentModificationException
	    }
	    internalTableModel.fireTableDataChanged();
	    
	    int j=0;
	    Iterator it1 = externalViewData.entrySet().iterator();
	    while (it1.hasNext()){
	    	Map.Entry pairs1 = (Map.Entry)it1.next();
	    	key = pairs1.getKey().toString();
	    	val = pairs1.getValue().toString();
	    	externalKeys.add(key);
	    	String[] rowEx = {val};
	    	externalTableModel.insertRow(j,rowEx);
	    	j++;
	    	it1.remove();
	    }
	    //toreturn.put(ticketId,sent_by+this.sep+this.sep+filename);	
	}
	
	/**
	 * Create the frame.
	 * @return 
	 */
	public GUI() {
	
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 604, 433);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		
		JPanel loginPanel = new JPanel();
		
		JPanel menuPanel = new JPanel();
		
		JScrollPane internalFilesscrollPane = new JScrollPane();
		
		JLabel lblEigeneDateien = new JLabel("Eigene Dateien");
		
		JScrollPane externalFilesscrollPane = new JScrollPane();
		
		JLabel lblFremdeDateien = new JLabel("Fremde Dateien");

		
		JLabel lblKonsole = new JLabel("Konsole");
		
		JScrollPane scrollPane = new JScrollPane();

		
		
		GroupLayout gl_contentPane = new GroupLayout(contentPane);
		gl_contentPane.setHorizontalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addComponent(loginPanel, GroupLayout.DEFAULT_SIZE, 586, Short.MAX_VALUE)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addComponent(menuPanel, GroupLayout.DEFAULT_SIZE, 576, Short.MAX_VALUE)
					.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
				.addGroup(gl_contentPane.createSequentialGroup()
					.addComponent(internalFilesscrollPane, GroupLayout.DEFAULT_SIZE, 576, Short.MAX_VALUE)
					.addContainerGap())
				.addGroup(gl_contentPane.createSequentialGroup()
					.addComponent(lblFremdeDateien)
					.addContainerGap())
				.addGroup(gl_contentPane.createSequentialGroup()
					.addComponent(lblEigeneDateien)
					.addContainerGap())
				.addGroup(gl_contentPane.createSequentialGroup()
					.addComponent(lblKonsole)
					.addContainerGap())
				.addGroup(Alignment.TRAILING, gl_contentPane.createSequentialGroup()
					.addGroup(gl_contentPane.createParallelGroup(Alignment.TRAILING)
						.addComponent(scrollPane, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 576, Short.MAX_VALUE)
						.addComponent(externalFilesscrollPane, GroupLayout.DEFAULT_SIZE, 576, Short.MAX_VALUE))
					.addContainerGap())
		);
		gl_contentPane.setVerticalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addComponent(loginPanel, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(menuPanel, GroupLayout.PREFERRED_SIZE, 57, GroupLayout.PREFERRED_SIZE)
					.addGap(4)
					.addComponent(lblEigeneDateien)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(internalFilesscrollPane, GroupLayout.PREFERRED_SIZE, 76, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(lblFremdeDateien)
					.addGap(11)
					.addComponent(externalFilesscrollPane, GroupLayout.PREFERRED_SIZE, 72, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(lblKonsole)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, 64, Short.MAX_VALUE))
		);
		
		JTextArea textArea = new JTextArea();
		MessageConsole mc = new MessageConsole(textArea);
		mc.redirectOut();
		mc.redirectErr(Color.RED, null);
		internalFilesscrollPane.setViewportView(textArea);
		scrollPane.setViewportView(textArea);
		
		
		
		externalTable = new JTable();
		externalFilesscrollPane.setRowHeaderView(externalTable);
		
		String[] columnNames = {"File"};
		internalTableModel = new DefaultTableModel(internalRowData, columnNames);
		internalTable = new JTable();
		internalTable.setModel(internalTableModel);
		//add(new JScrollPane(internalTable));
		internalFilesscrollPane.setViewportView(internalTable);

			
		verwalteZugriff = new JDialog();
		verwalteZugriff.setVisible(false);
		
		JButton btnErteilen = new JButton("Freigabe");
		btnErteilen.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				verwalteZugriff.setVisible(true);
			}
		});
		
		pfad = new JTextField();
		pfad.setColumns(10);
		
		/**
		 * @brief FILE UPLOAD
		 *
		 */
		JButton btnAuswhlen = new JButton("Hinzuf\u00FCgen");
		btnAuswhlen.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				Controller.shsconfig.uploadfile(file);
				updateView();
				internalTableModel.fireTableDataChanged();
			}
		});
		
		/**
		 * @brief Delete File
		 *
		 */
		JButton btnLsche = new JButton("L\u00F6schen");
		btnLsche.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				int selectedRow = internalTable.getSelectedRow();
				if (selectedRow!=-1){
					ArrayList<String> fileIds = new ArrayList<String>();
					fileIds.add(internalKeys.get(selectedRow));
					HashMap<String,ArrayList<String>> metadata = new HashMap<String,ArrayList<String>>();
					metadata.put(Controller.shsconfig.fileId, fileIds);
					Controller.shsconfig.delete(Controller.shsconfig.owner, Controller.shsconfig.filetype, metadata);
					updateView();
				} else {
					System.out.println("Bitte eine Datei auswählen!");
				}
			}
		});
		
		JButton btnEntnehmen = new JButton("Viewer Sicht");
		
		/**
		 * @brief create JFILEChooser
		 *
		 */
		JButton btnNewButton = new JButton("Ausw\u00E4hlen");
		btnNewButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				try{
					JFileChooser fileOeffnen = new JFileChooser();
					fileOeffnen.setDialogTitle("Datei Öffnen");
					fileOeffnen.showOpenDialog(null);
					file = fileOeffnen.getSelectedFile();
					String fileName = file.getAbsolutePath();
					pfad.setText(fileName);
				}catch (Exception e) {
					e.getStackTrace();
				}
				
			}
		});
		GroupLayout gl_menuPanel = new GroupLayout(menuPanel);
		gl_menuPanel.setHorizontalGroup(
			gl_menuPanel.createParallelGroup(Alignment.TRAILING)
				.addGroup(gl_menuPanel.createSequentialGroup()
					.addGroup(gl_menuPanel.createParallelGroup(Alignment.LEADING)
						.addComponent(btnNewButton)
						.addComponent(btnAuswhlen))
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addGroup(gl_menuPanel.createParallelGroup(Alignment.TRAILING)
						.addGroup(gl_menuPanel.createSequentialGroup()
							.addComponent(btnLsche)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(btnErteilen)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(btnEntnehmen))
						.addComponent(pfad, GroupLayout.PREFERRED_SIZE, 247, GroupLayout.PREFERRED_SIZE))
					.addContainerGap(148, Short.MAX_VALUE))
		);
		gl_menuPanel.setVerticalGroup(
			gl_menuPanel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_menuPanel.createSequentialGroup()
					.addGroup(gl_menuPanel.createParallelGroup(Alignment.BASELINE)
						.addComponent(pfad, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(btnNewButton))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_menuPanel.createParallelGroup(Alignment.BASELINE)
						.addComponent(btnAuswhlen)
						.addComponent(btnLsche)
						.addComponent(btnErteilen)
						.addComponent(btnEntnehmen))
					.addContainerGap(138, Short.MAX_VALUE))
		);
		menuPanel.setLayout(gl_menuPanel);
		
		JLabel lblBenutzername = new JLabel("Benutzername");
		
		benutzername = new JTextField();
		benutzername.setColumns(10);
		
		JLabel lblPasswort = new JLabel("Passwort");
		
		passwort = new JTextField();
		passwort.setColumns(10);
		
		/**
		 * @brief LOGIN USER
		 *
		 */
		JButton btnLogin = new JButton("Login");
		btnLogin.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				HashMap<String, String> attributes = new HashMap<String, String>();
				attributes.put(Controller.shsconfig.username, benutzername.getText());
				attributes.put(Controller.shsconfig.password, passwort.getText());
				Controller.shsuser = Controller.shsconfig.loginSHS(Controller.shsconfig.signactionA, attributes);
				//System.out.println(Controller.shsuser.getattr(Controller.shsconfig.username));
				updateView();
				
			}
		});
		

		/**
		 * @brief LOGOUT USER
		 *
		 */
		JButton btnLoout = new JButton("Logout");
		btnLoout.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				//Controller.shsconfig.logoutSHS(); sonst funktionier danach kein erneutes einloggen
				internalTableModel.setColumnCount(0);
			}
		});
		
		/**
		 * @brief REGISTER USER
		 *
		 */
		JButton btnRegister = new JButton("Register");
		btnRegister.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				HashMap<String, String> attributes = new HashMap<String, String>();
				attributes.put(Controller.shsconfig.username, benutzername.getText());
				attributes.put(Controller.shsconfig.password, passwort.getText());
				Controller.shsuser = Controller.shsconfig.loginSHS(Controller.shsconfig.signactionB, attributes);
				//System.out.println(Controller.shsuser.getattr(Controller.shsconfig.username));
				updateView();
			}
		});
		GroupLayout gl_loginPanel = new GroupLayout(loginPanel);
		gl_loginPanel.setHorizontalGroup(
			gl_loginPanel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_loginPanel.createSequentialGroup()
					.addContainerGap()
					.addComponent(lblBenutzername)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(benutzername, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
					.addGap(36)
					.addComponent(lblPasswort)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(passwort, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
					.addGap(18)
					.addComponent(btnLogin)
					.addGap(14)
					.addComponent(btnLoout)
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addComponent(btnRegister)
					.addContainerGap(19, Short.MAX_VALUE))
		);
		gl_loginPanel.setVerticalGroup(
			gl_loginPanel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_loginPanel.createSequentialGroup()
					.addGap(6)
					.addGroup(gl_loginPanel.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblBenutzername)
						.addComponent(benutzername, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)))
				.addGroup(gl_loginPanel.createSequentialGroup()
					.addGap(6)
					.addGroup(gl_loginPanel.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblPasswort)
						.addComponent(btnLogin)
						.addComponent(btnRegister)
						.addComponent(btnLoout)
						.addComponent(passwort, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)))
		);
		loginPanel.setLayout(gl_loginPanel);
		contentPane.setLayout(gl_contentPane);
	}
}

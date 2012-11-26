package VIEW;

import java.awt.Color;
import java.awt.Desktop;
import java.awt.Window;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.URI;
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
import javax.swing.JPasswordField;
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
	private JPanel menuPanel;
	private JTextField passwort;
	private JTextField pfad;
	private JTextField benutzername;
	private File file;
	private static JTable internalTable;
	private static HashMap<String, String> externalViewData = new HashMap<String, String>();
	private static HashMap<String, String> internalViewData = new HashMap<String, String>();
	private static HashMap<String, String> exFile = new HashMap<String, String>();
	private String[][] internalRowData;
	private String[][] externalRowData;
	private static DefaultTableModel internalTableModel;
	private static DefaultTableModel externalTableModel;
	private static ArrayList<String> internalKeys = new ArrayList<String>();
	private static ArrayList<String> internalVal = new ArrayList<String>();
	private static ArrayList<String> externalKeys = new ArrayList<String>();
	private static JTable externalTable;
	private static String fileId;
	
	public static String getFileId(){
		return fileId;
	}
	
	public static HashMap<String, String> getExternalFile(){
		return exFile;
	}
	
	public static void updateView(){
		Controller.shsconfig.loadexternalview();
		Controller.shsconfig.loadinternalview();
		internalViewData = Controller.shsconfig.getinternalviewdata();
		externalViewData = Controller.shsconfig.getexternalviewdata();
		System.out.println(externalViewData.size());
		for(int n=internalTable.getRowCount()-1;n>=0;n--){ 
			internalTableModel.removeRow(n); 
		}
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
	        internalVal.add(val);
	        String[] row = {val};
	        internalTableModel.insertRow(i, row);
	        i++;
	        it.remove(); // avoids a ConcurrentModificationException
	    }
	    internalTableModel.fireTableDataChanged();
	    
	    for(int l=externalTable.getRowCount()-1;l>=0;l--){ 
			externalTableModel.removeRow(l); 
		}
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
	    externalTableModel.fireTableDataChanged();
	    //toreturn.put(ticketId,sent_by+this.sep+this.sep+filename);	
	}
	
	/**
	 * Create the frame.
	 * @return 
	 */
	public GUI() {
		setTitle("Holmes Systems");
	
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 604, 433);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		
		JPanel loginPanel = new JPanel();
		
		menuPanel = new JPanel();
		menuPanel.setVisible(false);
		
		JScrollPane internalFilesscrollPane = new JScrollPane();
		
		JScrollPane externalFilesscrollPane = new JScrollPane();

		
		JLabel lblKonsole = new JLabel("Konsole");
		
		JScrollPane scrollPane = new JScrollPane();

		
		
		GroupLayout gl_contentPane = new GroupLayout(contentPane);
		gl_contentPane.setHorizontalGroup(
			gl_contentPane.createParallelGroup(Alignment.TRAILING)
				.addComponent(loginPanel, GroupLayout.DEFAULT_SIZE, 586, Short.MAX_VALUE)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addComponent(menuPanel, GroupLayout.DEFAULT_SIZE, 576, Short.MAX_VALUE)
					.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
				.addGroup(Alignment.LEADING, gl_contentPane.createSequentialGroup()
					.addComponent(lblKonsole)
					.addContainerGap())
				.addGroup(Alignment.LEADING, gl_contentPane.createSequentialGroup()
					.addComponent(internalFilesscrollPane, GroupLayout.DEFAULT_SIZE, 576, Short.MAX_VALUE)
					.addContainerGap())
				.addGroup(gl_contentPane.createSequentialGroup()
					.addGroup(gl_contentPane.createParallelGroup(Alignment.TRAILING)
						.addComponent(externalFilesscrollPane, GroupLayout.DEFAULT_SIZE, 576, Short.MAX_VALUE)
						.addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, 576, Short.MAX_VALUE))
					.addContainerGap())
		);
		gl_contentPane.setVerticalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addComponent(loginPanel, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(menuPanel, GroupLayout.PREFERRED_SIZE, 57, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(internalFilesscrollPane, GroupLayout.PREFERRED_SIZE, 94, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(externalFilesscrollPane, GroupLayout.PREFERRED_SIZE, 97, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(lblKonsole)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, 64, Short.MAX_VALUE))
		);
		
		JTextArea textArea = new JTextArea();
		MessageConsole mc = new MessageConsole(textArea);
		mc.redirectOut();
		mc.redirectErr(Color.RED, null);
		scrollPane.setViewportView(textArea);
		
		
		
		String[] excolumnNames = {"Fremde Dateien"};
		externalTableModel = new DefaultTableModel(externalRowData, excolumnNames);
		externalTable = new JTable();
		externalTable.setModel(externalTableModel);
		//add(new JScrollPane(internalTable));
		externalFilesscrollPane.setViewportView(externalTable);
		
		String[] columnNames = {"Eigene Dateien"};
		internalTableModel = new DefaultTableModel(internalRowData, columnNames);
		internalTable = new JTable();
		internalTable.setModel(internalTableModel);
		//add(new JScrollPane(internalTable));
		internalFilesscrollPane.setViewportView(internalTable);

		
		JButton btnErteilen = new JButton("Freigabe");
		btnErteilen.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				int selectedRow = internalTable.getSelectedRow();
				if (selectedRow!=-1){
					fileId = internalKeys.get(selectedRow);
					FreigabeDialog fd = new FreigabeDialog();
					fd.setVisible(true);
				} else {
					System.out.println("Bitte eine Datei auswählen, für die Sie die Freigabe erteilen wollen!");
				}
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
				int externalSelectedRow = externalTable.getSelectedRow();
				if (selectedRow!=-1){
					ArrayList<String> fileIds = new ArrayList<String>();
					fileIds.add(internalKeys.get(selectedRow));
					HashMap<String,ArrayList<String>> metadata = new HashMap<String,ArrayList<String>>();
					metadata.put(Controller.shsconfig.fileId, fileIds);
					Controller.shsconfig.delete(Controller.shsconfig.owner, Controller.shsconfig.filetype, metadata);
					updateView();
				} else if (externalSelectedRow!=-1){
					
				} else {
					System.out.println("Bitte eine Datei auswählen!");
				}
			}
		});
		
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
		
		JButton btnDateiAnzeigen = new JButton("Datei \u00F6ffnen");
		btnDateiAnzeigen.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				String pathId;
				Desktop desktop = Desktop.getDesktop();
				int selectedRow = internalTable.getSelectedRow();
<<<<<<< HEAD
				int exSelectedRow = externalTable.getSelectedRow();
				if (selectedRow!=-1){
					 pathId = internalVal.get(selectedRow);
					 pathId = pathId.replace("\\", "/");
					//Adresse mit Standardbrowser anzeigen
						URI uri;
						try {
						  uri = new URI("file:///" +pathId);
						  //uri = new URI("file:///"+pathId);
						  desktop.browse(uri);
						  
						}
						catch(Exception oError) {
						  //Hier Fehler abfangen
						}
				} else if (exSelectedRow !=-1){
					exFile = Controller.shsconfig.previewfile(externalKeys.get(exSelectedRow), Controller.shsconfig.reader);
					FileViewer fv = new FileViewer();
					fv.setVisible(true);
					System.out.println(exFile.get("content"));

				} else {
					System.out.println("Bitte eine Datei auswählen!");
				}
			
			 
=======
			 String pathId = internalVal.get(selectedRow);
			 System.out.println(pathId);
			 FileViewer fv = new FileViewer();
			 fv.setVisible(true);
>>>>>>> 2c86c7d49fd7a23d3b616c2e183bc428def20064
			 	
			}
		});
		GroupLayout gl_menuPanel = new GroupLayout(menuPanel);
		gl_menuPanel.setHorizontalGroup(
			gl_menuPanel.createParallelGroup(Alignment.TRAILING)
				.addGroup(Alignment.LEADING, gl_menuPanel.createSequentialGroup()
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
							.addComponent(btnDateiAnzeigen)
							.addGap(4))
						.addComponent(pfad, GroupLayout.PREFERRED_SIZE, 247, GroupLayout.PREFERRED_SIZE))
					.addContainerGap(230, Short.MAX_VALUE))
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
						.addComponent(btnDateiAnzeigen))
					.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
		);
		menuPanel.setLayout(gl_menuPanel);
		
		JLabel lblBenutzername = new JLabel("Benutzername");
		
		benutzername = new JTextField();
		benutzername.setColumns(10);
		
		JLabel lblPasswort = new JLabel("Passwort");
		
		passwort = new JPasswordField();
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
				System.out.println("Erfolgreich eingeloggt!");
				menuPanel.setVisible(true);
				
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
				//internalTableModel.setColumnCount(0);
				//externalTableModel.setColumnCount(0);
				System.out.println("Erfolgreich ausgeloggt!");
				menuPanel.setVisible(false);
				Main.frame.dispose();
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

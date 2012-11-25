package VIEW;

import java.awt.Window;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.util.HashMap;

import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.border.EmptyBorder;

import CONTROLLER.Controller;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JTable;

public class GUI extends JFrame {
	
	private JPanel contentPane;
	private JTextField passwort;
	private JTextField pfad;
	private JTextField benutzername;
	private File file;
	private JTable table;
	
	/**
	 * Create the frame.
	 * @return 
	 */
	public GUI() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 604, 396);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		
		JPanel loginPanel = new JPanel();
		
		JPanel menuPanel = new JPanel();
		
		JPanel externalFiles = new JPanel();
		
		JPanel konsole = new JPanel();
		
		JPanel internalFiles = new JPanel();
		GroupLayout gl_contentPane = new GroupLayout(contentPane);
		gl_contentPane.setHorizontalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addComponent(loginPanel, GroupLayout.DEFAULT_SIZE, 586, Short.MAX_VALUE)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addComponent(menuPanel, GroupLayout.DEFAULT_SIZE, 576, Short.MAX_VALUE)
					.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
				.addGroup(Alignment.TRAILING, gl_contentPane.createSequentialGroup()
					.addGroup(gl_contentPane.createParallelGroup(Alignment.TRAILING)
						.addComponent(konsole, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 576, Short.MAX_VALUE)
						.addComponent(externalFiles, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 576, Short.MAX_VALUE)
						.addComponent(internalFiles, GroupLayout.DEFAULT_SIZE, 576, Short.MAX_VALUE))
					.addContainerGap())
		);
		gl_contentPane.setVerticalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addComponent(loginPanel, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(menuPanel, GroupLayout.PREFERRED_SIZE, 57, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(internalFiles, GroupLayout.PREFERRED_SIZE, 108, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(externalFiles, GroupLayout.DEFAULT_SIZE, 104, Short.MAX_VALUE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(konsole, GroupLayout.PREFERRED_SIZE, 26, GroupLayout.PREFERRED_SIZE))
		);
		
		table = new JTable();
		internalFiles.add(table);
		
		JButton btnErteilen = new JButton("Freigabe");
		
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
				//Controller.shsconfig.delete(status, datatype, data)
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
				System.out.println(Controller.shsuser.getattr(Controller.shsconfig.username));
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
				Controller.shsconfig.logoutSHS();
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
				System.out.println(Controller.shsuser.getattr(Controller.shsconfig.username));
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

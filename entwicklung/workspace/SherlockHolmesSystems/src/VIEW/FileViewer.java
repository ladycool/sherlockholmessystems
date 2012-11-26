package VIEW;

import java.awt.BorderLayout;
<<<<<<< HEAD
import java.awt.Container;
import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.util.HashMap;
=======
import java.awt.EventQueue;
>>>>>>> 2c86c7d49fd7a23d3b616c2e183bc428def20064

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
<<<<<<< HEAD
import javax.swing.JTextPane;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JTextField;
import javax.swing.JTextArea;
import javax.swing.JScrollBar;
import javax.swing.LayoutStyle.ComponentPlacement;
import java.awt.ScrollPane;
import javax.swing.JScrollPane;
import java.awt.TextArea;
import java.awt.Component;
=======
>>>>>>> 2c86c7d49fd7a23d3b616c2e183bc428def20064

public class FileViewer extends JFrame {

	private JPanel contentPane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					FileViewer frame = new FileViewer();
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
	public FileViewer() {
<<<<<<< HEAD
		
		setBounds(100, 100, 900, 500);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		
		JTextArea textArea = new JTextArea();
		textArea.append(GUI.getExternalFile().get("content"));
		JScrollPane scroll = new JScrollPane(textArea);
		scroll.setAutoscrolls(true);
		Container c = getContentPane();
		c.add(BorderLayout.EAST, scroll);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		contentPane.setLayout(new BorderLayout(0, 0));
		contentPane.add(scroll);
		textArea.setCaretPosition(0);
		
		
	}
=======
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
	}

>>>>>>> 2c86c7d49fd7a23d3b616c2e183bc428def20064
}

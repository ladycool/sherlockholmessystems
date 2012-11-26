package VIEW;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.UIManager;

public class Main extends JFrame {
public static GUI frame;
	public Main() {
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
	}
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Throwable e) {
			e.printStackTrace();
		}
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					frame = new GUI();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	}

package ui;

import java.awt.Dimension;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;

import database.DBManager;
import gdb.GlobalDefines;

public class BaseWindow extends JFrame {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public BaseWindow() {
//		setVisible(true);
		setSize(new Dimension(800, 600));
		setIconImage(GlobalDefines.getWindowIcon());
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
//		GraphicsEnvironment env = GraphicsEnvironment.getLocalGraphicsEnvironment();
//		Rectangle bound = env.getMaximumWindowBounds();
//		System.out.println(bound);
//		setBounds(bound);
		
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosed(WindowEvent e) {
				DBManager.getManager().disconnectAll();
				System.out.println("Bye");
			}
		});
	}
	

}

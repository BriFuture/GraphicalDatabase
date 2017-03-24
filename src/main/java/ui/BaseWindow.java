package ui;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.Toolkit;
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
	
	public static void setComponentHorizontalCenter(Component c, JFrame owner) {
		if( owner == null ) {
			Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
			c.setLocation(0, (int) ((d.getHeight() - c.getHeight()) / 2));
		} else {
			c.setLocation(0, (owner.getHeight() - c.getHeight()) / 2);
		}
	}
	
	public static void setComponentVerticalCenter(Component c, JFrame owner) {
		if( owner == null ) {
			Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
			c.setLocation((int) ((d.getWidth() - c.getWidth()) / 2), 0);
		} else {
			c.setLocation( (owner.getWidth() - c.getWidth()) / 2, 0);
		}
	}
	
	public static void setComponentCenter(Component c, JFrame owner) {
		if( owner == null ) {
			Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
			c.setLocation((int) ((d.getWidth() - c.getWidth()) / 2), (int) ((d.getHeight() - c.getHeight()) / 2));
		} else {
			c.setLocation( (owner.getWidth() - c.getWidth()) / 2, (owner.getHeight() - c.getHeight()) / 2);
		}
	}

		
}

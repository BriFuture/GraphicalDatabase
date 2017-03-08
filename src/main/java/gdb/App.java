package gdb;

import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import ui.MainWindow;

public class App 
{
    public static void main( String[] args )
    {

    	// 使用系统外观
    	try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UnsupportedLookAndFeelException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
    	new MainWindow();
    }
}

package gdb;

import java.awt.Image;
import java.net.URL;

import javax.swing.ImageIcon;

public class GlobalDefines {
	/**
	 * 统一设置所有窗口图标的路径
	 */
	public static final String ICON_PATH = "/assets/img/";
	public static final String WINDOW_ICON 				= ICON_PATH + "icon.png";
	public static final String ICON_CONNECT_DATABASE 	= ICON_PATH + "connect_database.png";
	public static final String ICON_DATABASES 			= ICON_PATH + "databases.png";
	public static final String ICON_DISCONNECT_DATABASE = ICON_PATH + "disconnect_database.png";
	public static final String ICON_NEW_DATABASE 		= ICON_PATH + "new_database.png";
	
	
	private static GlobalDefines gd;
	
	/**
	 * 
	 * @return  窗口图标
	 */
	public static final Image getWindowIcon() {
		URL url = getUrl(WINDOW_ICON);
		return new ImageIcon(url).getImage();
	}
	
	
	/**
	 * 
	 * @param s
	 * @return  返回 URL
	 */
	public static final URL getUrl(String s) {
		URL url = GlobalDefines.class.getResource(s);
//		System.out.println(url);
		return url;
	}
	
	private GlobalDefines() {
		
	}
	
	public GlobalDefines getDefine() {
		if(gd == null) {
			gd = new GlobalDefines();
		}
		
		return gd;
	}
}

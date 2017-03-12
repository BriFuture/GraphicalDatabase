package ui;

import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;
import java.util.HashSet;

import javax.swing.ImageIcon;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import database.DBManager;
import gdb.GlobalDefines;

/**
 * MWMenuBar.java  
 * 负责渲染主页面的菜单栏
 * @author future
 *
 */
public class MWMenuBar implements ActionListener {
	private MainWindow mw;
	
	private JMenuBar menubar;
	
	private JMenu menuOption;
	private JMenu menuDatabases;
	private JMenu menuDatabase;
	private JMenu menuTable;
	private JMenu menuIndex;
	private JMenu menuView;
	private JMenu menuTrigger;
	private JMenu menuTools;
	private JMenu menuHelp;
	
	private JMenuItem optionConnectDatabase;
	private JMenuItem optionDisconnectDatabase;
	private JMenuItem optionDisconnectAllDatabase;
	private JMenuItem optionNewDatabase;
	private JMenuItem optionDelDatabase;
	private JMenu 	  optionRecentUsed ;   // 显示子菜单
	private JMenuItem optionExit;
	
	private JMenuItem databaseCreate;
	private JMenuItem databaseDrop;
	private JMenuItem databaseEmpty;
	private JMenuItem databaseRename;
	private JMenuItem databaseCopy;
	private JMenuItem databaseExport;
	private JMenuItem graphicDatabase;
	
	private JMenuItem tableCreate;
	private JMenuItem tableDrop;
	private JMenuItem tableEmpty;
	private JMenuItem tableRename;
	private JMenuItem tableCopy;
	private JMenuItem tableExport;
	private JMenuItem tableReindex;
	
	private JMenuItem indexCreate;
	private JMenuItem indexDrop;
	private JMenuItem indexReindex;
	
	private JMenuItem viewCreate;
	private JMenuItem viewDrop;
	private JMenuItem viewRename;
	private JMenuItem viewModify;
	private JMenuItem viewExport;
	
	private JMenuItem triggerCreate;
	private JMenuItem triggerDrop;
	private JMenuItem triggerRename;
	
	private JMenuItem toolsOption;
	
	private JMenuItem helpAbout;
	
	private HashSet<File> recentUsedFiles = new HashSet<File>();
	private ArrayList<File> openedFiles = new ArrayList<File>();

	
	public MWMenuBar() {
		initMenu();
	}
	
	public MWMenuBar(MainWindow mw) {
		this();
		this.mw = mw;
	}
	
	private void initMenu() {
		menubar = new JMenuBar();
		
		menuOption = new JMenu("文件");
		optionConnectDatabase 	 	= new JMenuItem("打开数据库");
		optionDisconnectDatabase 	= new JMenuItem("断开当前数据库");
		optionDisconnectAllDatabase = new JMenuItem("断开所有数据库");
		optionNewDatabase			 = new JMenuItem("新建数据库");
		optionDelDatabase		 	= new JMenuItem("删除当前数据库");
		optionRecentUsed 		 	= new JMenu("最近使用的文件");
		optionExit 		 		 	= new JMenuItem("Exit");		

		// 设置图标
		optionConnectDatabase.setIcon(getIcon(GlobalDefines.ICON_CONNECT_DATABASE));
		optionDisconnectDatabase.setIcon(getIcon(GlobalDefines.ICON_DISCONNECT_DATABASE));
		optionDisconnectAllDatabase.setIcon(getIcon(GlobalDefines.ICON_DISCONNECT_DATABASE));
		// 添加监听器
		optionConnectDatabase.addActionListener(this);
		optionDisconnectDatabase.addActionListener(this);
		optionDisconnectAllDatabase.addActionListener(this);
		optionRecentUsed.addActionListener(this);
		optionExit.addActionListener(this);
		
		menuOption.add(optionConnectDatabase);
		menuOption.add(optionDisconnectDatabase);
		menuOption.add(optionDisconnectAllDatabase);
		menuOption.add(optionNewDatabase);
		menuOption.add(optionDelDatabase);
		menuOption.add(optionRecentUsed);
		menuOption.addSeparator();
		menuOption.add(optionExit);
		
		/* 动态添加 Databases  */
		menuDatabases = new JMenu("Select Database");
		
		
		menuDatabase 	= new JMenu("Database");
		databaseCreate  = new JMenuItem("Create Database");
		databaseDrop  	= new JMenuItem("Drop Database");
		databaseEmpty 	= new JMenuItem("Empty Database");
		databaseRename  = new JMenuItem("Rename Database");
		databaseCopy 	= new JMenuItem("Copy Database");
		databaseExport  = new JMenuItem("Export Database");
		graphicDatabase  = new JMenuItem("Graphic Database");
		
		menuDatabase.add(databaseCreate);
		menuDatabase.add(databaseDrop);
		menuDatabase.add(databaseEmpty);
		menuDatabase.addSeparator();
		menuDatabase.add(databaseRename);
		menuDatabase.add(databaseCopy);
		menuDatabase.add(databaseExport);
		menuDatabase.addSeparator();
		menuDatabase.add(graphicDatabase);
		
		// table
		menuTable 	 = new JMenu("Table");
		tableCreate  = new JMenuItem("Create Table");
		tableDrop  	 = new JMenuItem("Drop Table");
		tableEmpty 	 = new JMenuItem("Empty Table");
		tableRename  = new JMenuItem("Rename Table");
		tableCopy 	 = new JMenuItem("Copy Table");
		tableExport  = new JMenuItem("Export Table");
		tableReindex = new JMenuItem("Reindex Table");
		
		menuTable.add(tableCreate);
		menuTable.add(tableDrop);
		menuTable.add(tableEmpty);
		menuTable.addSeparator();
		menuTable.add(tableRename);
		menuTable.add(tableCopy);
		menuTable.add(tableExport);
		menuTable.addSeparator();
		menuTable.add(tableReindex);
		
		// index
		menuIndex 	 = new JMenu("Index");
		indexCreate  = new JMenuItem("Create Index");
		indexDrop 	 = new JMenuItem("Drop Index");
		indexReindex = new JMenuItem("Reindex Index");
		
		menuIndex.add(indexCreate);
		menuIndex.add(indexDrop);
		menuIndex.addSeparator();
		menuIndex.add(indexReindex);
		
		// view
		menuView   = new JMenu("View");
		viewCreate = new JMenuItem("Create View");
		viewDrop   = new JMenuItem("Drop View");
		viewRename = new JMenuItem("Rename View");
		viewModify = new JMenuItem("Modify View");
		viewExport = new JMenuItem("Export View");
		
		menuView.add(viewCreate);
		menuView.add(viewDrop);
		menuView.addSeparator();
		menuView.add(viewRename);
		menuView.add(viewModify);
		menuView.add(viewExport);
		
		// trigger
		menuTrigger 	= new JMenu("Trigger");
		triggerCreate 	= new JMenuItem("Create Trigger");
		triggerDrop 	= new JMenuItem("Drop Trigger");
		triggerRename 	= new JMenuItem("Rename Trigger");
		
		menuTrigger.add(triggerCreate);
		menuTrigger.add(triggerDrop);
		menuTrigger.addSeparator();
		menuTrigger.add(triggerRename);
		
		menuTools = new JMenu("Tools");
		toolsOption = new JMenuItem("Option");
		
		menuTools.add(toolsOption);

		menuHelp = new JMenu("帮助");
		
		helpAbout = new JMenuItem("about");
		helpAbout.addActionListener(this);
		menuHelp.add(helpAbout);
		
		
		menubar.add(menuOption);
		menubar.add(menuDatabases);
		menubar.add(menuDatabase);
		menubar.add(menuTable);
		menubar.add(menuIndex);
		menubar.add(menuView);
		menubar.add(menuTrigger);
		menubar.add(menuTools);
		menubar.add(menuHelp);
	}
	

	/**
	 * @return the recentUsed
	 */
	public int getRecentUsed() {
		return recentUsedFiles.size();
	}


	/**
	 * @return the recentUsedFiles
	 */
	public HashSet<File> getRecentUsedFiles() {
		return recentUsedFiles;
	}

	/**
	 * 初始化时，传入最近使用的文件
	 * @param recentUsedFiles the recentUsedFiles to set
	 */
	public void setRecentUsedFiles(HashSet<File> recentUsedFiles) {
		this.recentUsedFiles = recentUsedFiles;
	}
	
	/**
	 * 向最近使用，添加一个文件
	 * @param f
	 */
	public void addRecentUsedFiles(File f) {
		this.recentUsedFiles.add(f);
	}
	
	/**
	 * 打开一个文件
	 * @param f
	 */
	public void addOpenedFile(File f) {
		this.openedFiles.add(f);
		update();
	}
	
	/**
	 * 关闭一个文件
	 * @param f
	 */
	public void removeOpenedFile(File f) {
		this.openedFiles.remove(f);
		update();
	}
	
	/**
	 * 关闭所有文件时，从菜单栏中去掉所有有关数据库文件的  MenuItem
	 */
	public void removeAllOpenedFile() {
		this.recentUsedFiles.addAll(this.openedFiles);
		this.openedFiles.clear();
		update();
	}

	public JMenuBar getMenuBar() {
		update();
		return menubar;
	}
	
	/**
	 * 实现动态更新
	 */
	public void update() {
		JMenuItem noused = new JMenuItem("空");
		noused.setEnabled(false);
		if( optionRecentUsed.getItemCount() == 0 && recentUsedFiles.size() == 0 ) {
			System.out.println("[Info] no recent Used Files");
			optionRecentUsed.add(noused);
		} else {
			optionRecentUsed.removeAll();
			JMenuItem mi; 
			for(File f : recentUsedFiles) {
				mi = new JMenuItem(f.getAbsolutePath());
				optionRecentUsed.add(mi);
			}
		}
		
		noused = new JMenuItem("空");
		noused.setEnabled(false);
		if( menuDatabases.getItemCount() == 0 && openedFiles.size() == 0 ) {
			menuDatabases.add(noused);
		} else {
			menuDatabases.removeAll();
			JMenuItem mi; 
			for(File f : openedFiles) {
				mi = new JMenuItem(f.getAbsolutePath());
				menuDatabases.add(mi);
			}
		}
		
	}
	
	private ImageIcon getIcon(String s) {
		ImageIcon icon = new ImageIcon(GlobalDefines.getUrl(s));
		Image image = icon.getImage();
		image = image.getScaledInstance(15, 15, Image.SCALE_AREA_AVERAGING);
//		System.out.println(image.getWidth(null));
		return new ImageIcon(image);
	}
	

	public void actionPerformed(ActionEvent e) {
		JMenuItem source = (JMenuItem) e.getSource();
		
		if( source == optionConnectDatabase ) {
			mw.connectDatabase();
		}
		if( source == optionDisconnectDatabase ) {
			mw.closeThisFile();
//			System.out.println("[Info] disconnect database: " + f.getAbsolutePath());
		}
		if( source == optionDisconnectAllDatabase ) {
			System.out.println("[Info] Disconnect All Database!");
			mw.closeAllFiles();
		}
		if( source == optionNewDatabase ) {
			
		}
		if( source == optionDelDatabase ) {
					
		}
		if( source == optionExit ) {
			// 断开所有数据库后，退出
			DBManager.getManager().disconnectAll();
			System.out.println("Bye");
			System.exit(0);
		}
		
		if( source == databaseCreate ) {
			
		}
		if( source == databaseDrop ) {
			
		}
		if( source == databaseEmpty ) {
			
		}
		if( source == databaseRename ) {
	
		}
		if( source == databaseCopy ) {
			
		}
		if( source == databaseExport ) {
			
		}
		if( source == graphicDatabase ) {
			
		}
		
		if( source == tableCreate ) {
			
		}
		if( source == tableDrop ) {
			
		}
		if( source == tableEmpty ) {
			
		}
		if( source == tableRename ) {
			
		}
		if( source == tableCopy ) {
			
		}
		if( source == tableExport ) {
			
		}
		if( source == tableReindex ) {
			
		}
		
		if( source == indexCreate ) {
			
		}
		if( source == indexDrop ) {
			
		}
		if( source == indexDrop ) {
			
		}
		if( source == indexReindex) {
			
		}
		
		if( source == viewCreate ) {
			
		}
		if( source == viewDrop ) {
			
		}
		if( source == viewRename ) {
			
		}
		if( source == viewModify ) {
			
		}
		if( source == viewExport ) {
			
		}
		
		if( source == triggerCreate ) {
			
		}
		if( source == triggerDrop ) {
			
		}
		if( source == triggerRename ) {
			
		}
		
		if( source == toolsOption ) {
			
		}
		
		if( source == helpAbout ) {
			JOptionPane.showMessageDialog(mw, "Graphical Database. A Java program." , "About", JOptionPane.INFORMATION_MESSAGE);
		}
		
	}
}

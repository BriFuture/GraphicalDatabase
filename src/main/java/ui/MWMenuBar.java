package ui;

import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.filechooser.FileNameExtensionFilter;

import database.DBManager;
import gdb.GlobalDefines;

public class MWMenuBar implements ActionListener {
	private MainWindow mw;
	
	private JMenuBar menubar;
	
	private JMenu menuOption;
	private JMenu menuDatabase;
	private JMenu menuTable;
	private JMenu menuView;
	private JMenu menuHelp;
	
	private JMenuItem optionConnectDatabase;
	private JMenuItem optionDisconnectDatabase;
	private JMenu optionRecentUsed ;
	private JMenuItem optionExit;
	
	private JMenuItem tableCreate;
	private JMenuItem tableDrop;
	private JMenuItem tableEmpty;
	
	private JMenuItem helpAbout;
	
	private JFileChooser chooser;
	private FileNameExtensionFilter sqlite;
	private FileNameExtensionFilter all;
	
	private int recentUsed = 0;
	private ArrayList<String> recentUsedFiles;

	
	public MWMenuBar() {
		initMenu();
		init();
	}
	
	public MWMenuBar(MainWindow mw) {
		this();
		this.mw = mw;
	}
	
	private void initMenu() {
		menubar = new JMenuBar();
		
		menuOption = new JMenu("文件");

		optionConnectDatabase = new JMenuItem("打开数据库");
		optionDisconnectDatabase = new JMenuItem("断开数据库");
		optionRecentUsed = new JMenu("最近使用的文件");
		optionExit = new JMenuItem("Exit");		

		optionConnectDatabase.setIcon(getIcon(GlobalDefines.CONNECT_DATABASE));
		optionDisconnectDatabase.setIcon(getIcon(GlobalDefines.DISCONNECT_DATABASE));
		optionConnectDatabase.addActionListener(this);
		optionDisconnectDatabase.addActionListener(this);
		optionRecentUsed.addActionListener(this);
		optionExit.addActionListener(this);
		
		menuOption.add(optionConnectDatabase);
		menuOption.add(optionDisconnectDatabase);
		menuOption.add(optionRecentUsed);
		menuOption.addSeparator();
		menuOption.add(optionExit);
		
		/* 动态添加  */
		menuDatabase = new JMenu("Database");
		
		menuTable = new JMenu("Table");
		tableCreate = new JMenuItem("打开数据库");
		tableDrop = new JMenuItem("打开数据库");
		tableEmpty = new JMenuItem("打开数据库");
		
		menuTable.add(tableCreate);
		menuTable.add(tableDrop);
		menuTable.add(tableEmpty);
		
		menuView = new JMenu("View");

		menuHelp = new JMenu("帮助");
		
		helpAbout = new JMenuItem("about");
		helpAbout.addActionListener(this);
		menuHelp.add(helpAbout);
		
		
		menubar.add(menuOption);
		menubar.add(menuDatabase);
		menubar.add(menuTable);
		menubar.add(menuView);
		menubar.add(menuHelp);
	}
	
	private void init() {
		chooser = new JFileChooser("C:\\Users\\future\\Documents\\my games\\Sid Meier's Civilization 5\\cache\\");
		sqlite = new FileNameExtensionFilter("SQLite DB Files(*.sqlite)", "sqlite");
		all = new FileNameExtensionFilter("All DB files (*.db)", "db");
				
		chooser.setAcceptAllFileFilterUsed(false);
//		chooser.setFileFilter(sqlite);
//		chooser.addChoosableFileFilter(all);
		chooser.setFileFilter(all);
		chooser.addChoosableFileFilter(sqlite);
	}
	
	
	
	/**
	 * @return the recentUsed
	 */
	public int getRecentUsed() {
		return recentUsed;
	}

	

	/**
	 * @return the recentUsedFiles
	 */
	public ArrayList<String> getRecentUsedFiles() {
		return recentUsedFiles;
	}

	/**
	 * @param recentUsedFiles the recentUsedFiles to set
	 */
	public void setRecentUsedFiles(ArrayList<String> recentUsedFiles) {
		this.recentUsedFiles = recentUsedFiles;
		this.recentUsed = recentUsedFiles.size();
	}

	public JMenuBar getMenuBar() {
		update();
		return menubar;
	}
	
	/**
	 * 实现动态更新
	 */
	public void update() {
		if(recentUsed == 0) {
			JMenuItem noused = new JMenuItem("空");
			noused.setEnabled(false);
			optionRecentUsed.add(noused);
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
			System.out.println("[Info] connect database");
			
			int returnVal = chooser.showOpenDialog(mw);
			if(returnVal == JFileChooser.APPROVE_OPTION) {
//				System.out.println("APPROVE  " + chooser.getSelectedFile().getName());
				mw.addFile(chooser.getSelectedFile());
			}
		}
		
		if( source == optionExit ) {
			// 断开所有数据库后，退出
			DBManager.getManager().disconnectAll();
			System.out.println("Bye");
			System.exit(0);
		}
		
	}
}

package ui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.io.File;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;

import javax.swing.JFileChooser;
import javax.swing.JSplitPane;
import javax.swing.filechooser.FileNameExtensionFilter;

import content.Data;

public class MainWindow extends BaseWindow{
	private String windowTitle = "GraphicalDatabase";
	
	private MWMenuBar menubar;
	private MWTree tree;
	
	private HashMap<File, Data> dataMap;
	
	private File openedFile;
	private ArrayList<File> addedFiles = new ArrayList<File>();
	
	// 弹出文件选择框
	private JFileChooser chooser;
	private FileNameExtensionFilter sqlite;
	private FileNameExtensionFilter all;
	

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public MainWindow() {
		setTitle(windowTitle);
		init();
		initChooser();
		// 最大化
		setExtendedState(MAXIMIZED_BOTH);	
		setVisible(true);
	}
	
	private void init() {
		// 设置菜单栏
		menubar = new MWMenuBar(this);
		setJMenuBar(menubar.getMenuBar());
		// 设置 Panel
		MWTabbedPanel panel = new MWTabbedPanel(this);
		// 设置左侧树
		tree = new MWTree(this);

		// 创建一个 SplitPane 用来分区
		JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, tree.getTree(), panel.getTabbedPane());
		splitPane.setOneTouchExpandable(true);
		Dimension minimumSize = new Dimension(200, 400);
		tree.getTree().setMinimumSize(minimumSize);
		panel.getTabbedPane().setMinimumSize(minimumSize);
		
		getContentPane().add(BorderLayout.CENTER, splitPane);
		
		dataMap = new HashMap<File, Data>();
		
	}
	
	/* 初始化选择器 */
	private void initChooser() {
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
	 * 打开数据库文件
	 */
	public void connectDatabase() {
		int returnVal = chooser.showOpenDialog(this);
		File f = null;
		if(returnVal == JFileChooser.APPROVE_OPTION) {
//			System.out.println("APPROVE  " + chooser.getSelectedFile().getName());
			f = chooser.getSelectedFile();
			addFile(f);
		}
		
//		System.out.println("[Info] connect database: " + f.getAbsolutePath());
	}
	
	/**
	 * 添加数据库文件
	 * @param f
	 */
	public void addFile(File f) {
		tree.getTree().setRootVisible(false);
		// 防止重复添加数据库对象
		if(!dataMap.containsKey(f)) {
			Data d = new Data(f);
			dataMap.put(f, d);
			tree.insertRoot(d);
			// 展开新添加的子树
			tree.getTree().expandRow(addedFiles.size());
			addedFiles.add(f);
			
			// 更新菜单栏
			menubar.addOpenedFile(f);
			menubar.update();
			
			// update opened File
			openedFile = f;
			openAddedFile(f);

			System.out.println("[Info] Add file: " + d);
		} else {
			System.out.println("[Warning] File has been added " + f.getAbsolutePath());
		}
	}
	
	/**
	 * 打开已添加的文件，
	 * 1. 在左侧树选中当前文件所在的节点
	 * 2. 右侧面板中显示相应的内容
	 * @param f
	 */
	public void openAddedFile(File f) {
		
	}
	
	/**
	 * 关闭数据库文件
	 * @param f
	 */
	public void closeFile(File f) {
		addedFiles.remove(f);
		
		// update menubar
		menubar.addRecentUsedFiles(f);
		menubar.removeOpenedFile(f);
		menubar.update();
		
	}
	
	/**
	 * 关闭当前数据库文件
	 * 若添加列表中还有文件，则显示最后一个使用过的文件
	 * 若没有，清空右侧面板
	 */
	public void closeThisFile() {
		closeFile(openedFile);
	}
	
	/**
	 * 关闭所有数据库文件
	 */
	public void closeAllFiles() {
		// 
		addedFiles.clear();
		
	}
	
	/**
	 * 删除文件
	 * @param f
	 */
	public void delFile(File f) {
		closeFile(f);
		if(f.delete()) {
			System.out.println("[Info] file " + f.getAbsolutePath() + " has been deleted");
		}
	}
	
	/**
	 * 
	 * @return  返回已经打开的所有数据库文件
	 */
	public Set<File> getOpenedFiles() {
		return dataMap.keySet();
	}
	
	

}

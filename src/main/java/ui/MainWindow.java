package ui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.io.File;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;

import javax.swing.JFileChooser;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTextField;
import javax.swing.filechooser.FileNameExtensionFilter;

import content.Data;

public class MainWindow extends BaseWindow{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String windowTitle = "GraphicalDatabase";
	
	private MWMenuBar menubar;
	private MWTree tree;
	private MWTabbedPanel tabbedpanel;
	
	private JPanel statebar;
	private JTextField stateFileName;
	
	private HashMap<File, Data> dataMap = new HashMap<File, Data>();
	
	private File openedFile;
	private ArrayList<File> addedFiles = new ArrayList<File>();
	
	// 弹出文件选择框
	private JFileChooser chooser;
	private FileNameExtensionFilter sqlite;
	private FileNameExtensionFilter all;
	

	
	public MainWindow() {
		setTitle(windowTitle);
		init();
		initChooser();
		// 最大化
		setExtendedState(MAXIMIZED_BOTH);	
		setVisible(true);
		test();
	}
	
	private void init() {
		// 设置菜单栏
		menubar = new MWMenuBar(this);
		setJMenuBar(menubar.getMenuBar());
		// 设置左侧树
		tree = new MWTree(this);
		JScrollPane scrollPane = new JScrollPane(tree.getTree());
		scrollPane.setPreferredSize(new Dimension(280, 600));
		scrollPane.setMinimumSize(new Dimension(200, 600));
		// 设置 Panel
		tabbedpanel = new MWTabbedPanel(this);
		// 状态栏
		initStatebar();

		// 创建一个 SplitPane 用来分区
		JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, scrollPane, tabbedpanel.getTabbedPane());
		splitPane.setOneTouchExpandable(true);
		getContentPane().add(BorderLayout.CENTER, splitPane);
	}
	
	/* 初始化文件选择器 */
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
	
	/* 初始化状态栏 */
	private void initStatebar() {
		statebar = new JPanel();
		statebar.setLayout(new GridLayout(1, 5));
		statebar.setAutoscrolls(false);

		Dimension statebarsize = new Dimension(200, 24);
		statebar.setPreferredSize(statebarsize);
		
		stateFileName = new JTextField();
		stateFileName.setPreferredSize(statebarsize);
		stateFileName.setEnabled(false);
		if(openedFile != null)
			stateFileName.setText(openedFile.getName());
		
		statebar.add(stateFileName);
		getContentPane().add(BorderLayout.SOUTH, statebar);
	}
	
	/**
	 * 打开数据库文件
	 */
	public void connectDatabase() {
		int returnVal = chooser.showOpenDialog(this);
		File f = null;
		if(returnVal == JFileChooser.APPROVE_OPTION) {
			f = chooser.getSelectedFile();
			addFile(f);
		}
	}
	
	/**
	 * 添加数据库文件
	 * @param f
	 */
	public void addFile(File f) {
		// 防止重复添加数据库对象
		if(!dataMap.containsKey(f)) {
			// update opened File
			addedFiles.add(f);
			openAddedFile(f);

			Data d = new Data(f);
			dataMap.put(f, d);
			tree.insertRoot(d);
			
			tabbedpanel.add(d);
			
			// 菜单栏添加文件
			menubar.addOpenedFile(f);
			
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
		openedFile = f;
		stateFileName.setText(f.getAbsolutePath());
	}
	
	/**
	 * 关闭数据库文件
	 * @param f
	 */
	public void closeFile(File f) {
		addedFiles.remove(f);
		
		if(f.equals(openedFile)) {
			stateFileName.setText("");
		}
			
		// update menubar
		menubar.addRecentUsedFiles(f);
		menubar.removeOpenedFile(f);
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
		// 清除 Tree 中的所有节点
		tree.clearRoot();
		// 清除  menubar 中的已打开文件
		menubar.removeAllOpenedFile();
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
	
	private void test() {
		String str = "C:\\Users\\future\\Documents\\my games\\Sid Meier's Civilization 5\\cache\\Localization-Korea.db";
		File f = new File(str);
		addFile(f);
	}

}

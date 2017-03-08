package ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.HashMap;
import java.util.Set;

import javax.swing.JPanel;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;

import content.Data;
import content.MWTreeModel;

public class MainWindow extends BaseWindow implements ActionListener{
	private String windowTitle = "GraphicalDatabase";
	
	private MWMenuBar menubar;
	private JTree databaseTree;
	private MWTreeModel treeModel;
	
	private HashMap<File, Data> dataMap;
	// 默认的根节点
	private DefaultMutableTreeNode rootNode = new DefaultMutableTreeNode("Databases");
	

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public MainWindow() {
		setTitle(windowTitle);
		init();
		
		// 最大化
		setExtendedState(MAXIMIZED_BOTH);	
		setVisible(true);
	}
	
	private void init() {
		// 设置菜单栏
		menubar = new MWMenuBar(this);
		setJMenuBar(menubar.getMenuBar());
		// 设置 Panel
		JPanel panel = new JPanel();
		panel.setBackground(Color.GRAY);
		// 设置左侧树
		databaseTree = new JTree();
		databaseTree.setRootVisible(false);
		treeModel = new MWTreeModel(rootNode);
		databaseTree.setModel(treeModel);

		getContentPane().add(BorderLayout.WEST, databaseTree);
		
//		getContentPane().add(BorderLayout.CENTER, panel);
		
		dataMap = new HashMap<File, Data>();
	}
	
	public void addFile(File f) {
		// 防止重复添加数据库对象
		if(!dataMap.containsKey(f)) {
			Data d = new Data(f);
			dataMap.put(f, d);
			treeModel.insertIntoRoot(d);
			System.out.println("[Info] Add file: " + d);
		} else {
			System.out.println("[Warning] File has been added " + f.getAbsolutePath());
		}
	}
	
	/**
	 * 
	 * @return  返回已经打开的所有数据库文件
	 */
	public Set<File> getOpenedFiles() {
		return dataMap.keySet();
	}
	

	public void actionPerformed(ActionEvent e) {

	}
	
	

}
